package es.aguaygestion.ag2timerecord;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Global {
	// WindowBuilder
	public static TimeRecordWindow window;
	// DAO Lists
	public static List<TimeRecordType> typesList = new ArrayList<TimeRecordType>();
	public static List<TimeRecordCode> codesList = new ArrayList<TimeRecordCode>();
	// DAO return values
	public static Integer timerecord_type_id = 0;
	public static Integer timerecord_code_id = 0;
	public static String worker_name = null;
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
	public static String current_user = System.getProperty("user.name");
	public static Integer user_id = 0;
	
	public static Boolean fileExists(String fullFileName) {
		File f = new File(fullFileName);
		if (f.exists()) {
			return true;
		} else {
			return false;
		}
	}
}
