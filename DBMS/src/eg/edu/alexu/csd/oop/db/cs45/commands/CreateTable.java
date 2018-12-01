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
	private final String WORDP = "[a-zA-Z_0-9]";
	private final String COLUMN = "(("+WORDP+"+)\\s+("+WORDP+"+)\\s*,?\\s*)";
	
	/**
	 * The validation pattern for the Create Table statement.
	 */
	private String validation = "\\s*create\\s+table\\s+("+WORDP+"+)\\s*\\(\\s*("+COLUMN+"*)\\)\\s*";
	
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
			if(getDB().isTableExist(m.group(1))) {
				return false;
			}
			dtd.put("table_name", m.group(1));
			String cols = m.group(2);
			m = Pattern.compile(COLUMN).matcher(cols);
			while(m.find()) {
				String colName = m.group(2);
				if(dtd.get(colName) != null) {
					throw new SQLException();
				}
				dtd.put(colName, m.group(3));
			}
			return getDB().createTable(dtd);
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
