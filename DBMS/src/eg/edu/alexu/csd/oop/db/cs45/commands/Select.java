package eg.edu.alexu.csd.oop.db.cs45.commands;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.alexu.csd.oop.db.Command;
import eg.edu.alexu.csd.oop.db.cs45.DBMS;
import eg.edu.alexu.csd.oop.db.cs45.DataBase;

public class Select implements Command{
	
	private String validQuery = "\\s*select\\s+(?:([\\w\\s,]+|\\*))\\s+from\\s+(\\w+)\\s*(?:([\\s\\S]+))?";
	private String validCondition = "^\\s*where\\s+([\\w]+)\\s*(<=|>=|[=><])\\s*([\\s\\S]+)";
	private String validValue = "^('[\\s\\S]+'|\\d+)$";
	private Object[][] selected;
	
	public Object[][] getSelected() {
		Object[][] temp = selected;
		selected = null;
		return temp;
	}

	@Override
	public boolean execute(String query) throws SQLException  {
		if(getDB() == null) {
			throw new SQLException();
		}
		if (isValid(query)) {
			Pattern p = Pattern.compile(validQuery,Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(query);
			m.find();
			String coloumns = m.group(1).trim();
			String tableName = m.group(2);
			String condition = m.group(3);
			if (coloumns.endsWith(",")) {
				throw new SQLException();
			}
			String[] splitColoumns = coloumns.split(",");
			for (String coloumn : splitColoumns) {
				coloumn = coloumn.trim();
				if (coloumn.contains(" ")) {
					throw new SQLException();
				}
			}
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
			selected = getDB().selectRow(tableName, splitColoumns, operand, operator, value);
			return true;
		}
		return false;
	}

	private DataBase getDB() {
		return DBMS.getDatabase();
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
