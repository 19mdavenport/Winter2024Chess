package dataAccess.mysql;

import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import model.UserData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.ResultSet;

public class MySqlUserDAO extends MySqlDAO implements UserDAO {
    public MySqlUserDAO() throws DataAccessException {
    }

    @Override
    public void clear() throws DataAccessException {
        executeUpdate("TRUNCATE TABLE user;");
    }

    @Override
    public void insertUser(UserData user) throws DataAccessException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(user.password());
        String statement = "INSERT INTO user (username, password, email) VALUES (?, ?, ?);";
        executeUpdate(statement, user.username(), hashedPassword, user.email());
    }


    @Override
    public boolean usernameExists(String username) throws DataAccessException {
        return executeQuery("SELECT * FROM user WHERE username=?", ResultSet::next, username);
    }


    @Override
    public boolean verifyUser(UserData user) throws DataAccessException {
        return executeQuery("SELECT * FROM user WHERE username=?", (rs) -> {
            if (!rs.next()) return false;
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            return encoder.matches(user.password(), rs.getString("password"));
        }, user.username());
    }

    @Override
    protected String[] getCreateStatements() {
        return new String[]{"""
            CREATE TABLE IF NOT EXISTS `user` (
                `username` VARCHAR(64) NOT NULL PRIMARY KEY,
                `password` VARCHAR(64) NOT NULL,
                `email` VARCHAR(64) NOT NULL
            )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
            
            """};
    }
}
