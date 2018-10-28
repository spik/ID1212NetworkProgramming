package server.integration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.Credentials;
import common.UnauthorizedAccessException;
import common.WrongCredentialsException;
import server.model.ExtendedFile;
import server.model.FileHandler;

public class DatabaseAccess {

	public static DatabaseConnection connector = new DatabaseConnection();
	public static Connection connection = connector.connect();
	private String USER_TABLE = "users";
	private String FILE_TABLE = "files";
	private FileHandler handler = new FileHandler();

	public void createAccount(Credentials credentials) throws SQLException {
		String username = credentials.getUsername();
		String password = credentials.getPassword();

		if (doesUsernameExist(username)) {
			throw new WrongCredentialsException();
		}
		else {
			PreparedStatement createUserStmt = connection.prepareStatement("INSERT INTO "+ USER_TABLE +" VALUES(?, ?)");
			createUserStmt.setString(1, username);
			createUserStmt.setString(2, password);
			createUserStmt.executeUpdate();
		}
	}

	public boolean doesUsernameExist(String username) throws SQLException {

		PreparedStatement getUserStmt = connection.prepareStatement("SELECT * FROM " + USER_TABLE + " WHERE username = ?");
		getUserStmt.setString(1, username);
		ResultSet result = getUserStmt.executeQuery();

		if(!result.next()) {
			return false;
		}
		else {
			return true;
		}
	}

	public boolean doesFileExist(String fileName) throws SQLException {
		PreparedStatement getFileStmt = connection.prepareStatement("SELECT * FROM " + FILE_TABLE + " WHERE filename=?");
		getFileStmt.setString(1, fileName);
		ResultSet result = getFileStmt.executeQuery();

		if(!result.next()) {
			return false;
		}
		else {
			return true;
		}
	}

	public void deleteAccount(String username) throws SQLException {

		PreparedStatement deleteStmt = connection.prepareStatement("DELETE FROM " + USER_TABLE + " WHERE username = ?");
		deleteStmt.setString(1, username);
		deleteStmt.executeUpdate();
	}

	public void login(Credentials credentials) throws SQLException {
		String username = credentials.getUsername();
		String password = credentials.getPassword();

		PreparedStatement loginStmt = connection.prepareStatement("SELECT * FROM " + USER_TABLE + " WHERE username=? AND password = ?");
		loginStmt.setString(1, username);
		loginStmt.setString(2, password);
		ResultSet result = loginStmt.executeQuery();

		if(!result.next()) {
			throw new WrongCredentialsException();
		}
	}

	public ArrayList<ExtendedFile> getAllPossibleFiles(String username) throws SQLException {
		PreparedStatement getFilesStmt = connection.prepareStatement("SELECT * FROM " + FILE_TABLE + " WHERE permissions = 'read' OR permissions = 'write' OR owner=?");
		getFilesStmt.setString(1, username);
		ResultSet result = getFilesStmt.executeQuery();
		ArrayList<ExtendedFile> files = new ArrayList<ExtendedFile>();
		while(result.next()) {
			byte[] dummy = new byte[0];
			ExtendedFile file = new ExtendedFile(dummy, result.getString(1), result.getString(1), result.getString(3), result.getString(4), false);
			file.setSize(result.getDouble(2));
			files.add(file);
		}
		return files;
	}

	public String getSpecificFile(String fileNameForDatabase, String username) throws SQLException {
		PreparedStatement getFileStmt = connection.prepareStatement("SELECT * FROM files WHERE filename = ? AND (permissions = 'read' OR permissions = 'write' OR owner=?)");
		getFileStmt.setString(1, fileNameForDatabase);
		getFileStmt.setString(2, username);
		ResultSet result = getFileStmt.executeQuery();

		if(!result.next()) {
			throw new UnauthorizedAccessException();
		}
		String owner = result.getString(3);
		if (result.getBoolean(5) && !owner.equals(username)) {
			return owner + " " + fileNameForDatabase;
		} else
			return null;

	}

	public String insertFile(ExtendedFile extendedFile, String username) throws SQLException {

		String fileName = extendedFile.getFileNameForDatabase();

		//Check if the user trying to insert or edit the file as permission to do so
		if (doesFileExist(fileName)) {
			checkWritePermissions(username, fileName);
		}

		PreparedStatement getOwner = connection.prepareStatement("SELECT * FROM files WHERE filename = ?");
		getOwner.setString(1, fileName);
		ResultSet result = getOwner.executeQuery();

		//Inserts the file into the database. If there is a conflict (the file already exists) update the row in the table. This successfully edits the file metadata. 
		String stmtString = "INSERT INTO "+ FILE_TABLE +" VALUES(?, ?, ?, ?, ?) ON CONFLICT (filename) DO UPDATE SET filename = EXCLUDED.filename, "
				+ "size = EXCLUDED.size";
		PreparedStatement insertFileStmt = connection.prepareStatement(stmtString);
		insertFileStmt.setString(1, extendedFile.getFileNameForDatabase());
		insertFileStmt.setDouble(2, extendedFile.getSize());
		insertFileStmt.setString(3, extendedFile.getOwner());
		insertFileStmt.setString(4, extendedFile.getPermissions());
		insertFileStmt.setBoolean(5, extendedFile.getNotification());
		insertFileStmt.executeUpdate();

		if (result.next()) {
			String owner = result.getString(3);
			if (result.getBoolean(5) && !owner.equals(username)) {
				return owner + " " + fileName;
			} else
				return null;
		}
		else return null;

	}

	private void checkWritePermissions(String username, String fileName) throws SQLException {
		PreparedStatement getFileStmt = connection.prepareStatement("SELECT * FROM " + FILE_TABLE + " WHERE filename = ? AND (permissions = 'write' OR owner=?)");
		getFileStmt.setString(1, fileName);
		getFileStmt.setString(2, username);
		ResultSet result = getFileStmt.executeQuery();
		if(!result.next()) {
			throw new UnauthorizedAccessException();
		}
	}

	public String deleteFileFromDatabase(String username, String fileNameForDatabase) throws SQLException {

		checkWritePermissions(username, fileNameForDatabase);

		PreparedStatement getNotification = connection.prepareStatement("SELECT * FROM files WHERE filename = ?");
		getNotification.setString(1, fileNameForDatabase);
		ResultSet result = getNotification.executeQuery();

		String owner = null;
		boolean notification = false;
		if(result.next()) {
			if (result.getBoolean(5)) {
				notification = true;
				owner = result.getString(3);
			}
			else notification = false;
		}


		PreparedStatement deleteStmt = connection.prepareStatement("DELETE FROM " + FILE_TABLE + " WHERE filename = ?");
		deleteStmt.setString(1, fileNameForDatabase);
		deleteStmt.executeUpdate();

		if (notification && !owner.equals(username)) return owner + " " + fileNameForDatabase;
		else return null;
	}
}
