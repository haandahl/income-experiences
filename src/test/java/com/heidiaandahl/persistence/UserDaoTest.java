package com.heidiaandahl.persistence;

import com.heidiaandahl.entity.User;
import com.heidiaandahl.test.util.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserDaoTest {

    UserDao dao;

    @BeforeEach
    void setUp() {
        dao = new UserDao();

        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
    }

    @Test
    void updateSuccess() {
        int newRole = 3;  //cleandb sets original role to 4

        User userToUpdate = dao.getById(8);
        userToUpdate.setRole(newRole);

        dao.saveOrUpdate(userToUpdate);
        User retrievedUser = dao.getById(8);

        assertEquals(newRole, retrievedUser.getRole());
    }

    @Test
    void addSuccess() {
        User newUser = new User("anne", "password9", 2);
        int id = dao.insert(newUser);
        assertNotEquals(0,id);
        User insertedUser = dao.getById(id);
        assertEquals("anne", insertedUser.getUsername());
        assertEquals("password9", insertedUser.getPassword());
        assertEquals(2, insertedUser.getRole());
        assertEquals(9, insertedUser.getId());
        // Instead of comparing all values
        // it may make sense to use .equals()
        // TODO review .equals recommendations http://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#mapping-model-pojo-equalshashcode
    }

}
