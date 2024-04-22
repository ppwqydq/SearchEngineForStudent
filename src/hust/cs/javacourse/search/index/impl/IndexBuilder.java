package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractDocumentBuilder;
import hust.cs.javacourse.search.index.AbstractIndex;
import hust.cs.javacourse.search.index.AbstractIndexBuilder;
import hust.cs.javacourse.search.util.FileUtil;

import java.io.File;

public class IndexBuilder extends AbstractIndexBuilder {

    public IndexBuilder(AbstractDocumentBuilder docBuilder){
        super(docBuilder);
    }

    @Override
    public AbstractIndex buildIndex(String rootDirectory) {
        //创建新的索引对象
        AbstractIndex index = new Index();
        //遍历目录下所有文件
        for (String path : FileUtil.list(rootDirectory)) {
            //使用文档构建器构造每个文件对应的文档对象并添加到索引中
            AbstractDocument document = docBuilder.build(docId++, path, new File(path));
            index.addDocument(document);
        }
        return index;
    }
}
