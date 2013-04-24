package es.aguaygestion.ag2timerecord;

import java.io.File;

public class Global {
	// XML Application
	public static String hostName = null;
	public static String hostIP = null;
	public static String db = null;
	public static String db_user = null;
	public static String db_pswd = null;
	// XML User
	public static String worker_user = null;
	public static Integer worker_id = 0;
	// XML Files
	public static String xmlAppFile = "ag2TimeRecord.xml";
	public static String xmlUsrFile = "worker_id.xml";
	public static String appPath = System.getProperty("user.dir");
	public static String usrPath = System.getProperty("user.home");
	// Current user
	public static String user = System.getProperty("user.name");
	
	public static Boolean fileExists(String fullFileName) {
		File f = new File(fullFileName);
		if (f.exists()) {
			return true;
		} else {
			return false;
		}
	}
}
