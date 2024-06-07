package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractPosting;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Posting extends AbstractPosting {
    // 默认构造函数
    public Posting(){

    }

    // 带参数的构造函数
    public Posting(int docId, int freq, List<Integer> positions) {
        super(docId, freq, positions);
    }

    // 重写equals方法，用于比较两个Posting对象是否相等
    @Override
    public boolean equals(Object obj){
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Posting posting = (Posting) obj;
        Set<Integer> positions1 = new HashSet<>(positions);
        Set<Integer> positions2 = new HashSet<>(posting.positions);
        return docId == posting.docId && freq == posting.freq && positions1.equals(positions2);
    }

    // 重写toString方法，用于输出Posting对象的信息
    @Override
    public String toString(){
        return "docId:"+this.docId+",freq:"+this.freq+",positions:"+this.positions;
    }

    // 获取文档ID
    @Override
    public int getDocId(){
        return this.docId;
    }

    // 获取词频
    @Override
    public int getFreq(){
        return this.freq;
    }

    // 设置文档ID
    @Override
    public void setDocId(int docId){
        this.docId=docId;
    }

    // 设置词频
    @Override
    public void setFreq(int freq){
        this.freq=freq;
    }

    // 设置位置列表
    @Override
    public void setPositions(List<Integer> positions){
        this.positions=positions;
    }

    // 获取位置列表
    @Override
    public List<Integer> getPositions(){
        return this.positions;
    }

    // 实现比较方法，用于排序
    @Override
    public int compareTo(AbstractPosting o) {
        return this.docId-((Posting)o).docId;
    }

    // 对位置列表进行排序
    @Override
    public void sort(){
        Collections.sort(this.positions);
    }

    // 将Posting对象写入到输出流
    @Override
    public void writeObject(ObjectOutputStream out){
        try{
            out.writeInt(this.docId);
            out.writeInt(this.freq);
            out.writeObject(this.positions);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 从输入流中读取Posting对象
    @Override
    public void readObject(ObjectInputStream in){
        try{
            this.docId=in.readInt();
            this.freq=in.readInt();
            this.positions= (List<Integer>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
