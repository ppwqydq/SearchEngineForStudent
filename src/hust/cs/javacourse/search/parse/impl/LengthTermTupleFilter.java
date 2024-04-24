package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.Config;

public class LengthTermTupleFilter extends AbstractTermTupleFilter {

    public LengthTermTupleFilter(AbstractTermTupleStream input) {
        super(input);
    }

    @Override
    public AbstractTermTuple next(){
        AbstractTermTuple termTuple=input.next();
        if(termTuple==null) return null;
        //过滤掉超出范围的单词
        while (termTuple.term.getContent().length() < Config.TERM_FILTER_MINLENGTH || termTuple.term.getContent().length() > Config.TERM_FILTER_MAXLENGTH) {
            termTuple = input.next();
            if (termTuple == null) {
                return null;
            }
        }
        return termTuple;
    }
}
