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

	public void userIdForCurrentUser() throws Exception {
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
				Global.user_id = 0;
			} else {
				Global.user_id = _user_id;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			closeDataObjects();
		}
	}
	
	public void workerIdForCurrentUser() throws Exception {
		userIdForCurrentUser();
		if (Global.user_id == 0) {
			Global.worker_id = 0;
			return;
		}
		if (!newConnection()) {
			Global.worker_id = 0;
			return;
		}
		try {
			/* PreparedStatement to issue SQL query */
			// Search worker using user_id to extract worker_id
			preparedStatement = connection
					.prepareStatement("select id, last_name, first_name from workers where user_id = "
							+ Global.user_id);
			resultSet = preparedStatement.executeQuery();
			resultSet.first();
			Integer _worker_id = resultSet.getInt("id");
			if (_worker_id == null) {
				_worker_id = 0;
			}
			Global.worker_id = _worker_id;
		} catch (Exception e) {
			throw e;
		} finally {
			closeDataObjects();
		}
	}

	public void workerNameForCurrentWorker() throws Exception {
		userIdForCurrentUser();
		if (Global.user_id == 0) {
			return;
		}
		if (!newConnection()) {
			return;
		}
		try {
			/* PreparedStatement to issue SQL query */
			// Search worker using worker_id
			preparedStatement = connection
					.prepareStatement("select last_name, first_name from workers where id = "
							+ Global.worker_id);
			resultSet = preparedStatement.executeQuery();
			resultSet.first();
			String _worker_last_name = resultSet.getString("last_name");
			String _worker_first_name = resultSet.getString("first_name");
			Global.worker_name = _worker_last_name + ", "
					+ _worker_first_name;
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

	public void readTimeRecordTypes() throws Exception {
		try {
			if (!newConnection()) {
				return;
			}
			// PreparedStatement to issue SQL query
			preparedStatement = connection
					.prepareStatement("select id, name from timerecord_types order by id");
			// SQL query's ResultSet with PreparedStatement
			resultSet = preparedStatement.executeQuery();
			// Load types list
			Global.typesList.clear();
			while (resultSet.next()) {
				Integer _id = resultSet.getInt("id");
				String _name = resultSet.getString("name");
				TimeRecordType _type = new TimeRecordType(_id, _name);
				Global.typesList.add(_type);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			closeDataObjects();
		}
	}

	public void readTimeRecordCodes() throws Exception {
		try {
			if (!newConnection()) {
				return;
			}
			// PreparedStatement to issue SQL query
			preparedStatement = connection
					.prepareStatement("select id, name from timerecord_codes order by id");
			// SQL query's ResultSet with PreparedStatement
			resultSet = preparedStatement.executeQuery();
			// Load codes list
			Global.codesList.clear();
			while (resultSet.next()) {
				Integer _id = resultSet.getInt("id");
				String _name = resultSet.getString("name");
				TimeRecordCode _code = new TimeRecordCode(_id, _name);
				Global.codesList.add(_code);
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
			// 0: id
			// 1: timerecord_date
			// 2: timerecord_time,
			// 3: worker_id
			// 4: timerecord_type
			// 5: timerecord_code,
			// 6: created_at
			// 7: updated_at
			// 8: created_by
			// 9: updated_by
			// 10: source_ip
			preparedStatement = connection
					.prepareStatement("insert into time_records values (default, ?, ?, ?, ? , ?, ?, ?, ?, ?, ?)");
			preparedStatement.setDate(1, timerecord_date);
			preparedStatement.setTime(2, timerecord_time);
			preparedStatement.setInt(3, Global.worker_id);
			preparedStatement.setInt(4, Global.timerecord_type_id);
			preparedStatement.setInt(5, Global.timerecord_code_id);
			preparedStatement.setTimestamp(6, _at);
			preparedStatement.setTimestamp(7, _at);
			preparedStatement.setInt(8, Global.user_id);
			preparedStatement.setInt(9, Global.user_id);
			preparedStatement.setString(10, Global.clientIP());
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
