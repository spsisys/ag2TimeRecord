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

	public void readWorkers() throws Exception {
		try {
			// Load the MySQL driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection
			connection = DriverManager.getConnection(connectionString,
					Global.db_user, Global.db_pswd);
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

	@SuppressWarnings("deprecation")
	public void writeTimeRecord() throws Exception {
		try {
			// Current date and time
			LocalDate today = new LocalDate();
			LocalTime now = new LocalTime();
			Integer yy = today.getYear();
			Integer mm = today.getMonthOfYear();
			Integer dd = today.getDayOfMonth();
			Integer h = now.getHourOfDay();
			Integer m = now.getMinuteOfHour();
			Integer s = now.getSecondOfMinute();
			// Load the MySQL driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection
			connection = DriverManager.getConnection(connectionString,
					Global.db_user, Global.db_pswd);
			// PreparedStatement to INSERT values:
			// id, timerecord_date, timerecord_time,
			// worker_id, timerecord_type, timerecord_code
			preparedStatement = connection
					.prepareStatement("insert into time_records values (default, ?, ?, ?, ? , ?)");
			preparedStatement.setDate(1, new java.sql.Date(yy, mm, dd));
			preparedStatement.setTime(2, new java.sql.Time(h, m, s));
			preparedStatement.setInt(3, 1);
			preparedStatement.setInt(4, 1);
			preparedStatement.setInt(5, 1);
			preparedStatement.executeUpdate();
		} catch (Exception e) {

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
