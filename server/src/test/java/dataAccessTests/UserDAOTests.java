package dataAccessTests;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import model.UserData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UserDAOTests {
    private static UserDAO userDAO;

    private final UserData userOne = new UserData("mdaven19", "notGonnaTe11Yo0", "mdaven19@byu.edu");

    private final UserData userTwo = new UserData("19mdavenport", "n0tG0nnaTe11Yo0", "19mdavenport@gmail.com");

    @BeforeAll
    public static void beforeAll() throws DataAccessException {
        DataAccess dataAccess = TestFactory.getDataAccess();
        userDAO = dataAccess.getUserDAO();
        userDAO.clear();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        userDAO.clear();
    }

    @Test
    public void clearAllUser() throws DataAccessException {
        userDAO.insertUser(userOne);
        userDAO.insertUser(userTwo);
        userDAO.clear();
        Assertions.assertFalse(userDAO.usernameExists(userOne.username()));
        Assertions.assertFalse(userDAO.usernameExists(userTwo.username()));
    }

    @Test
    public void insertPass() throws DataAccessException {
        userDAO.insertUser(userOne);
        Assertions.assertTrue(userDAO.verifyUser(userOne));
    }

    @Test
    public void insertFail() throws DataAccessException {
        userDAO.insertUser(userOne);
        Assertions.assertThrows(DataAccessException.class, () -> userDAO.insertUser(userOne));
    }

    @Test
    public void usernameExistsPass() throws DataAccessException {
        userDAO.insertUser(userOne);
        Assertions.assertTrue(userDAO.usernameExists(userOne.username()));
    }

    @Test
    public void usernameExistsFail() throws DataAccessException {
        Assertions.assertFalse(userDAO.usernameExists("testUsername2"));
    }

    @Test
    public void verifyUserPass() throws DataAccessException {
        userDAO.insertUser(userOne);
        Assertions.assertTrue(userDAO.verifyUser(userOne));
    }

    @Test
    public void verifyUserFail() throws DataAccessException {
        Assertions.assertFalse(userDAO.verifyUser(userOne));
    }
}
