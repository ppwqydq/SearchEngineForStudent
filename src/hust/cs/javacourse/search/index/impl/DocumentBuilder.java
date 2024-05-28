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
        termTupleStream.close();
        return new Document(docId, docPath, newList);
    }

    @Override
    public AbstractDocument build(int docId, String docPath, File file){
        //缓冲读取器用于读取文档
        BufferedReader reader;
        //三元组扫描从文档中读取的内容
        AbstractTermTupleStream scanner;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            scanner = new TermTupleScanner(reader);
            //过滤
            scanner = new LengthTermTupleFilter(new PatternTermTupleFilter(new StopWordTermTupleFilter(scanner)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return build(docId, docPath, scanner);
    }
}
