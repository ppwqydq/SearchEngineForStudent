package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractTermTuple;

import java.util.List;

/**
 * Document类是AbstractDocument的具体实现类
 */
public class Document extends AbstractDocument {
    /**
     * 默认构造函数
     */
    public Document() {
        super();
    }

    /**
     * 构造函数
     * @param docID 文档ID
     * @param docPath 文档路径
     */
    public Document(int docID, String docPath) {
        super(docID, docPath);
    }

    /**
     * 构造函数
     * @param docID 文档ID
     * @param docPath 文档路径
     * @param tuples TermTuple列表
     */
    public Document(int docID, String docPath, List<AbstractTermTuple> tuples) {
        super(docID, docPath, tuples);
    }

    /**
     * 获取文档ID
     * @return 文档ID
     */
    @Override
    public int getDocId() {
        return this.docId;
    }

    /**
     * 设置文档ID
     * @param docID 要设置的文档ID
     */
    @Override
    public void setDocId(int docID) {
        this.docId = docID;
    }

    /**
     * 获取文档路径
     * @return 文档路径
     */
    @Override
    public String getDocPath() {
        return this.docPath;
    }

    /**
     * 设置文档路径
     * @param docPath 要设置的文档路径
     */
    @Override
    public void setDocPath(String docPath) {
        this.docPath = docPath;
    }

    /**
     * 获取TermTuple列表
     * @return TermTuple列表
     */
    @Override
    public List<AbstractTermTuple> getTuples() {
        return this.tuples;
    }

    /**
     * 向文档中添加一个TermTuple
     * @param tuple 要添加的TermTuple
     */
    @Override
    public void addTuple(AbstractTermTuple tuple) {
        if (!this.tuples.contains(tuple)) {
            this.tuples.add(tuple);
        }
    }

    /**
     * 检查文档中是否包含指定的TermTuple
     * @param tuple 要检查的TermTuple
     * @return 如果包含则返回true，否则返回false
     */
    @Override
    public boolean contains(AbstractTermTuple tuple) {
        return this.tuples.contains(tuple);
    }

    /**
     * 根据索引获取TermTuple
     * @param index 要获取的TermTuple的索引
     * @return 指定索引处的TermTuple
     */
    @Override
    public AbstractTermTuple getTuple(int index) {
        return this.tuples.get(index);
    }

    /**
     * 获取文档中TermTuple的数量
     * @return TermTuple的数量
     */
    @Override
    public int getTupleSize() {
        return this.tuples.size();
    }

    /**
     * 返回文档的字符串表示形式
     * @return 文档的字符串表示
     */
    @Override
    public String toString() {
        return "docId: " + this.docId + ", docPath: " + this.docPath + ", tuples: " + this.tuples;
    }
}
