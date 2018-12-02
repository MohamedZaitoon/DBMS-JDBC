package eg.edu.alexu.csd.oop.db.cs45.commands;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Insert extends UpdateQuery {

	private String validQuery = "\\s*insert\\s+into\\s+(\\w+)\\s*(?:\\({1}([^()]+)\\){1})?\\s*values\\s*\\({1}([\\S\\s]+)\\){1}\\s*(?!.)";
	private String validValue = "^('[\\s\\S]+'|\\d+)$";

	@Override
	public boolean execute(String query) throws SQLException {
		if (getDB() == null) {
			throw new SQLException();
		}
		if (isValid(query)) {
			Pattern p = Pattern.compile(validQuery, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(query);
			m.find();
			String tableName = m.group(1).trim();
			String coloumns = (m.group(2)!= null)?m.group(2).trim():null;
			String values = m.group(3).trim();
			if (values.endsWith(",")) {
				throw new SQLException();
			}
			String[] splitColumns = null;
			String[] splitValues;
			if ( coloumns != null) {
				splitColumns = coloumns.split(",");
				for (String coloumn : splitColumns) {
					coloumn = coloumn.trim();
					if (coloumn.contains(" ")) {
						throw new SQLException();
					}
				}
			}
				splitValues = values.split(",");
				int size = splitValues.length;
				for (int i = 0; i < size; i++) {
					splitValues[i] = splitValues[i].trim();
					if (!splitValues[i].matches(validValue)) {
						throw new SQLException();
					}
					splitValues[i]= splitValues[i].contains("'")?splitValues[i].replaceAll("'", ""):splitValues[i];
					System.out.println(splitValues[i]);
				}
				/*if (splitColumns.length != splitValues.length) {
					throw new SQLException();
				}*/
			
			this.updatedRows = getDB().insert(tableName, splitColumns, splitValues);
			return true;
		}
		return false;
	}

	@Override
	public boolean isValid(String query) throws SQLException {
		Pattern p = Pattern.compile(validQuery, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(query);
		if (!m.find()) {
			throw new SQLException();
		}
		return true;
	}

}
