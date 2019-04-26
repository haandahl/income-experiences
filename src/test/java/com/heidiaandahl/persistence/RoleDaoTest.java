package com.heidiaandahl.persistence;

import com.heidiaandahl.entity.Role;
import com.heidiaandahl.entity.User;
import com.heidiaandahl.test.util.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for <code style="color: gray; font-size: 0.8em;">GenericDao</code> used with the
 * <code style="color: gray; font-size: 0.8em;">Role</code> class.
 *
 * @author Heidi Aandahl
 */
public class RoleDaoTest {
    GenericDao roleDao;

    /**
     * Sets up a new <code style="color: gray; font-size: 0.8em;">GenericDao</code> and
     * refreshes the test database before each unit test.
     */
    @BeforeEach
    void setUp() {
        roleDao = new GenericDao(Role.class);

        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
    }

    @Test
    void addSuccess() {
        GenericDao userDao = new GenericDao(User.class);
        User testUser = new User("anne", "password9");
        int userId = userDao.insert(testUser);

        Role testRole = new Role("user", testUser);

        int id = roleDao.insert(testRole);

        Role insertedRole = (Role) roleDao.getById(id);

        assertNotEquals(0, id);
        assertEquals(testRole, insertedRole);
    }

    // todo - my intention was to have the admin change the user roles,
    //  but that only makes sense if a user is only allowed to have one role.  Since that's not the deal (in theory),
    //  the admin would actually delete the wrong role and add the right role.


}
