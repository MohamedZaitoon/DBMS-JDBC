package eg.edu.alexu.csd.oop.db.cs45.commands;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.alexu.csd.oop.db.Command;
import eg.edu.alexu.csd.oop.db.cs45.DBMS;
import eg.edu.alexu.csd.oop.db.cs45.DataBase;

public class Delete implements Command {

	private String validation ="\\s*delete\\s+from\\s+([a-zA-Z_0-9]+)"
			+ "(\\s+where\\s+([a-zA-Z_0-9]+)\\s*([<>=])\\s*('(.*)'|(\\d+))\\s*)?";
	@Override
	public boolean execute(String query) throws SQLException {
		if(getDB() == null) {
			throw new SQLException();
		}
		if(isValid(query)) {
			String tableName;
			Pattern p = Pattern.compile(validation, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(query);
			m.find();
			tableName = m.group(1);
			if(!getDB().isTableExist(tableName)) {
				return false;
			}
			if(m.group(2) == null) {
				
			}else {
				String columnName = m.group(3);
				String operation = m.group(4);
				String value = m.group(5);
				if(value.contains("'")) {
					value = value.replaceAll("'", "");
				}
				
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean isValid(String query) throws SQLException {
		Pattern p = Pattern.compile(validation, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(query);
		if (!m.find()) {
			throw new SQLException();
		}
		return true;
	}

	private DataBase getDB() { 
		return DBMS.getDatabase();
	}
}
