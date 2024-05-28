package hust.cs.javacourse.search.query.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractPostingList;
import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.index.impl.Posting;
import hust.cs.javacourse.search.query.AbstractHit;
import hust.cs.javacourse.search.query.AbstractIndexSearcher;
import hust.cs.javacourse.search.query.Sort;

import java.io.File;
import java.util.*;

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
        List<AbstractHit> hits= new ArrayList<>();
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
        AbstractPostingList postingList1 = index.search(queryTerm1);
        AbstractPostingList postingList2 = index.search(queryTerm2);
        // 如果两个postingList都为null，则直接返回null
        if (postingList1 == null && postingList2 == null) return null;
        List<AbstractHit> hits = new ArrayList<>();
        // 使用三元运算符来避免NullPointerException
        int length1 = (postingList1 != null) ? postingList1.size() : 0;
        int length2 = (postingList2 != null) ? postingList2.size() : 0;

        int i = 0;
        int j = 0;
        // 创建一个空的Posting对象，其docId为-1，以便在比较时使用
        Posting posting = new Posting(-1, -1, null);
        while (i < length1 || j < length2) {
            AbstractPosting posting1;
            if ((i < length1)) {
                assert postingList1 != null;
                posting1 = postingList1.get(i);
            } else {
                posting1 = posting;
            }
            AbstractPosting posting2;
            if ((j < length2)) {
                assert postingList2 != null;
                posting2 = postingList2.get(j);
            } else {
                posting2 = posting;
            }

            int docId1 = posting1.getDocId();
            int docId2 = posting2.getDocId();

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
