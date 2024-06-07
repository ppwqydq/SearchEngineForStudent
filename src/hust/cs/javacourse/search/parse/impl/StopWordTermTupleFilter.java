package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.StopWords;

import java.util.Arrays;
import java.util.List;

public class StopWordTermTupleFilter extends AbstractTermTupleFilter {

    // 将停用词列表转换为List
    private List<String> stopwords = Arrays.asList(StopWords.STOP_WORDS);

    // 构造函数，传入一个AbstractTermTupleStream对象
    public StopWordTermTupleFilter(AbstractTermTupleStream input) {
        super(input);
    }

    @Override
    public AbstractTermTuple next(){
        // 从输入流中获取下一个TermTuple
        AbstractTermTuple termTuple=input.next();
        // 如果获取到的TermTuple为null，则直接返回null
        if(termTuple==null) return null;
        // 过滤掉停用词，如果当前TermTuple的内容在停用词列表中，继续读取下一个TermTuple
        while (stopwords.contains(termTuple.term.getContent())) {
            termTuple = input.next();
            // 如果再次获取的TermTuple为null，返回null
            if (termTuple == null) {
                return null;
            }
        }
        // 返回过滤后的TermTuple
        return termTuple;
    }
}
