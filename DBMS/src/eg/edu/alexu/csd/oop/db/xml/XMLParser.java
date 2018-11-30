package eg.edu.alexu.csd.oop.db.xml;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import eg.edu.alexu.csd.oop.db.cs45.Table;

public class XMLParser {

	private String path;
	public void setPath(String path) {
		this.path = path;
	}

	public XMLParser(String path) {
		this.path = path;
	}

	public Table getTable() {
		Table table = new Table();
		ArrayList<ArrayList<Object>> tableAsArrayList = new ArrayList<
				ArrayList<Object>>(); 
		ArrayList<Object> tableRow = new ArrayList<Object>();
		File input = new File(path);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(input);
			doc.getDocumentElement().normalize();
			table.setName(doc.getDocumentElement().getNodeName());
			NodeList rows = doc.getElementsByTagName("row");
			NodeList row = rows.item(0).getChildNodes();
			for (int j = 0; j < row.getLength(); j++) {
				Node col = row.item(j);
				tableRow.add(col.getNodeName());
			}
			tableAsArrayList.add(0, tableRow);
			tableRow = new ArrayList<Object>();	
			for (int i = 0; i < rows.getLength(); i++) {
					row = rows.item(i).getChildNodes();
					for (int j = 0; j < row.getLength(); j++) {
						Node col = row.item(j);
						tableRow.add(col.getTextContent());
					}
					tableAsArrayList.add(i, tableRow);
					tableRow = new ArrayList<Object>();	
			}
			table.setTable(tableAsArrayList);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return table;
	}

	
}
