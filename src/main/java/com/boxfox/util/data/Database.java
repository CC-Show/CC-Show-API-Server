package com.boxfox.util.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

    private static PreparedStatement buildQuery(String sql, Object... args) {
        PreparedStatement statement = null;
        try {
            statement = DataSource.getConnection().prepareStatement(sql);
            int placeholderCount = 1;
            for (Object o : args) {
                statement.setObject(placeholderCount++, o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return statement;
    }

    public static ResultSet executeQuery(String sql, Object... args) {
        try {
            return buildQuery(sql, args).executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int executeUpdate(String sql, Object... args) {
        try {
            return buildQuery(sql, args).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}