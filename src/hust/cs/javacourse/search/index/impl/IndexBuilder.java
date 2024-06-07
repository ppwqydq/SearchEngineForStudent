package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractDocumentBuilder;
import hust.cs.javacourse.search.index.AbstractIndex;
import hust.cs.javacourse.search.index.AbstractIndexBuilder;
import hust.cs.javacourse.search.util.FileUtil;

import java.io.File;

public class IndexBuilder extends AbstractIndexBuilder {
    /**
     * 构造函数
     * @param docBuilder 文档构建器对象，用于构建文档
     */
    public IndexBuilder(AbstractDocumentBuilder docBuilder){
        super(docBuilder);
    }

    /**
     * 根据指定的根目录构建索引
     * @param rootDirectory 根目录的路径
     * @return 构建好的索引对象
     */
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
