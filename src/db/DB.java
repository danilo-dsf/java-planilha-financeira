package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe responsável pela conexão com o banco de dados MySQL
 *
 * @author danilodsf
 */
public class DB {

    private static Connection conn = null;

    public static Connection getConnection(DbConnectionData connData) {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection((connData.getUrl() + connData.getDbName()), connData.getUsername(), connData.getPassword());
            } 
            catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } 
            catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } 
            catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } 
            catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
}
