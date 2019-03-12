package com.heidiaandahl.persistence;

import com.heidiaandahl.persistence.SessionFactoryProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A generic data access object (DAO) following the model from Paula Waite's Enterprise Java Course,
 * originally inspired in part by http://rodrigouchoa.wordpress.com.
 *
 * @param <T> the type parameter
 * @author Heidi Aandahl
 */
public class GenericDao<T> {
    private Class<T> type;
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Instantiates a new Generic dao.
     *
     * @param type the type
     */
    public GenericDao(Class<T> type) {
        this.type = type;
    }

    /**
     * Returns an open session from the SessionFactory.
     */
    private Session getSession() {
        return SessionFactoryProvider.getSessionFactory().openSession();
    }


    /**
     * Inserts an entity
     *
     * @param entity the entity
     * @return the entity id
     */
    public int insert(T entity) {
        int id = 0;
        Session session = getSession();
        Transaction transaction = session.beginTransaction();

        id = (int)session.save(entity);

        transaction.commit();
        session.close();

        return id;
    }

    /**
     * Gets all entities
     *
     * @return the all entities
     */
    public List<T> getAll() {
        Session session = getSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);
        List<T> list = session.createQuery(query).getResultList();
        session.close();
        return list;

    }

    /**
     * Gets an entity by id.
     *
     * @param <T> the type of entity
     * @param id  the id of the entity
     * @return the id of the entity
     */
    public <T> T getById(int id) {
        Session session = getSession();
        T entity = (T)session.get(type, id);
        session.close();
        return entity;
    }

     /**
     * Gets entities by property name and value when the value is a String.
     *
     * @param propertyName the property name
     * @param value        the property value
     * @return the entities matching the query
     */
    public List<T> getByPropertyName(String propertyName, String value) {
        Session session = getSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);

        query.select(root).where(builder.equal(root.get(propertyName), value));
        List<T> entities = session.createQuery(query).getResultList();
        session.close();
        return entities;
    }

    /**
     * Gets entities by property name and value when the value is a boolean.
     *
     * @param propertyName the property name
     * @param value        the property value
     * @return the entities matching the query
     */
    public List<T> getByPropertyName(String propertyName, boolean value) {
        Session session = getSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);

        query.select(root).where(builder.equal(root.get(propertyName), value));
        List<T> entities = session.createQuery(query).getResultList();
        session.close();
        return entities;
    }

    /**
     * Gets entities by property name and value when the value is an int.
     *
     * @param propertyName the property name
     * @param value        the property value
     * @return the entities matching the query
     */
    public List<T> getByPropertyName(String propertyName, int value) {
        Session session = getSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);

        query.select(root).where(builder.equal(root.get(propertyName), value));
        List<T> entities = session.createQuery(query).getResultList();
        session.close();
        return entities;
    }

    /**
     * Finds entities by multiple properties.
     * Written by Paula Waite (with slight modifications)
     * Inspired by https://stackoverflow.com/questions/11138118/really-dynamic-jpa-criteriabuilder
     * @param propertyMap property and value pairs
     * @return entities with properties equal to those passed in the map
     */
    public List<T> getByPropertyNames(Map<String, Object> propertyMap) {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);
        List<Predicate> predicates = new ArrayList<Predicate>();
        for (Map.Entry entry: propertyMap.entrySet()) {
            predicates.add(builder.equal(root.get((String) entry.getKey()), entry.getValue()));
        }
        query.select(root).where(builder.and(predicates.toArray(new Predicate[predicates.size()])));

        List<T> entities = session.createQuery(query).getResultList();
        session.close();

        return entities;
    }

     /**
     * Gets by property where the search String is contained in the value.
     *
     * @param propertyName the property name
     * @param value        the search String
     * @return the entities matching the query
     */
//TODO determine whether this is still needed with Hibernate Search
    public List<T> getByPropertyLike(String propertyName, String value) {
        Session session = getSession();

        logger.debug("Searching for story with {} = {}",  propertyName, value);

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);
        Expression<String> propertyPath = root.get(propertyName);

        query.where(builder.like(propertyPath, "%" + value + "%"));

        List<T> entities = session.createQuery(query).getResultList();
        session.close();
        return entities;
    }

    /**
     * Save or update.
     *
     * @param entity the entity
     */
    public void saveOrUpdate(T entity) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();

        session.saveOrUpdate(entity);

        transaction.commit();
        session.close();
    }


    /**
     * Deletes the entity.
     *
     * @param entity entity to be deleted
     */
    public void delete(T entity) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(entity);
        transaction.commit();
        session.close();
    }

}
