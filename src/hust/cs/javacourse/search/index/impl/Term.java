package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractTerm;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Term extends AbstractTerm {
    public Term(){

    }

    public Term(String term) {
        super(term);
    }

    @Override
    public String toString() {
        return this.content;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Term term) {
            return term.content.equals(this.content);
        }
        return false;
    }

    @Override
    public String getContent(){
        return this.content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int compareTo(AbstractTerm o) {
        return content.compareTo(o.getContent());
    }

    @Override
    public void readObject(ObjectInputStream in) {
        try{
            this.content=(String) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeObject(ObjectOutputStream out) {
        try{
            out.writeObject(this.content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
