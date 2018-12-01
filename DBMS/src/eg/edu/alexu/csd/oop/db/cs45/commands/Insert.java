package eg.edu.alexu.csd.oop.db.cs45.commands;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Insert extends UpdateQuery {
	
	private String validQuery = "\\s*insert\\s+into\\s+(\\w+)\\s*\\({1}([^\\)\\(]+)\\){1}\\s*values\\s*\\({1}([\\S\\s]+)\\){1}\\s*(?!.)";
	private String validValue = "^('[\\s\\S]+'|\\d+)$";
	
	@Override
	public boolean execute(String query) throws SQLException {
		if(getDB() == null) {
			throw new SQLException();
		}
		if(isValid(query)) {
			Pattern p = Pattern.compile(validQuery,Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(query);
			m.find();
			String tableName = m.group(1).trim();
			String coloumns = m.group(2).trim();
			String values = m.group(3).trim();
			if (values.endsWith(",")) {
				throw new SQLException();
			}
			String[] splitColumns = coloumns.split(",");
			for (String coloumn : splitColumns) {
				coloumn = coloumn.trim();
				if (coloumn.contains(" ")) {
					throw new SQLException();
				}
			}
			String[] splitValues = values.split(",");
			for (String value : splitValues) {
				value = value.trim();
				if (!value.matches(validValue)){
					throw new SQLException();
				}
			}
			if(splitColumns.length != splitValues.length) {
				throw new SQLException();
			}
			this.updatedRows = getDB().insert(tableName,splitColumns,splitValues);
			return true;
		}
		return false;
	}

	@Override
	public boolean isValid(String query) throws SQLException {
		Pattern p = Pattern.compile(validQuery,Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(query);
		if(!m.find()) {
			throw new SQLException();
		}
		return true;
	}

}
