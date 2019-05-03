package com.heidiaandahl.persistence;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

/**
 * A filter factory used to limit full text searches for stories based on their "visible" status.
 *
 * @author Heidi Aandahl
 */
public class VisibleStoryFilterFactory {
    /**
     * Creates a query for "isVisible" values of "true".
     *
     * @return the query
     */
    @org.hibernate.search.annotations.Factory
    public Query create() {
        return new TermQuery( new Term( "isVisible", "true"));
    }
}
