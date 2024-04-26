package hust.cs.javacourse.search.query.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractPostingList;
import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.index.impl.Posting;
import hust.cs.javacourse.search.query.AbstractHit;
import hust.cs.javacourse.search.query.AbstractIndexSearcher;
import hust.cs.javacourse.search.query.Sort;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class IndexSearcher extends AbstractIndexSearcher {

    @Override
    public void open(String indexFile) {
        index.load(new File(indexFile));
        index.optimize();
    }

    /**
     *
     *根据单个检索词检索
     * @param queryTerm ：检索词
     * @param sorter ：排序器
     * @return
     */
    @Override
    public AbstractHit[] search(AbstractTerm queryTerm, Sort sorter) {
        AbstractPostingList postingList= index.search(queryTerm);
        if(postingList==null) return null;
        List<AbstractHit> hits= new ArrayList<AbstractHit>();
        for(int i=0; i<postingList.size(); i++){
            AbstractPosting posting= postingList.get(i);
            String path=index.getDocName(posting.getDocId());
            HashMap<AbstractTerm,AbstractPosting> map=new HashMap<>();
            map.put(queryTerm, posting);
            AbstractHit hit=new Hit(posting.getDocId(),path,map);
            hit.setScore(sorter.score(hit));
            hits.add(hit);
        }
        sorter.sort(hits);
        return hits.toArray(new AbstractHit[0]);
    }

    /**
     * 根据两个检索词检索
     * @param queryTerm1 ：第1个检索词
     * @param queryTerm2 ：第2个检索词
     * @param sorter ：    排序器
     * @param combine ：   多个检索词的逻辑组合方式
     * @return
     */
    @Override
    public AbstractHit[] search(AbstractTerm queryTerm1, AbstractTerm queryTerm2, Sort sorter, LogicalCombination combine) {
        AbstractPostingList postingList1= index.search(queryTerm1);
        AbstractPostingList postingList2= index.search(queryTerm2);
        if(postingList1==null && postingList2==null) return null;
        List<AbstractHit> hits=new ArrayList<>();

        int length1,length2;
        if(postingList1==null) length1=0;
        else length1=postingList1.size();
        if(postingList2==null) length2=0;
        else length2=postingList2.size();

        int i=0;
        int j=0;
        Posting posting=new Posting(-1,-1,null);

        while(i<length1 || j<length2){
            AbstractPosting posting1,posting2;
            if(i<length1) posting1=postingList1.get(i);
            else posting1=posting;
            if(j<length2) posting2=postingList2.get(j);
            else posting2=posting;

            int docId1=posting1.getDocId();
            int docId2=posting2.getDocId();

            if(docId1==docId2){
                String path1=index.getDocName(docId1);
                HashMap<AbstractTerm,AbstractPosting> map=new HashMap<>();
                map.put(queryTerm1, posting1);
                map.put(queryTerm2, posting2);
                AbstractHit hit=new Hit(docId1,path1,map);
                hit.setScore(sorter.score(hit));
                hits.add(hit);
                i++;
                j++;
            }
            else if(docId1<docId2){
                if(combine==LogicalCombination.OR){
                    search(queryTerm1, sorter, hits, posting1, docId1);
                }
                i++;
            }
            else{
                if(combine==LogicalCombination.AND){
                    search(queryTerm2, sorter, hits, posting2, docId1);
                }
                j++;
            }
        }
        sorter.sort(hits);
        return hits.toArray(new AbstractHit[0]);
    }

    private void search(AbstractTerm queryTerm, Sort sorter, List<AbstractHit> hits, AbstractPosting posting, int docId) {
        String path=index.getDocName(docId);
        HashMap<AbstractTerm,AbstractPosting> map=new HashMap<>();
        map.put(queryTerm, posting);
        AbstractHit hit=new Hit(docId,path,map);
        hit.setScore(sorter.score(hit));
        hits.add(hit);
    }
}
