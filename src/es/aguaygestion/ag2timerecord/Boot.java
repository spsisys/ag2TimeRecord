package es.aguaygestion.ag2timerecord;

import es.aguaygestion.ag2timerecord.XMLParser;

public class Boot {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// Console exit message
		String exitMessage = null;

		System.out.println("ag2TimeRecord Client Started.");

		XMLParser xml = new XMLParser();
		if (xml.readAppXML(Global.appPath, Global.xmlAppFile)) {
			if (xml.readUsrXML(Global.usrPath, Global.xmlUsrFile)) {
				// Display Main Window
				try {
					TimeRecordWindow frame = new TimeRecordWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
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
