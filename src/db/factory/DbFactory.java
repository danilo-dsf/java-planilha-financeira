package db.factory;

import db.DbConnectionData;
import db.DbException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe responsável pela criação do banco de dados caso ele não exista
 * 
 * @author danilodsf
 */
public class DbFactory {

    private static Connection conn = null;

    public static void start(DbConnectionData connData) {
        Statement st = null;
        String connectionString = connData.getUrl() + "?user=" + connData.getUsername() + "&password=" + connData.getPassword();

        try {
            Class.forName(connData.getDriver());
            conn = DriverManager.getConnection(connectionString);

            st = conn.createStatement();
        
            createDatabase(st, connData);
            createTableContas(st, connData);
            createTableTransacoes(st, connData);
        }
        catch (SQLException | ClassNotFoundException e) {
            throw new DbException(e.getMessage());
        }
    }

    private static void createDatabase(Statement st, DbConnectionData connData) throws SQLException {
        st.executeUpdate("CREATE DATABASE IF NOT EXISTS " + connData.getDbName());
    }

    private static void createTableContas(Statement st, DbConnectionData connData) throws SQLException {
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + connData.getDbName() + ".contas (\n"
                + "	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,\n"
                + "	descricao varchar(255) NOT NULL\n"
                + ");");
    }

    private static void createTableTransacoes(Statement st, DbConnectionData connData) throws SQLException {
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + connData.getDbName() + ".transacoes (\n"
                + "	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,\n"
                + "	natureza CHAR(1),\n"
                + "	descricao VARCHAR(255),\n"
                + "	data_movimento DATETIME,\n"
                + "	valor DOUBLE,\n"
                + "	id_conta INTEGER,\n"
                + "     CONSTRAINT fk_id_conta FOREIGN KEY (id_conta) REFERENCES contas (id)"
                + ");");
    }
}
