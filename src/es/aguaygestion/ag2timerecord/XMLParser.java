package es.aguaygestion.ag2timerecord;

import es.aguaygestion.ag2timerecord.MySQLAccess;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class XMLParser {

	private DocumentBuilder dBuilder = null;
	private Document doc = null;
	private NodeList nList = null;

	public Boolean InitXMLReadObjects(String path, String file)
			throws Exception {
		try {
			dBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			doc = dBuilder.parse(path + "//" + file);
			doc.getDocumentElement().normalize();
			if (doc.hasChildNodes()) {
				nList = doc.getChildNodes();
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean readAppXML(String path, String file) throws Exception {
		if (!Global.fileExists(path + "//" + file)) {
			return false;
		}

		try {
			if (InitXMLReadObjects(path, file)) {
				for (int node = 0; node < nList.getLength(); node++) {
					Node nNode = nList.item(node);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						Global.hostName = eElement
								.getElementsByTagName("hostName").item(0)
								.getTextContent();
						Global.hostIP = eElement.getElementsByTagName("hostIP")
								.item(0).getTextContent();
						Global.db = eElement.getElementsByTagName("db").item(0)
								.getTextContent();
						Global.db_user = eElement.getElementsByTagName("user")
								.item(0).getTextContent();
						Boolean bPswd = Boolean.parseBoolean(eElement
								.getElementsByTagName("pswd").item(0)
								.getTextContent());
						if (bPswd) {
							Global.db_pswd = "root";
						} else {
							Global.db_pswd = null;
						}
					}
				}
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean readUsrXML(String path, String file) throws Exception {
		if (!Global.fileExists(path + "//" + file)) {
			if (!writeUsrXML(path, file)) {
				return false;
			}
		}

		try {
			if (InitXMLReadObjects(path, file)) {
				for (int node = 0; node < nList.getLength(); node++) {
					Node nNode = nList.item(node);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						Global.worker_user = eElement
								.getElementsByTagName("user").item(0)
								.getTextContent();
						Global.worker_id = Integer.parseInt(eElement
								.getElementsByTagName("id").item(0)
								.getTextContent());
					}
				}
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean writeUsrXML(String path, String file) throws Exception {
		try {
			/* Search current_user worker_id */
			MySQLAccess dao = new MySQLAccess();
			dao.workerIdForCurrentUser();
			if (Global.worker_id == 0) {
				return false;
			}
			String _id = Global.worker_id.toString();

			/* Scaffold XML */
			dBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			doc = dBuilder.newDocument();
			// root element (worker)
			Element rootElement = doc.createElement("worker");
			doc.appendChild(rootElement);
			// user element
			Element user = doc.createElement("user");
			user.appendChild(doc.createTextNode(Global.current_user));
			rootElement.appendChild(user);
			// id element
			Element id = doc.createElement("id");
			id.appendChild(doc.createTextNode(_id));
			rootElement.appendChild(id);

			/* Write the content into XML file */
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(path + "//" + file));
			Transformer transformer = TransformerFactory.newInstance()
					.newTransformer();
			transformer.transform(source, result);

			return true;
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
			return false;
		} catch (TransformerException te) {
			te.printStackTrace();
			return false;
		} catch (Exception e) {
			return false;
		}
	}

}
