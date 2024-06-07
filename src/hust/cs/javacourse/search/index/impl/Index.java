package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.*;

import java.io.*;
import java.util.*;

public class Index extends AbstractIndex {

    public Index() {
        // 构造函数，创建一个新的Index对象
    }

    @Override
    public String toString(){
        // 如果文档ID到文档路径的映射和术语到倒排列表的映射都为空，则返回null
        if (docIdToDocPathMapping.isEmpty() && termToPostingListMapping.isEmpty()){
            return null;
        }
        else{
            // 否则，返回映射的字符串表示形式
            return "docIdToDocPathMap:\n" + docIdToDocPathMapping + "\ntermToPostingListMap:\n" + termToPostingListMapping.toString();
        }
    }

    @Override
    public void addDocument(AbstractDocument document){
        // 用于存储每个单词在文档中的出现位置
        HashMap<AbstractTerm, List<Integer>> map = new HashMap<>();
        for (AbstractTermTuple termTuple : document.getTuples()) {
            // 如果单词还没有加入HashMap
            if (!map.containsKey(termTuple.term)) {
                // 创建一个新的列表来存储出现的位置
                map.put(termTuple.term, new ArrayList<>());
            }
            // 将单词在文档中的出现位置添加到列表中
            map.get(termTuple.term).add(termTuple.curPos);
        }
        // 更新索引
        for (Map.Entry<AbstractTerm, List<Integer>> entry : map.entrySet()) {
            if (!this.termToPostingListMapping.containsKey(entry.getKey())) {
                // 如果索引中不存在该单词，则创建一个新的倒排链
                termToPostingListMapping.put(entry.getKey(), new PostingList());
            }
            // 将单词在当前文档中的位置信息添加到倒排链中
            termToPostingListMapping.get(entry.getKey()).add(new Posting(document.getDocId(), entry.getValue().size(), entry.getValue()));
        }
        // 将文档ID和文档路径存储到映射中
        docIdToDocPathMapping.put(document.getDocId(), document.getDocPath());
    }

    @Override
    public Set<AbstractTerm> getDictionary(){
        // 返回索引中所有单词的集合
        return termToPostingListMapping.keySet();
    }

    @Override
    public String getDocName(int docId){
        // 根据文档ID获取文档的路径
        return docIdToDocPathMapping.get(docId);
    }

    @Override
    public void load(File file) {
        // 从文件中加载索引
        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            readObject(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void optimize(){
        // 优化索引，排序每个倒排列表和列表中的位置信息
        for(Map.Entry<AbstractTerm,AbstractPostingList> entry: termToPostingListMapping.entrySet()){
            entry.getValue().sort();
            for (int i = 0; i < entry.getValue().size(); i++)
                Collections.sort(entry.getValue().get(i).getPositions());
        }
    }

    @Override
    public void save(File file) {
        // 将索引保存到文件
        try{
            ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(file));
            writeObject(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AbstractPostingList search(AbstractTerm term){
        // 根据单词搜索倒排列表
        return termToPostingListMapping.get(term);
    }

    @Override
    public void writeObject(ObjectOutputStream out) {
        // 将索引的映射写入到输出流中
        try{
            out.writeObject(docIdToDocPathMapping);
            out.writeObject(termToPostingListMapping);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void readObject(ObjectInputStream in) {
        // 从输入流中读取索引的映射
        try{
            docIdToDocPathMapping=(Map<Integer,String>)in.readObject();
            termToPostingListMapping=(Map<AbstractTerm, AbstractPostingList>)in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
