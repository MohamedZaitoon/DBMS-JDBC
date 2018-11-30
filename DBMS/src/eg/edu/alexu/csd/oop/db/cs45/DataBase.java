package eg.edu.alexu.csd.oop.db.cs45;

import java.io.File;
import java.io.IOException;
import java.util.Map;

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
	public boolean createTable(Map<String, String> dtd) {
		File f = new File(path+DBMS.getSeparator()+dtd.get("table_name")+".xml");
		try {
			
			return f.createNewFile() && 
					new File(path+DBMS.getSeparator()+dtd.get("table_name")+".dtd").createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean isTableExist(String tableName) {
		File[] tables = new File(path).listFiles();
		for(File f : tables) {
			if(f.isFile() && f.getName().matches(tableName+".[Xx][Mm][Ll]"));{
				return true;
			}
		}
		return false;
		
	}
	public boolean dropTable(String tableName) {
		File[] tables = new File(path).listFiles();
		int n = 0;
		for(File f : tables) {
			if(f.isFile() && f.getName().matches(tableName+".[Xx][Mm][Ll]")){
				f.delete();
				n++;
			}else if(f.isFile() && f.getName().matches(tableName+".[Dd][Tt][Dd]")) {
				f.delete();
				n++;
			}
			if(n == 2) {
				return true;
			}
		}
		return false;
	}
	
	
}