package dataAccess.memory;

import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import model.UserData;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MemoryUserDAO implements UserDAO {
    private final Map<String, UserData> users = new HashMap<>();

    @Override
    public void clear() throws DataAccessException {
        users.clear();
    }

    @Override
    public void insertUser(UserData user) throws DataAccessException {
        if (users.containsKey(user.username())) throw new DataAccessException("User already exists");
        users.put(user.username(), user);
    }

    @Override
    public boolean usernameExists(String username) throws DataAccessException {
        return users.get(username) != null;
    }

    @Override
    public boolean verifyUser(UserData user) throws DataAccessException {
        UserData fromDatabase = users.get(user.username());
        return fromDatabase != null && Objects.equals(user.password(), fromDatabase.password());
    }
}
