package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractTerm;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

// Term类继承自AbstractTerm抽象类，用于表示一个文档中的一个单词
public class Term extends AbstractTerm {
    // 默认构造函数
    public Term(){

    }

    // 带参数的构造函数，用于初始化Term对象
    public Term(String term) {
        super(term);
    }

    // 重写toString方法，返回Term的字符串表示形式
    @Override
    public String toString() {
        return this.content;
    }

    // 重写equals方法，比较两个Term对象是否相等
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Term term) {
            return term.content.equals(this.content);
        }
        return false;
    }

    // 获取Term的内容
    @Override
    public String getContent(){
        return this.content;
    }

    // 设置Term的内容
    @Override
    public void setContent(String content) {
        this.content = content;
    }

    // 实现Comparable接口的compareTo方法，用于比较两个Term对象的顺序
    @Override
    public int compareTo(AbstractTerm o) {
        return content.compareTo(o.getContent());
    }

    // 实现序列化接口的readObject方法，用于从输入流中读取Term对象
    @Override
    public void readObject(ObjectInputStream in) {
        try{
            this.content=(String) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // 实现序列化接口的writeObject方法，用于将Term对象写入输出流
    @Override
    public void writeObject(ObjectOutputStream out) {
        try{
            out.writeObject(this.content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
