package es.aguaygestion.ag2timerecord;

import es.aguaygestion.ag2timerecord.MySQLAccess;

public class Boot {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// Public static variables for XML
		Global.hostName = "ag2Back";
		Global.hostIP = "192.168.100.24";
		Global.db = "ag2Db_development";
		Global.user = "root";
		Global.pswd = "root";
		
		System.out.println("Workers");
		MySQLAccess dao = new MySQLAccess();
		dao.readWorkers();
	}

}
