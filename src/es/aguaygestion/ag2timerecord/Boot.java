package es.aguaygestion.ag2timerecord;

import es.aguaygestion.ag2timerecord.MySQLAccess;
import es.aguaygestion.ag2timerecord.XMLParser;

public class Boot {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// Console exit message
		String exitMessage = null;
		String hola = null;

		// Display Main Window
		try {
			TimeRecordWindow frame = new TimeRecordWindow();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("ag2TimeRecord Client Started.");
		
		XMLParser xml = new XMLParser();
		if (xml.readAppXML(Global.appPath, Global.xmlAppFile)) {
			if (xml.readUsrXML(Global.usrPath, Global.xmlUsrFile)) {
				MySQLAccess dao = new MySQLAccess();
				// dao.readWorkers();
				if (dao.writeTimeRecord()) {
					exitMessage = "\nTime record saved for worker "
							+ Global.worker_user + " id " + Global.worker_id
							+ ". Finished.";
				} else {
					exitMessage = "\nError writing Time Record for current user!";
				}
			} else {
				exitMessage = "\nError reading/writing User XML file!";
			}
		} else {
			exitMessage = "\nError reading Application XML file!";
		}
		
		System.out.println(exitMessage);
	}

}
