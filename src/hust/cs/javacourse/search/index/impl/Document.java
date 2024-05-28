package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractTermTuple;

import java.util.List;

public class Document extends AbstractDocument {
    public Document() {
        super();
    }

    public Document(int docID,String docPath) {
        super(docID,docPath);
    }

    public Document(int docID, String docPath, List<AbstractTermTuple> tuples) {
        super(docID, docPath, tuples);
    }

    @Override
    public int getDocId(){
        return this.docId;
    }

    @Override
    public void setDocId(int docID){
        this.docId = docID;
    }

    @Override
    public String getDocPath(){
        return this.docPath;
    }

    @Override
    public void setDocPath(String docPath){
        this.docPath = docPath;
    }

    @Override
    public List<AbstractTermTuple> getTuples(){
        return this.tuples;
    }

    @Override
    public void addTuple(AbstractTermTuple tuple){
        if(!this.tuples.contains(tuple)){
            this.tuples.add(tuple);
        }
    }

    @Override
    public boolean contains(AbstractTermTuple tuple){
        return this.tuples.contains(tuple);
    }

    @Override
    public AbstractTermTuple getTuple(int index){
        return this.tuples.get(index);
    }

    @Override
    public int getTupleSize(){
        return this.tuples.size();
    }

    @Override
    public String toString(){
        return "docId: " + this.docId + ", docPath: " + this.docPath + ", tuples: " + this.tuples;
    }
}
