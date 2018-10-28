package server.integration;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
	
	public Connection connect() {
        Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ID1212HW3", "postgres", "postgres");
			createUserTable(connection);
			createFileTable(connection);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
        return connection;
	}
	
    private void createUserTable(Connection connection) throws SQLException {
        if (!tableExists(connection, "users")) {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(
                    "CREATE TABLE users (username varchar(32) primary key, password varchar(12))");
        }
    }
    
    private void createFileTable(Connection connection) throws SQLException {
        if (!tableExists(connection, "files")) {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(
                    "CREATE TABLE files (filename varchar(32) primary key, size double precision, owner varchar(12), permissions varchar(12), notification boolean)");
        }
    }

    private boolean tableExists(Connection connection, String name) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tableMetaData = metaData.getTables(null, null, null, null);
        while (tableMetaData.next()) {
            String tableName = tableMetaData.getString(3);
            if (tableName.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}
