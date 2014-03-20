package es.aguaygestion.ag2timerecord;

import java.awt.SystemTray;

import es.aguaygestion.ag2timerecord.MySQLAccess;
import es.aguaygestion.ag2timerecord.XMLParser;

public class ag2TimeRecord {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// Console exit message
		String startMessage = null;

		System.out.println("ag2TimeRecord Client Started.");
		System.out.println(Global.clientIP());

		XMLParser xml = new XMLParser();
		// Try read XML Application file
		if (xml.readAppXML(Global.appPath, Global.xmlAppFile)) {
			// Try read XML User file (or generate it)
			if (xml.readUsrXML(Global.usrPath, Global.xmlUsrFile)) {
				MySQLAccess dao = new MySQLAccess();
				// Load DAO Lists
				dao.readTimeRecordTypes();
				dao.readTimeRecordCodes();
				dao.workerNameForCurrentWorker();
				//Check the SystemTray support
		        if (!SystemTray.isSupported()) {
		            startMessage = "\nSystemTray is not supported";
		        } else {
					// Display Main Window
					try {
						Global.window = new TimeRecordWindow();
					} catch (Exception e) {
						e.printStackTrace();
					}
		        }
			} else {
				startMessage = "\nError reading/writing User XML file!";
			}
		} else {
			startMessage = "\nError reading Application XML file!";
		}
		if (startMessage != null) {
			System.out.println(startMessage);
		}
	}

}
