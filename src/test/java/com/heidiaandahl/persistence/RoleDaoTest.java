package com.heidiaandahl.persistence;

import com.heidiaandahl.entity.Role;
import com.heidiaandahl.entity.User;
import com.heidiaandahl.test.util.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Test
    void removeSuccess() {
        /*  some data in cleandb:
            16	read	mary
            17	write	mary

            8	mary	password7
         */

        GenericDao userDao = new GenericDao(User.class);
        User testUser = (User) userDao.getById(8);

        // Map the search criteria
        Map<String, Object> testMap = new HashMap<>();
        testMap.put("username", testUser);
        testMap.put("name", "write");

        Role testRole = ((List<Role>) roleDao.getByPropertyNames(testMap)).get(0);

        roleDao.delete(testRole);

        List<Role> remainingRoles = (List<Role>) roleDao.getAll();

        assertFalse(remainingRoles.contains(testRole));
    }

}
