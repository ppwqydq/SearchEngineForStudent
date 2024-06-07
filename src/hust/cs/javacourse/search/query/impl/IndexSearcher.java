package hust.cs.javacourse.search.query.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractPostingList;
import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.query.AbstractHit;
import hust.cs.javacourse.search.query.AbstractIndexSearcher;
import hust.cs.javacourse.search.query.Sort;

import java.io.File;
import java.util.*;

public class IndexSearcher extends AbstractIndexSearcher {

    @Override
    public void open(String indexFile) {
        // 加载指定文件的索引数据
        index.load(new File(indexFile));
    }

    @Override
    public AbstractHit[] search(AbstractTerm queryTerm, Sort sorter) {
        // 根据单个检索词进行搜索
        AbstractPostingList postingList = index.search(queryTerm);
        // 如果没有找到任何posting列表，返回空数组
        if (postingList == null) return new AbstractHit[0];
        List<AbstractHit> hits = new ArrayList<>();
        for (int i = 0; i < postingList.size(); i++) {
            AbstractPosting posting = postingList.get(i);
            String path = index.getDocName(posting.getDocId());
            HashMap<AbstractTerm, AbstractPosting> map = new HashMap<>();
            map.put(queryTerm, posting);
            AbstractHit hit = new Hit(posting.getDocId(), path, map);
            hit.setScore(sorter.score(hit));
            hits.add(hit);
        }
        sorter.sort(hits);
        return hits.toArray(new AbstractHit[0]);
    }

    @Override
    public AbstractHit[] search(AbstractTerm queryTerm1, AbstractTerm queryTerm2, Sort sorter, LogicalCombination combine) {
        // 根据两个检索词进行搜索
        AbstractPostingList postingList1 = index.search(queryTerm1);
        AbstractPostingList postingList2 = index.search(queryTerm2);
        if (postingList1 == null && postingList2 == null) return new AbstractHit[0];

        Map<Integer, Map<AbstractTerm, AbstractPosting>> result;
        if (combine == LogicalCombination.AND) {
            // 如果是AND组合，找出两个列表中都存在的文档
            result = intersect(queryTerm1, queryTerm2, postingList1, postingList2);
        } else {
            // 如果是OR组合，找出两个列表中任一存在的文档
            result = union(queryTerm1, queryTerm2, postingList1, postingList2);
        }
        return getHits(sorter, result);
    }

    private AbstractHit[] getHits(Sort sorter, Map<Integer, Map<AbstractTerm, AbstractPosting>> result) {
        // 将结果转换为AbstractHit数组，并进行排序
        if (result.isEmpty()) {
            return new AbstractHit[0];
        }
        AbstractHit[] hits = new AbstractHit[result.size()];
        List<Map.Entry<Integer, Map<AbstractTerm, AbstractPosting>>> entryList = new ArrayList<>(result.entrySet());
        for (int i = 0; i < hits.length; i++) {
            Map.Entry<Integer, Map<AbstractTerm, AbstractPosting>> entry = entryList.get(i);
            hits[i] = new Hit(entry.getKey(), index.getDocName(entry.getKey()), entry.getValue());
            hits[i].setScore(sorter.score(hits[i]));
        }
        List<AbstractHit> hitList = Arrays.asList(hits);
        sorter.sort(hitList);
        hits = hitList.toArray(hits);
        return hits;
    }

    private Map<Integer, Map<AbstractTerm, AbstractPosting>> intersect(AbstractTerm queryTerm1, AbstractTerm queryTerm2, AbstractPostingList postingList1, AbstractPostingList postingList2) {
        // 找出两个posting列表中都存在的文档
        Map<Integer, Map<AbstractTerm, AbstractPosting>> result = new HashMap<>();
        int i = 0, j = 0;
        while (i < postingList1.size() && j < postingList2.size()) {
            AbstractPosting posting1 = postingList1.get(i);
            AbstractPosting posting2 = postingList2.get(j);
            if (posting1.getDocId() == posting2.getDocId()) {
                Map<AbstractTerm, AbstractPosting> termPostingMapping = new HashMap<>();
                termPostingMapping.put(queryTerm1, posting1);
                termPostingMapping.put(queryTerm2, posting2);
                result.put(posting1.getDocId(), termPostingMapping);
                i++;
                j++;
            } else if (posting1.getDocId() < posting2.getDocId()) {
                i++;
            } else {
                j++;
            }
        }
        return result;
    }

    private Map<Integer, Map<AbstractTerm, AbstractPosting>> union(AbstractTerm queryTerm1, AbstractTerm queryTerm2, AbstractPostingList postingList1, AbstractPostingList postingList2) {
        // 找出两个posting列表中任一存在的文档
        Map<Integer, Map<AbstractTerm, AbstractPosting>> result = new HashMap<>();
        int i = 0, j = 0;
        while (i < postingList1.size() && j < postingList2.size()) {
            AbstractPosting posting1 = postingList1.get(i);
            AbstractPosting posting2 = postingList2.get(j);
            if (posting1.getDocId() == posting2.getDocId()) {
                Map<AbstractTerm, AbstractPosting> termPostingMapping = new HashMap<>();
                termPostingMapping.put(queryTerm1, posting1);
                termPostingMapping.put(queryTerm2, posting2);
                result.put(posting1.getDocId(), termPostingMapping);
                i++;
                j++;
            } else if (posting1.getDocId() < posting2.getDocId()) {
                Map<AbstractTerm, AbstractPosting> termPostingMapping = new HashMap<>();
                termPostingMapping.put(queryTerm1, posting1);
                result.put(posting1.getDocId(), termPostingMapping);
                i++;
            } else {
                Map<AbstractTerm, AbstractPosting> termPostingMapping = new HashMap<>();
                termPostingMapping.put(queryTerm2, posting2);
                result.put(posting2.getDocId(), termPostingMapping);
                j++;
            }
        }
        put(queryTerm1, postingList1, result, i);
        put(queryTerm2, postingList2, result, j);
        return result;
    }

    private void put(AbstractTerm queryTerm1, AbstractPostingList postingList1, Map<Integer, Map<AbstractTerm, AbstractPosting>> result, int i) {
        // 将剩余的posting添加到结果中
        while (i < postingList1.size()) {
            AbstractPosting posting1 = postingList1.get(i);
            Map<AbstractTerm, AbstractPosting> termPostingMapping = new HashMap<>();
            termPostingMapping.put(queryTerm1, posting1);
            result.put(posting1.getDocId(), termPostingMapping);
            i++;
        }
    }

}