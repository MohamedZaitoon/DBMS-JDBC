package eg.edu.alexu.csd.oop.db.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

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
			ArrayList<String> dataTypes = new ArrayList<>();
			//get names of tags & get dataTypes
			for (int j = 0; j < row.getLength(); j++) {
				Node col = row.item(j);
				tableRow.add(col.getNodeName());
				dataTypes.add(col.getAttributes().getNamedItem("type").getNodeValue());
				//.getChildNodes().item(0).getAttributes().getNamedItem("data").getNodeValue());
			}
			tableAsArrayList.add(0, tableRow);
			tableRow = new ArrayList<Object>();	
			for (int i = 0; i < rows.getLength(); i++) {
					row = rows.item(i).getChildNodes();
					for (int j = 0; j < row.getLength(); j++) {
						Node col = row.item(j);
						tableRow.add(col.getTextContent());
					}
					tableAsArrayList.add(tableRow);
					tableRow = new ArrayList<Object>();	
			}
			table.setTable(tableAsArrayList);
			table.setDataTypes(dataTypes);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return table;
	}

	public boolean domValidationWithDtd() throws ParserConfigurationException, IOException {
		
		try {
		      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		      factory.setValidating(true);
		      factory.setNamespaceAware(true);

		      DocumentBuilder builder = factory.newDocumentBuilder();

		      builder.setErrorHandler(
		          new ErrorHandler() {
		            public void warning(SAXParseException e) throws SAXException {
		              System.out.println("WARNING : " + e.getMessage());
		            }

		            public void error(SAXParseException e) throws SAXException {
		              System.out.println("ERROR : " + e.getMessage());
		              throw e;
		            }

		            public void fatalError(SAXParseException e) throws SAXException {
		              System.out.println("FATAL : " + e.getMessage());
		              throw e;
		            }
		          }
		          );
		      builder.parse(new InputSource(path));
		      return true;
		    }
		    catch (ParserConfigurationException pce) {
		      throw pce;
		    } 
		    catch (IOException io) {
		      throw io;
		    }
		    catch (SAXException se){
		      return false;
		    }
		  }
}
