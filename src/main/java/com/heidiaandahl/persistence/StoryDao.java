package com.heidiaandahl.persistence;

import com.heidiaandahl.entity.Story;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * The data access object (DAO) for the <code style="color: gray; font-size: 0.8em;">Story</code>.
 *
 * @author Heidi Aandahl
 */
public class StoryDao {

    private final Logger logger = LogManager.getLogger(this.getClass());
    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();


    /**
     * Inserts a new <code style="color: gray; font-size: 0.8em;">Story</code> to the database.
     *
     * @param story the new story
     * @return the Id assigned by the database
     */
    public int insert(Story story) {
        int id = 0;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        id = (int)session.save(story);

        transaction.commit();
        session.close();

        return id;
    }

    /**
     * Retrieves a story by Id.
     *
     * @param id the Id of the story in the database
     * @return the story with the Id requested
     */
    public Story getById(int id) {
        Session session = sessionFactory.openSession();
        Story story = session.get(Story.class, id); // Now I'm getting org.hibernate.exception.SQLGrammarException: could not extract ResultSet
        session.close();
        return story;
    }

    /**
     * Retrieves a story by property name and value.
     *
     * @param propertyName the property name
     * @param value        the value of that property
     * @return the list of storys with the property name and value queried
     */
    public List<Story> getByPropertyName(String propertyName, String value) {
        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Story> query = builder.createQuery(Story.class);
        Root<Story> root = query.from(Story.class);

        query.select(root).where(builder.equal(root.get(propertyName), value));
        List<Story> storys = session.createQuery(query).getResultList();
        session.close();
        return storys;
    }

    /**
     * Saves a story with a new Id or updates a story with an existing Id.
     *
     * @param story the story to be saved or updated
     */
    public void saveOrUpdate(Story story) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.saveOrUpdate(story);

        transaction.commit();
        session.close();
    }

    /**
     * Deletes a story.
     *
     * @param story the story to be deleted.
     */
    public void delete(Story story) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.delete(story);

        transaction.commit();
        session.close();
    }
}
