package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.index.impl.Term;
import hust.cs.javacourse.search.index.impl.TermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleScanner;
import hust.cs.javacourse.search.util.Config;
import hust.cs.javacourse.search.util.StringSplitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TermTupleScanner extends AbstractTermTupleScanner {
    //读取的三元组在输入流中的位置
    private int position = 0;
    //输入流字符串分割后结果存储
    private List<String> terms;
    //分割字符串
    private StringSplitter splitter = new StringSplitter();

    public TermTupleScanner(BufferedReader input) {
        super(input);
        terms = new ArrayList<>();
        splitter.setSplitRegex(Config.STRING_SPLITTER_REGEX);
    }

    @Override
    public AbstractTermTuple next() {
        try {
            if (terms.isEmpty()) {
                //逐行读取存储到sbu中
                String s;
                StringBuilder sbu = new StringBuilder();
                while ((s = input.readLine()) != null) {
                    sbu.append(s).append("\n");
                }
                //全变为小写并去除首尾空格
                s = sbu.toString().trim();
                s = s.toLowerCase();
                //分割字符串，存入terms
                terms = splitter.splitByRegex(s);
            }
            //terms为空输入流处理完毕
            if(terms.isEmpty()) return null;
            //挨个取出并构建三元组
            AbstractTerm term = new Term(terms.get(0));
            terms.remove(0);
            return new TermTuple(term, position++);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close(){
        super.close();
    }
}
