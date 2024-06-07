package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractPostingList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;

public class PostingList extends AbstractPostingList {
    // 构造函数
    public PostingList() {

    }

    // 带参数的构造函数，接收一个AbstractPosting列表
    public PostingList(List<AbstractPosting> list){
        this.list = list;
    }

    // 添加一个posting到列表中，如果已存在则不添加
    @Override
    public void add(AbstractPosting posting){
        if (this.list.contains(posting)) {
            return;
        }
        this.list.add(posting);
    }

    // 重写toString方法，返回列表的字符串表示形式
    @Override
    public String toString() {
        return list.toString();
    }

    // 添加一个posting列表到当前列表中
    @Override
    public void add(List<AbstractPosting> postings) {
        for (AbstractPosting posting : postings) {
            this.add(posting);
        }
    }

    // 获取指定索引位置的posting
    @Override
    public AbstractPosting get(int index) {
        return list.get(index);
    }

    // 返回指定posting的索引位置
    @Override
    public int indexOf(AbstractPosting posting) {
        return list.indexOf(posting);
    }

    // 根据文档ID查找posting的索引位置
    @Override
    public int indexOf(int docId){
        for(int i = 0; i < list.size(); i++)
            if (list.get(i).getDocId() == docId) {
                return i;
            }
        return -1;
    }

    // 检查列表是否包含指定的posting
    @Override
    public boolean contains(AbstractPosting posting) {
        return list.contains(posting);
    }

    // 根据索引位置移除posting
    @Override
    public void remove(int index){
        list.remove(index);
    }

    // 移除指定的posting
    @Override
    public void remove(AbstractPosting posting){
        list.remove(posting);
    }

    // 获取列表的大小
    @Override
    public int size() {
        return list.size();
    }

    // 清空列表
    @Override
    public void clear() {
        list.clear();
    }

    // 检查列表是否为空
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    // 对列表进行排序
    @Override
    public void sort(){
        Collections.sort(list);
    }

    // 将列表写入到输出流
    @Override
    public void writeObject(ObjectOutputStream out){
        try{
            out.writeObject(list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 从输入流读取列表
    @Override
    public void readObject(ObjectInputStream in){
        try{
            this.list = (List<AbstractPosting>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
