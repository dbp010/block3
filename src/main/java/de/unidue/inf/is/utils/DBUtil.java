package de.unidue.inf.is.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.ibm.db2.jcc.DB2Driver;

import de.unidue.inf.is.dbp010.exception.PersistenceManagerException;

public final class DBUtil {

	private DBUtil() {
	}

	static {
		com.ibm.db2.jcc.DB2Driver driver = new DB2Driver();
		try {
			DriverManager.registerDriver(driver);
		} catch (SQLException e) {
			throw new Error("Laden des Datenbanktreiber nicht möglich");
		}
	}

	public static Connection getConnection(String database) throws SQLException {
		final String url = "jdbc:db2:" + database;
		return DriverManager.getConnection(url);
	}

	public static Connection getExternalConnection(String database) throws SQLException {
		Properties properties = new Properties();
		properties.setProperty("securityMechanism",
				Integer.toString(com.ibm.db2.jcc.DB2BaseDataSource.USER_ONLY_SECURITY));
		properties.setProperty("user", "DBP010");
		properties.setProperty("password", "ahr4kohm");

		final String url = "jdbc:db2://dione.is.inf.uni-due.de:50010/" + database + ":currentSchema=DBP010;";
		System.out.println(url);
		Connection connection = DriverManager.getConnection(url, properties);
		return connection;
	}

	public static boolean checkDatabaseExistsExternal(String database) {
		// Nur für Demozwecke!
		boolean exists = false;

		try (Connection connection = getExternalConnection(database)) {
			exists = true;
		} catch (SQLException e) {
			exists = false;
			e.printStackTrace();
		}

		return exists;
	}

	public static boolean checkDatabaseExists(String database) {
		// Nur für Demozwecke!
		boolean exists = false;

		try (Connection connection = getConnection(database)) {
			exists = true;
		} catch (SQLException e) {
			exists = false;
			e.printStackTrace();
		}

		return exists;
	}

	public static void close(ResultSet resultSet) {
		try{
			close(resultSet, true);
		}catch (PersistenceManagerException e) {
			// suppressed
		}
	}
	
	public static void close(ResultSet resultSet, boolean suppressException) throws PersistenceManagerException {
		if(resultSet == null)
			return;
		
		try {
			resultSet.close();
		}catch (SQLException e) {
			if(!suppressException)
				e.printStackTrace();
			else
				throw new PersistenceManagerException("Failed to close result set");
		}
		
	}	
}