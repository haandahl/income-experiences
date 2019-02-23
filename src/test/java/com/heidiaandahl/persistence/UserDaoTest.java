package com.heidiaandahl.persistence;

import com.heidiaandahl.entity.User;
import com.heidiaandahl.test.util.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDaoTest {

    UserDao dao;

    @BeforeEach
    void setUp() {
        dao = new UserDao();

        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
    }
/*
    @Test
    void updateSuccess() {
        int newRole = 3;  //cleandb sets original role to 4

        User userToUpdate = dao.getByPropertyName("id", "8");
        userToUpdate.setRole(newRole);

        dao.saveOrUpdate(userToUpdate);
        User retrievedUser = dao.getByPropertyName("id", "8");

        assertEquals(newRole, retrievedUser.getRole());

    }

    */

    @Test
    void updateSuccess() {
        int newRole = 3;  //cleandb sets original role to 4

        User userToUpdate = dao.getById(8);
        userToUpdate.setRole(newRole);

        dao.saveOrUpdate(userToUpdate);
        User retrievedUser = dao.getById(8);

        assertEquals(newRole, retrievedUser.getRole());
    }

}
