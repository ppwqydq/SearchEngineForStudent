package hust.cs.javacourse.search.query.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.query.AbstractHit;

import java.util.Map;

/**
 * Hit类是搜索结果的具体实现类，表示搜索引擎返回的一个搜索结果。
 * 一个Hit对象对应一个文档和该文档的得分等信息。
 */
public class Hit extends AbstractHit {
    /**
     * 默认构造函数。
     */
    public Hit() {

    }

    /**
     * 构造函数。
     * @param docID 文档ID。
     * @param docPath 文档路径。
     */
    public Hit(int docID, String docPath) {
        super(docID, docPath);
    }

    /**
     * 构造函数。
     * @param docId 文档ID。
     * @param docPath 文档路径。
     * @param termPostingMapping 一个包含了检索词和对应Posting(包含位置信息)的映射表。
     */
    public Hit(int docId, String docPath, Map<AbstractTerm, AbstractPosting> termPostingMapping){
        super(docId, docPath, termPostingMapping);
    }

    /**
     * 获取文档ID。
     * @return 文档ID。
     */
    @Override
    public int getDocId(){
        return this.docId;
    }

    /**
     * 获取文档路径。
     * @return 文档路径。
     */
    @Override
    public String getDocPath() {
        return this.docPath;
    }

    /**
     * 获取文档内容。
     * @return 文档内容。
     */
    @Override
    public String getContent() {
        return this.content;
    }

    /**
     * 设置文档内容。
     * @param content 文档内容。
     */
    @Override
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取文档得分。
     * @return 文档得分。
     */
    @Override
    public double getScore() {
        return this.score;
    }

    /**
     * 设置文档得分。
     * @param score 文档得分。
     */
    @Override
    public void setScore(double score) {
        this.score = score;
    }

    /**
     * 获取检索词和对应Posting的映射表。
     * @return 检索词和对应Posting的映射表。
     */
    @Override
    public Map<AbstractTerm, AbstractPosting> getTermPostingMapping() {
        return this.termPostingMapping;
    }

    /**
     * 返回Hit对象的字符串表示形式，方便打印查看。
     * @return Hit对象的字符串表示。
     */
    @Override
    public String toString() {
        return "Hit{" +
                "docId=" + docId +
                ", docPath='" + docPath + '\'' +
                ", content='" + content + '\'' +
                ", score=" + score +
                ", termPostingMapping=" + termPostingMapping +
                '}';
    }

    /**
     * 实现比较接口，根据得分比较两个Hit对象。
     * @param obj 另一个Hit对象。
     * @return 比较的结果。如果当前对象得分高，返回正数；如果得分低，返回负数；得分相同，返回0。
     */
    @Override
    public int compareTo(AbstractHit obj) {
        return (int) (this.score - obj.getScore());
    }
}
