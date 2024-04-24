package hust.cs.javacourse.search.query.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.query.AbstractHit;
import hust.cs.javacourse.search.query.Sort;

import java.util.List;
import java.util.Map;

public class SimpleSorter implements Sort {

    @Override
    public void sort(List<AbstractHit> hits) {
        hits.sort((AbstractHit o1, AbstractHit o2) -> ((int) (score(o2) - score(o1))));
    }

    @Override
    public double score(AbstractHit hit) {
        double score = 0.0;
        for (Map.Entry<AbstractTerm, AbstractPosting> entry : hit.getTermPostingMapping().entrySet()) {
            score += entry.getValue().getFreq();
        }
        return score;
    }
}
