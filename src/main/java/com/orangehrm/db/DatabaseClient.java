package com.orangehrm.db;

import com.orangehrm.config.ConfigManager;
import com.orangehrm.enums.ConfigProperties;
import com.orangehrm.exceptions.FrameworkException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Minimal JDBC helper for the (bonus) DB-validation layer. Lets a step assert
 * that what the UI shows matches what landed in the database. Connection details
 * are config-driven and the connection is always closed via try-with-resources.
 */
public final class DatabaseClient {

    private DatabaseClient() { }

    public static List<Map<String, Object>> query(String sql) {
        List<Map<String, Object>> results = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(
                ConfigManager.get(ConfigProperties.DBURL),
                ConfigManager.get(ConfigProperties.DBUSER),
                ConfigManager.get(ConfigProperties.DBPASSWORD));
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            int cols = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                for (int i = 1; i <= cols; i++) {
                    row.put(rs.getMetaData().getColumnName(i), rs.getObject(i));
                }
                results.add(row);
            }
        } catch (Exception e) {
            throw new FrameworkException("DB query failed: " + sql, e);
        }
        return results;
    }
}
