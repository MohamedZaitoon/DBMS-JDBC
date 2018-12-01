package eg.edu.alexu.csd.oop.db.cs45;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import eg.edu.alexu.csd.oop.db.xml.SaveAsXml;

public class DataBase {
	/**
	 * The current working table we work on.
	 */
	private Table table;
	/**
	 * name Of database
	 */
	private String name;
	/**
	 * just do some operations between table and file XML.
	 */
	private String path;
	public DataBase(String dbName) {
		this.name = dbName;
		this.path = DBMS.getPath()+DBMS.getSeparator()+dbName;
	}
	public String getPath() {
		return path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param tableName
	 */
	public void setelectTable(String tableName) {
	}
	public boolean createTable(Map<String, String> dtd)  {
		String tableName = dtd.get("table_name");
		File fxml = new File(path+DBMS.getSeparator()+tableName+".xml");
		File fdtd = new File(path+DBMS.getSeparator()+tableName+".dtd");
		if(fxml.exists()&&fdtd.exists()) {
			return false;
		}
		Table table = new Table();
		table.setName(tableName);
		SaveAsXml save = new SaveAsXml(path+DBMS.getSeparator(), table);
		
		return  false;
	}
	public boolean dropTable(String tableName) {
		File fxml = new File(path+DBMS.getSeparator()+tableName+".xml");
		File fdtd = new File(path+DBMS.getSeparator()+tableName+".dtd");
		if(fxml.exists() && fdtd.exists()) {
				fxml.delete();
				fdtd.delete();
				return true;
		}
		return false;
	}
	public int update(String tableName, Map<String, String> updatedColumns, Map<String, String> condation) {

		return 0;
	}
	public Object[][] selectRow(String tableName, String[] splitColoumns, String operand, String operator, String value) {
		
		return null;
	}
	
	public int insert(String tableName, String[] splitColumns, String[] splitValues) {
		return 0;
	}
	public int deleteRow(String tableName, String operand, String operator, String value) {
		return 0;
	}
	
	
}