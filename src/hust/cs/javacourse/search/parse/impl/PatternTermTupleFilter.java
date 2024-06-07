package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple; // 导入AbstractTermTuple类
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter; // 导入AbstractTermTupleFilter类
import hust.cs.javacourse.search.parse.AbstractTermTupleStream; // 导入AbstractTermTupleStream类
import hust.cs.javacourse.search.util.Config; // 导入Config类

/**
 * PatternTermTupleFilter类继承自AbstractTermTupleFilter类，用于过滤不符合正则表达式模式的TermTuple。
 */
public class PatternTermTupleFilter extends AbstractTermTupleFilter {

    /**
     * 构造函数，调用父类构造函数。
     * @param input 输入的TermTupleStream对象
     */
    public PatternTermTupleFilter(AbstractTermTupleStream input) {
        super(input);
    }

    /**
     * 从输入流中获取下一个满足条件的TermTuple对象。
     * @return 满足条件的TermTuple对象，如果到达流末尾，则返回null。
     */
    @Override
    public AbstractTermTuple next() {
        AbstractTermTuple termTuple=input.next(); // 从输入流中获取下一个TermTuple对象
        if(termTuple==null) return null; // 如果没有更多TermTuple对象，则返回null
        // 循环检查TermTuple对象中的term内容是否满足正则表达式模式
        while (!termTuple.term.getContent().matches(Config.TERM_FILTER_PATTERN)) {
            termTuple = input.next(); // 获取下一个TermTuple对象
            if (termTuple == null) // 如果没有更多TermTuple对象，则返回null
                return null;
        }
        return termTuple; // 返回满足条件的TermTuple对象
    }
}
