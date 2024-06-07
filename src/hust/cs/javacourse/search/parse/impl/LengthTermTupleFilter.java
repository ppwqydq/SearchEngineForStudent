package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.Config;

public class LengthTermTupleFilter extends AbstractTermTupleFilter {
    /**
     * 构造函数
     * @param input 输入的TermTupleStream
     */
    public LengthTermTupleFilter(AbstractTermTupleStream input) {
        super(input);
    }

    /**
     * 从输入的TermTupleStream中获取下一个TermTuple，但是会过滤掉长度不符合要求的单词
     * @return 符合长度要求的下一个TermTuple，如果没有下一个符合要求的TermTuple则返回null
     */
    @Override
    public AbstractTermTuple next(){
        AbstractTermTuple termTuple=input.next();  // 获取下一个TermTuple
        if(termTuple==null) return null;  // 如果没有下一个TermTuple，返回null
        // 过滤掉长度不在指定范围内的单词
        while (termTuple.term.getContent().length() < Config.TERM_FILTER_MINLENGTH || termTuple.term.getContent().length() > Config.TERM_FILTER_MAXLENGTH) {
            termTuple = input.next();  // 获取下一个TermTuple
            if (termTuple == null) {  // 如果没有下一个TermTuple，返回null
                return null;
            }
        }
        return termTuple;  // 返回符合长度要求的TermTuple
    }
}
