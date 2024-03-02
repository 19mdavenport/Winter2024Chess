package dataAccess.mysql;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.DataAccessException;

import java.sql.*;

public abstract class MySqlDAO {
    private static boolean configured = false;
    public MySqlDAO() throws DataAccessException {
        configureDatabase();
    }
    protected int executeUpdate(String statement, Object... params) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS)) {
                addParams(ps, params);
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }

                return 0;
            }
        } catch (SQLException e) {
            throw new DataAccessException(
                    String.format("unable to update database: %s, %s", statement, e.getMessage()));
        }
    }


    protected <T> T executeQuery(String statement, ResultSetParser<T> parser, Object... params)
            throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(statement)) {
            addParams(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                return parser.parseResultSet(rs);
            }
        } catch (SQLException e) {
            throw new DataAccessException(
                    String.format("unable to update database: %s, %s", statement, e.getMessage()));
        }
    }


    private void addParams(PreparedStatement ps, Object[] params) throws SQLException, DataAccessException {
        for (int i = 0; i < params.length; i++) {
            Object param = params[i];
            switch (param) {
                case String p -> ps.setString(i + 1, p);
                case Integer p -> ps.setInt(i + 1, p);
                case ChessGame p -> ps.setString(i + 1, new Gson().toJson(p));
                case null -> ps.setNull(i + 1, Types.NULL);
                default -> throw new DataAccessException("Unexpected data type: " + param.getClass());
            }
        }
    }


    protected void configureDatabase() throws DataAccessException {
        if(!configured) {
            DatabaseManager.createDatabase();
            configured = true;
        }
        try (Connection conn = DatabaseManager.getConnection()) {
            for (String statement : getCreateStatements()) {
                try (PreparedStatement preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }

    protected abstract String[] getCreateStatements();


    @FunctionalInterface
    protected static interface ResultSetParser<T> {

        T parseResultSet(ResultSet rs) throws SQLException;

    }
}
