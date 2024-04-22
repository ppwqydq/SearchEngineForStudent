package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractDocumentBuilder;
import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentBuilder extends AbstractDocumentBuilder {

    @Override
    public AbstractDocument build(int docId, String docPath, AbstractTermTupleStream termTupleStream){
        List<AbstractTermTuple> list = new ArrayList<>();
        AbstractTermTuple termTuple;
        //读取三元组流中的三元组对象并构造列表
        if((termTuple=termTupleStream.next()) != null){
            list.add(termTuple);
        }
        //关闭流
        termTupleStream.close();
        //构建Document对象
        return new Document(docId, docPath, list);
    }

    @Override
    public AbstractDocument build(int docId, String docPath, File file){
        //缓冲读取器用于读取文档
        BufferedReader reader;
        //三元组扫描从文档中读取内容
        AbstractTermTupleStream scanner;

    }
}
