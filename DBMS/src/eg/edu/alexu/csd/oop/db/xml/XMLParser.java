package eg.edu.alexu.csd.oop.db.xml;
import java.io.File;
import java.util.HashMap;
import java.util.Map;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLParser {

	private final String path;
	private HashMap<String, String> map;

	public XMLParser(String path, HashMap<String, String> nameAndCol) {
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
			Element root = document.createElement(map.get("table"));
			document.appendChild(root);
			map.remove("table");
			Element row = document.createElement("row");
			
			//colomns 
			for (Map.Entry<String, String> entry : map.entrySet())
			{
				Element col = document.createElement(entry.getKey());
				Attr attr = document.createAttribute("dataType");
				attr.setValue(entry.getValue());
				col.setAttributeNode(attr);
				row.appendChild(col);
			}
			root.appendChild(row);
			
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
