package uk.mqchinee.archelia.sql;

import lombok.Getter;

import java.io.File;
import java.sql.*;

public class SQLHandler {

	@Getter
	private static Connection connection = null;
	private static boolean debug = false;
	private final String filePath;

	public SQLHandler(final String filePath) {
		this.filePath = filePath;
		debug = false;
	}

	public SQLHandler(final String filePath, boolean debugMode) {
		this.filePath = filePath;
		debug = debugMode;
	}


	public static ResultSet sqlQuery(final String sql) {
		ResultSet rs = null;
		try {
			final Statement statement = connection.createStatement();
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();

		}
		if (rs != null)
			return rs;
		throw new IllegalStateException("Result set can not be null!");
	}

	public static void sqlUpdate(final String sql) {
		try {
			final PreparedStatement pstmt = SQLHandler.getConnection().prepareStatement(sql);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void connect(final String databaseName) {
		connection = null;
		try {

			Class.forName("org.sqlite.JDBC");
			final String URL = "jdbc:sqlite:" + filePath + File.separator + databaseName + ".db";

			connection = DriverManager.getConnection(URL);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {
		if (connection == null) return;
		try {
			connection.close();
			connection = null;
		} catch (final SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	public boolean isConnected() {
		return connection != null;
	}

}
