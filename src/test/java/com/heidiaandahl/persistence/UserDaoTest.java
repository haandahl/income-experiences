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

        //RESOLVED//[ERROR] 2019-02-23 15:06:28.791 [main] SqlExceptionHelper - Unknown column 'user0_.user_name' in 'field list'

        //2019-02-23 15:16:00,727 main ERROR Unable to rename file C:\Users\idile\IdeaProjects\incomeexperiences\.\logs\hibernate.log to C:\Users\idile\IdeaProjects\incomeexperiences\.\logs\hibernate_20190223 15:06.log: java.nio.file.InvalidPathException Illegal char <:> at index 74: C:\Users\idile\IdeaProjects\incomeexperiences\.\logs\hibernate_20190223 15:06.log
    }

}
