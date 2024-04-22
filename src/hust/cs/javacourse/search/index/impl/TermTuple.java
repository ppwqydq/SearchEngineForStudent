package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.index.AbstractTermTuple;

public class TermTuple extends AbstractTermTuple {
    public TermTuple(){

    }

    public TermTuple(AbstractTerm term,int curPos){
        this.term = term;
        this.curPos = curPos;
    }

    @Override
    public String toString(){
        return "term: "+this.term.toString()+",position: "+this.curPos+",freq: "+this.freq;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof TermTuple t){
            return this.term.equals(t.term) && this.curPos == t.curPos;
        }
        return false;
    }
}
