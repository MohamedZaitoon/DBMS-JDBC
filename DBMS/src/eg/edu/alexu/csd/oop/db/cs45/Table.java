package eg.edu.alexu.csd.oop.db.cs45;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Table {
	private final String path;
	private HashMap<String, String> map;

	public Table(String path, HashMap<String, String> nameAndCol) {
		this.map = nameAndCol;
		this.path = path;
		create();
	}

	private void create() {
		try {
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();

			// root element
			Element root = document.createElement("table");
			document.appendChild(root);

			/*
			 * rest of code...
			 */
			
			//transfer document to an xml file.
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File(path));
			transformer.transform(domSource, streamResult);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
