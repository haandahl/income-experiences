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

public class UserDao {

    private final Logger logger = LogManager.getLogger(this.getClass());
    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();


    public int insert(User user) {
        int id = 0;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        id = (int)session.save(user);

        transaction.commit();
        session.close();

        return id;
    }

    public User getById(int id) {
        Session session = sessionFactory.openSession();
        User user = session.get(User.class, id);
        session.close();
        return user;
    }

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

    public void saveOrUpdate(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.saveOrUpdate(user);

        transaction.commit();
        session.close();
    }

    public void delete(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.delete(user);

        transaction.commit();
        session.close();
    }


    // TODO refactor ideas??
    // doTransaction
    // doQuery
    // but what could I pass into those?  I can't just make a where-clause and pass it in can I? or pass some other function in?
    // all the parts seem so interdependent...




    // TODO get all users by number of removals or admin edits associated with their account
    // Finish Week 5 and Lookup Hibernate Search before digging in

}
