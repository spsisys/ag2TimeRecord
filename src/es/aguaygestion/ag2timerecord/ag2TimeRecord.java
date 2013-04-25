package es.aguaygestion.ag2timerecord;

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
		if (xml.readAppXML(Global.appPath, Global.xmlAppFile)) {
			if (xml.readUsrXML(Global.usrPath, Global.xmlUsrFile)) {
				// Display Main Window
				try {
					TimeRecordWindow window = new TimeRecordWindow();
					window.frmAgtimerecordMarcajes.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
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
