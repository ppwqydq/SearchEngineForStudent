package hust.cs.javacourse.search.query.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.query.AbstractHit;
import hust.cs.javacourse.search.query.Sort;

import java.util.List;
import java.util.Map;

/**
 * SimpleSorter 类实现了 Sort 接口, 提供了排序搜索结果的简单实现.
 */
public class SimpleSorter implements Sort {

    /**
     * 对搜索结果进行排序.
     * @param hits 搜索结果的列表.
     */
    @Override
    public void sort(List<AbstractHit> hits) {
        // 使用lambda表达式进行排序, 根据每个hit的得分进行降序排序
        hits.sort((AbstractHit o1, AbstractHit o2) -> ((int) (score(o2) - score(o1))));
    }

    /**
     * 计算一个搜索结果的得分.
     * @param hit 一个搜索结果.
     * @return 返回该搜索结果的得分.
     */
    @Override
    public double score(AbstractHit hit) {
        double score = 0.0;
        // 遍历hit中的所有词项和它们对应的posting
        for (Map.Entry<AbstractTerm, AbstractPosting> entry : hit.getTermPostingMapping().entrySet()) {
            // 将每个posting的出现频率加到总得分上
            score += entry.getValue().getFreq();
        }
        return score;
    }
}
