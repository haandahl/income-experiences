package com.heidiaandahl.persistence;

import com.heidiaandahl.entity.User;
import com.heidiaandahl.persistence.SessionFactoryProvider;
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
 * The data access object (DAO) for the <code style="color: gray; font-size: 0.8em;">User</code>.
 *
 * @author Heidi Aandahl
 */
public class UserDao {

    private final Logger logger = LogManager.getLogger(this.getClass());
    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();


    /**
     * Inserts a new <code style="color: gray; font-size: 0.8em;">User</code> to the database.
     *
     * @param user the new user
     * @return the Id assigned by the database //TODO verify conceptually (already verified 1x in debugging)
     */
    public int insert(User user) {
        int id = 0;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        id = (int)session.save(user);

        transaction.commit();
        session.close();

        return id;
    }

    /**
     * Retrieves a user by Id.
     *
     * @param id the Id of the user in the database
     * @return the user with the Id requested
     */
    public User getById(int id) {
        Session session = sessionFactory.openSession();
        User user = session.get(User.class, id);
        session.close();
        return user;
    }

    /**
     * Retrieves a user by property name and value.
     *
     * @param propertyName the property name
     * @param value        the value of that property
     * @return the list of users with the property name and value queried
     */
    public List<User> getByPropertyName(String propertyName, String value) {
        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);

        query.select(root).where(builder.equal(root.get(propertyName), value));
        List<User> users = session.createQuery(query).getResultList();

        session.close();
        return users;
    }

    /**
     * Saves a user with a new Id or updates a user with an existing Id.
     *
     * @param user the user to be saved or updated
     */
    public void saveOrUpdate(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.saveOrUpdate(user);

        transaction.commit();
        session.close();
    }

    /**
     * Deletes a user.
     *
     * @param user the user to be deleted.
     */
    public void delete(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.delete(user);

        transaction.commit();
        session.close();
    }

    // TODO get all users by number of removals or admin edits associated with their account
    // TODO get all users with income near the search criteria and the same family size
    // Finish Week 5 and Lookup Hibernate Search before digging in

}
