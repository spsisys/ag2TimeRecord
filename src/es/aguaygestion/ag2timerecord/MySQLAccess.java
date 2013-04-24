package es.aguaygestion.ag2timerecord;

import java.sql.*;
import java.util.Date;

public class MySQLAccess {
	// For Data objects (DAO)
	private Connection connection = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	// For Connection
	private String hostName = "ag2Back";
	private String hostIp = "192.168.100.24";
	private String db = "ag2Db_development";
	private String user = "root";
	private String pswd = "root";
	private String connectionString = "jdbc:mysql://" + hostIp + "/" + db;

	public void readWorkers() throws Exception {
		// Load the MySQL driver
		Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection
		connection = DriverManager.getConnection(connectionString, user, pswd);
		/*
		 * // Statement to issue SQL query statement =
		 * connection.createStatement(); // SQL query's ResultSet with Statement
		 * resultSet = statement.executeQuery("select * from workers");
		 */
		// PreparedStatement to issue SQL query
		preparedStatement = connection
				.prepareStatement("select * from workers");
		// SQL query's ResultSet with PreparedStatement
		resultSet = preparedStatement.executeQuery();
		System.out.println(hostName + "\n");
		while (resultSet.next()) {
			// It is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g. resultSet.getSTring(2);
			String lastName = resultSet.getString("last_name");
			String firstName = resultSet.getString("first_name");
			String workerCode = resultSet.getString("worker_code");
			Date createdAt = resultSet.getDate("created_at");
			System.out.println("Worker: " + lastName + ", " + firstName
					+ "\t (" + workerCode + ")\t Created at: " + createdAt);
			/*
			 * System.out.println("Code: " + workerCode);
			 * System.out.println("Created at: " + createdAt);
			 */
		}
		try {

		} catch (Exception e) {
			throw e;
		} finally {
			closeDataObjects();
		}
	}

	public void closeDataObjects() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
		}
	}
}
