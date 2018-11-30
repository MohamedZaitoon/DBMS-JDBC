package eg.edu.alexu.csd.oop.db.xml;
import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import eg.edu.alexu.csd.oop.db.cs45.Table;

public class SaveAsXml {

	private final String path;
	private Table table;

	public SaveAsXml(String path, Table table) {
		this.table = table;
		this.path = path;
		create();
	}

	private void create() {
		try {
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			
			//convert table to an array
			ArrayList<ArrayList<Object>> getTable = table.getTable();
			Object[][] rows = new Object[getTable.size()][getTable.get(0).size()];
			
			for (int i = 0; i < getTable.size(); i++) {
			    ArrayList<Object> row = getTable.get(i);
			    rows[i] = row.toArray(new Object[row.size()]);
			}
			//root element
			Element root = document.createElement(table.getName());
			//root children:
			for(int i = 1; i < rows.length; i++)
			{
				Element row = document.createElement("row");
				for(int j = 0; j < rows[i].length; j++)
				{
					Element col = document.createElement((String) rows[0][j]);
					col.appendChild(document.createTextNode((String) rows[i][j]));
					row.appendChild(col);
				}
				root.appendChild(row);
			}
			document.appendChild(root);
			
			
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
