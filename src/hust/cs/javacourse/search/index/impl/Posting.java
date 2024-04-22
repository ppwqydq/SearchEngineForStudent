package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractPosting;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;

public class Posting extends AbstractPosting {
    public Posting(){

    }

    public Posting(int docId, int freq, List<Integer> positions) {
        super(docId, freq, positions);
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Posting p){
            return this.docId==p.docId&&this.freq==p.freq&&this.positions.equals(p.positions);
        }
        return false;
    }

    @Override
    public String toString(){
        return "docId:"+this.docId+",freq:"+this.freq+",positions:"+this.positions;
    }

    @Override
    public int getDocId(){
        return this.docId;
    }

    @Override
    public int getFreq(){
        return this.freq;
    }

    @Override
    public void setDocId(int docId){
        this.docId=docId;
    }

    @Override
    public void setFreq(int freq){
        this.freq=freq;
    }

    @Override
    public void setPositions(List<Integer> positions){
        this.positions=positions;
    }

    @Override
    public List<Integer> getPositions(){
        return this.positions;
    }

    @Override
    public int compareTo(AbstractPosting o) {
        return this.docId-((Posting)o).docId;
    }

    @Override
    public void sort(){
        Collections.sort(this.positions);
    }

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
