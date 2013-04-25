package es.aguaygestion.ag2timerecord;

import java.sql.*;
import org.joda.time.*;
import java.util.Date;

public class MySQLAccess {
	// For Data objects (DAO)
	private Connection connection = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	// For Connection
	private String connectionString = "jdbc:mysql://" + Global.hostIP + "/"
			+ Global.db;

	public Boolean newConnection() throws Exception {
		try {
			// Load the MySQL driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection
			connection = DriverManager.getConnection(connectionString,
					Global.db_user, Global.db_pswd);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void workerIdForCurrentUser() throws Exception {
		if (!newConnection()) {
			return;
		}
		try {
			/* PreparedStatement to issue SQL query */
			// Search user using current_user to extract user_id
			preparedStatement = connection
					.prepareStatement("select id from users where name = '"
							+ Global.current_user + "'");
			resultSet = preparedStatement.executeQuery();
			resultSet.first();
			Integer _user_id = resultSet.getInt("id");
			if (_user_id == null) {
				Global.current_id = 0;
				return;
			}
			// Search worker using user_id to extract worker_id
			preparedStatement = connection
					.prepareStatement("select id from workers where user_id = "
							+ _user_id);
			resultSet = preparedStatement.executeQuery();
			resultSet.first();
			Integer _worker_id = resultSet.getInt("id");
			if (_worker_id == null) {
				_worker_id = 0;
			}
			Global.current_id = _worker_id;
		} catch (Exception e) {
			throw e;
		} finally {
			closeDataObjects();
		}
	}

	public void readWorkers() throws Exception {
		try {
			if (!newConnection()) {
				return;
			}
			// PreparedStatement to issue SQL query
			preparedStatement = connection
					.prepareStatement("select * from workers");
			// SQL query's ResultSet with PreparedStatement
			resultSet = preparedStatement.executeQuery();
			LocalDate d = new LocalDate();
			LocalTime t = new LocalTime();
			System.out.println(Global.hostName + " - Running at " + d + " " + t
					+ "\n");
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
		} catch (Exception e) {
			throw e;
		} finally {
			closeDataObjects();
		}
	}

	public Boolean writeTimeRecord() throws Exception {
		try {
			if (!newConnection()) {
				return false;
			}
			// Current date and time
			DateTime dt = new DateTime();
			Timestamp _at = new Timestamp(dt.getMillis());
			java.sql.Date timerecord_date = new java.sql.Date(dt.getMillis());
			java.sql.Time timerecord_time = new java.sql.Time(dt.getMillis());
			// PreparedStatement to INSERT values:
			// id, timerecord_date, timerecord_time,
			// worker_id, timerecord_type, timerecord_code,
			// created_at, updated_at
			preparedStatement = connection
					.prepareStatement("insert into time_records values (default, ?, ?, ?, ? , ?, ?, ?)");
			preparedStatement.setDate(1, timerecord_date);
			preparedStatement.setTime(2, timerecord_time);
			preparedStatement.setInt(3, Global.worker_id);
			preparedStatement.setInt(4, 1);
			preparedStatement.setInt(5, 1);
			preparedStatement.setTimestamp(6, _at);
			preparedStatement.setTimestamp(7, _at);
			preparedStatement.executeUpdate();
			
			return true;
		} catch (Exception e) {
			return false;
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
