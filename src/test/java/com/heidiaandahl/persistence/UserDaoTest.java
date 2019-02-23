package com.heidiaandahl.persistence;

import com.heidiaandahl.UserDao;
import com.heidiaandahl.test.util.Database;
import org.junit.jupiter.api.BeforeEach;

public class UserDaoTest {

    UserDao dao;

    @BeforeEach
    void setUp() {
        dao = new UserDao();

        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
    }
}
