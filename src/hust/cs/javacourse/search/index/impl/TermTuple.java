package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.index.AbstractTermTuple;

/**
 * TermTuple 是 AbstractTermTuple 的具体实现类.
 * 一个TermTuple对象表示文档中的一个单词及其出现的位置（位置索引）.
 */
public class TermTuple extends AbstractTermTuple {
    /**
     * 默认构造函数.
     */
    public TermTuple(){

    }

    /**
     * 构造函数，初始化term和curPos.
     * @param term 单词对象.
     * @param curPos 单词在文档中的位置索引.
     */
    public TermTuple(AbstractTerm term,int curPos){
        this.term = term;
        this.curPos = curPos;
    }

    /**
     * 返回TermTuple的字符串表示形式.
     * @return TermTuple的字符串表示，包含term的字符串表示、位置和频率.
     */
    @Override
    public String toString(){
        return "term: "+this.term.toString()+",position: "+this.curPos+",freq: "+this.freq;
    }

    /**
     * 判断两个TermTuple是否相等.
     * @param obj 要比较的另一个对象.
     * @return 如果两个TermTuple的term和curPos都相等，返回true；否则返回false.
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof TermTuple t){
            return this.term.equals(t.term) && this.curPos == t.curPos;
        }
        return false;
    }
}
