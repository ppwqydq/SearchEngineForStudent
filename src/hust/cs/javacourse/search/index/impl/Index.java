package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.*;

import java.io.*;
import java.util.*;

public class Index extends AbstractIndex {

    public Index() {

    }

    @Override
    public String toString(){
        if (docIdToDocPathMapping.isEmpty() && termToPostingListMapping.isEmpty()){
            return null;
        }
        else{
            return "docIdToDocPathMap:\n" + docIdToDocPathMapping.toString() + "\ntermToPostingListMap:\n" + termToPostingListMapping.toString();
        }
    }

    @Override
    public void addDocument(AbstractDocument document){
        //存储每个单词在文档中出现位置
        HashMap<AbstractTerm, List<Integer>> map = new HashMap<>();
        for (AbstractTermTuple termTuple : document.getTuples()) {
            //单词尚未加入HashMap
            if (!map.containsKey(termTuple.term)) {
                //创建新列表存储出现位置
                map.put(termTuple.term, new ArrayList<>());
                //将其在文档中出现位置加入列表
                map.get(termTuple.term).add(termTuple.curPos);
            }
            //已加入直接加入列表
            else map.get(termTuple.term).add(termTuple.curPos);
        }
        //更新索引
        for (Map.Entry<AbstractTerm, List<Integer>> entry : map.entrySet()) {
            if (!this.termToPostingListMapping.containsKey(entry.getKey())) {
                // 如果索引中不存在该单词创建一个新的倒排链
                termToPostingListMapping.put(entry.getKey(), new PostingList());
            }
            //将该词项在当前文档中出现的位置信息添加到倒排链中
            termToPostingListMapping.get(entry.getKey()).add(new Posting(document.getDocId(), entry.getValue().size(), entry.getValue()));
        }
        // 将文档ID和文档路径存储到映射中
        docIdToDocPathMapping.put(document.getDocId(), document.getDocPath());
    }

    @Override
    public Set<AbstractTerm> getDictionary(){
        return termToPostingListMapping.keySet();
    }

    @Override
    public String getDocName(int docId){
        return docIdToDocPathMapping.get(docId);
    }

    @Override
    public void load(File file) {
        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            readObject(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void optimize(){
        for(Map.Entry<AbstractTerm,AbstractPostingList> entry: termToPostingListMapping.entrySet()){
            entry.getValue().sort();
            for (int i = 0; i < entry.getValue().size(); i++)
                Collections.sort(entry.getValue().get(i).getPositions());
        }
    }

    @Override
    public void save(File file) {
        try{
            ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(file));
            writeObject(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AbstractPostingList search(AbstractTerm term){
        return termToPostingListMapping.get(term);
    }

    @Override
    public void writeObject(ObjectOutputStream out) {
        try{
            out.writeObject(docIdToDocPathMapping);
            out.writeObject(termToPostingListMapping);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void readObject(ObjectInputStream in) {
        try{
            docIdToDocPathMapping=(Map<Integer,String>)in.readObject();
            termToPostingListMapping=(Map<AbstractTerm, AbstractPostingList>)in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
