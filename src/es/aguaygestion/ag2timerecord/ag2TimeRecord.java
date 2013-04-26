package es.aguaygestion.ag2timerecord;

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

		XMLParser xml = new XMLParser();
		// Try read XML Application file
		if (xml.readAppXML(Global.appPath, Global.xmlAppFile)) {
			// Try read XML User file (or generate it)
			if (xml.readUsrXML(Global.usrPath, Global.xmlUsrFile)) {
				MySQLAccess dao = new MySQLAccess();
				dao.readWorkers();
				// Load DAO Lists
				dao.readTimeRecordTypes();
				dao.readTimeRecordCodes();
				// Display Main Window
//				try {
//					TimeRecordWindow window = new TimeRecordWindow();
//					window.frmAgtimerecordMarcajes.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
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
