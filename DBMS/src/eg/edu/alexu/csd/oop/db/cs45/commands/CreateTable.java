package eg.edu.alexu.csd.oop.db.cs45.commands;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.alexu.csd.oop.db.Command;
import eg.edu.alexu.csd.oop.db.cs45.DBMS;
import eg.edu.alexu.csd.oop.db.cs45.DataBase;

public class CreateTable implements Command {
	/**
	 * Pattern of word
	 */
	private final String COLUMN = "^\\s*(\\w+)\\s+(int|varchar)\\s*$";
	
	/**
	 * The validation pattern for the Create Table statement.
	 */
	private String validation = "\\s*create\\s+table\\s+(\\w+)\\s*\\({1}\\s*([\\s\\S]+)\\){1}\\s*(?!.)";
	
	@Override
	public boolean execute(String query) throws SQLException {
		if(getDB() == null) {
			throw new SQLException();
		}
		if(isValid(query)) {
			Map<String, String> dtd = new HashMap<>();
			Pattern p = Pattern.compile(validation,Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(query);
			m.find();
			String tableName = m.group(1).trim();
			getDB().tableIsExist(tableName);
			String cols = m.group(2).trim();
			if(cols.isEmpty() || cols.endsWith(",")) {
				throw new SQLException();
			}
			String[] splitCol = cols.split(","); 
			String[] columns = new String[splitCol.length];
			String[] dataTypes = new String[splitCol.length];
			int i = 0;
			for(String info : splitCol) {
				m = Pattern.compile(COLUMN).matcher(info);
				if(m.find()) {
					columns[i] = m.group(1).trim();
					if(dtd.get(columns[i]) != null) {
						throw new SQLException();
					}
					dataTypes[i] = m.group(2);
					dtd.put(columns[i], dataTypes[i]);
					i++;
				}else {
					throw new SQLException();
				}
			}
			
			return getDB().createTable(tableName,columns,dataTypes);
		}
		return false;
	}

	@Override
	public boolean isValid(String query) throws SQLException {
		Pattern p = Pattern.compile(validation,Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(query);
		if(!m.find()) {
			throw new SQLException();
		}
		return true;
	}
	
	private DataBase getDB() { 
		return DBMS.getDatabase();
	}
}
