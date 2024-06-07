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

/**
 * TermTupleScanner类是AbstractTermTupleScanner的具体实现类。
 * 主要用于从文本输入流中扫描并解析出TermTuple对象（一个TermTuple对象代表一个单词及其出现的位置）。
 */
public class TermTupleScanner extends AbstractTermTupleScanner {
    // 记录读取的三元组在输入流中的位置
    private int position = 0;
    // 存储输入流字符串分割后的结果
    private List<String> terms;
    // 用于分割字符串的工具
    private StringSplitter splitter = new StringSplitter();

    /**
     * 构造函数，初始化TermTupleScanner。
     *
     * @param input BufferedReader对象，表示输入流。
     */
    public TermTupleScanner(BufferedReader input) {
        super(input);
        terms = new ArrayList<>();
        // 设置分割字符串的正则表达式
        splitter.setSplitRegex(Config.STRING_SPLITTER_REGEX);
    }

    /**
     * 从输入流中读取下一个TermTuple对象。
     *
     * @return 读取到的TermTuple对象，如果输入流结束则返回null。
     */
    @Override
    public AbstractTermTuple next() {
        try {
            // 如果terms列表为空，说明需要从输入流中读取新的数据
            if (terms.isEmpty()) {
                // 逐行读取并存储到StringBuilder中
                String s;
                StringBuilder sbu = new StringBuilder();
                while ((s = input.readLine()) != null) {
                    sbu.append(s).append("\n");
                }
                // 将读取到的字符串全转换为小写并去除首尾空格
                s = sbu.toString().trim().toLowerCase();
                // 使用分割器分割字符串，结果存入terms列表
                terms = splitter.splitByRegex(s);
            }
            // 如果terms列表为空，说明输入流已处理完毕
            if(terms.isEmpty()) return null;
            // 依次取出terms列表中的单词，构建并返回TermTuple对象
            AbstractTerm term = new Term(terms.get(0));
            terms.remove(0);
            return new TermTuple(term, position++);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭输入流。
     */
    @Override
    public void close(){
        super.close();
    }
}
