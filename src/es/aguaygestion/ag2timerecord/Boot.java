package es.aguaygestion.ag2timerecord;

import es.aguaygestion.ag2timerecord.MySQLAccess;
import es.aguaygestion.ag2timerecord.XMLParser;

public class Boot {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("Workers");
		XMLParser xml = new XMLParser();
		if (xml.readAppXML(Global.appPath, Global.xmlAppFile)) {
			if (xml.readUsrXML(Global.usrPath, Global.xmlUsrFile)) {
				MySQLAccess dao = new MySQLAccess();
				dao.readWorkers();
			}
		}
	}

}
