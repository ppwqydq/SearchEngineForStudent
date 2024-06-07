package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractDocumentBuilder;
import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.parse.impl.LengthTermTupleFilter;
import hust.cs.javacourse.search.parse.impl.PatternTermTupleFilter;
import hust.cs.javacourse.search.parse.impl.StopWordTermTupleFilter;
import hust.cs.javacourse.search.parse.impl.TermTupleScanner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentBuilder extends AbstractDocumentBuilder {

    @Override
    public AbstractDocument build(int docId, String docPath, AbstractTermTupleStream termTupleStream){
        List<AbstractTermTuple> newList = new ArrayList<>();
        // 读取termTupleStream中的所有termTuple
        while (true) {
            AbstractTermTuple termTuple = termTupleStream.next();
            if (termTuple == null) {
                break;
            }
            newList.add(termTuple);
        }
        termTupleStream.close(); // 关闭流
        return new Document(docId, docPath, newList); // 创建并返回一个新的文档对象
    }

    @Override
    public AbstractDocument build(int docId, String docPath, File file){
        // 缓冲读取器用于读取文档
        BufferedReader reader;
        // 三元组扫描器，用于从文档中读取内容并生成三元组
        AbstractTermTupleStream scanner;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            scanner = new TermTupleScanner(reader); // 使用扫描器读取文档内容
            // 应用过滤器链，按顺序过滤长度不合适的、不符合模式的和停用词的三元组
            scanner = new LengthTermTupleFilter(
                    new PatternTermTupleFilter(
                            new StopWordTermTupleFilter(scanner)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e); // 如果文件未找到，抛出运行时异常
        }
        return build(docId, docPath, scanner); // 构建并返回文档对象
    }
}