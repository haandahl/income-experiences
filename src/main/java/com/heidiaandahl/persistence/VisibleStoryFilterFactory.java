package com.heidiaandahl.persistence;
// todo verify imports
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

public class VisibleStoryFilterFactory {
    @org.hibernate.search.annotations.Factory
    public Query create() {
        return new TermQuery( new Term( "isVisible", String.valueOf(true)));
    }

}
