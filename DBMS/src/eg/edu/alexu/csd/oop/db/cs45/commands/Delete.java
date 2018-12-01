package eg.edu.alexu.csd.oop.db.cs45.commands;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Delete extends UpdateQuery {

	private String validation ="\\s*delete\\s+from\\s+(.*(?!where))"
			+ "(\\s+where\\s+(\\w+)\\s*([<>=])\\s*('([^']*)'|(\\d+))\\s*)?";
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
				System.out.println("done");
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

}
