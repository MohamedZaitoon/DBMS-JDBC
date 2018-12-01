package eg.edu.alexu.csd.oop.db.cs45.commands;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Delete extends UpdateQuery {

	private String validation = "\\s*delete\\s+from\\s+(\\w+)(?:([\\s\\S]+))?";
	private String validCondition = "^\\s*where\\s+([\\w]+)\\s*(<=|>=|[=><])\\s*([\\s\\S]+)";
	private String validValue = "^('[\\s\\S]+'|\\d+)$";

	@Override
	public boolean execute(String query) throws SQLException {
		if (getDB() == null) {
			throw new SQLException();
		}
		if (isValid(query)) {
			Pattern p = Pattern.compile(validation, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(query);
			m.find();
			String tableName = m.group(1);
			String condition =  m.group(2);
			String operand = null, operator = null, value = null;
			if (condition != null) {
				m = Pattern.compile(validCondition).matcher(condition);
				if (!m.find()) {
					throw new SQLException();
				}
				operand = m.group(1).trim();
				operator = m.group(2).trim();
				m = Pattern.compile(validValue).matcher(m.group(3));
				if (!m.find()) {
					throw new SQLException();
				}
				value = m.group(1).trim();
				value = value.contains("'")?value.replaceAll("'", ""):value;
			}
			this.updatedRows = getDB().deleteRow(tableName, operand, operator, value);
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
