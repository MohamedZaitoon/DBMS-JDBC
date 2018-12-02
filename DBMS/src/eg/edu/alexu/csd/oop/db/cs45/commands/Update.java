package eg.edu.alexu.csd.oop.db.cs45.commands;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Update extends UpdateQuery {
	 
	private String basicPattern = "\\s*update\\s+(\\w+)\\s+set\\s+([\\s\\S]+)\\s+where\\s+([\\s\\S]+)";
	private String validation2  = "\\s*update\\s+(\\w+)\\s+set\\s+([\\s\\S]+)\\s*";
	@Override
	public boolean execute(String query) throws SQLException {
		if (getDB() == null) {
			throw new SQLException();
		}
		String condations = " ";
		Pattern p = Pattern.compile(basicPattern, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(query);
		if (!m.find()) {
			m = Pattern.compile(validation2, Pattern.CASE_INSENSITIVE).matcher(query);
			if(!m.find()) {
				throw new SQLException();
			}
			condations = null;
		}

		String tableName = m.group(1);
		String updateCols ="set "+ m.group(2).trim();
		condations =condations == null ?null: m.group(3);
		
		if (updateCols == null || updateCols.isEmpty() ||updateCols.endsWith(",")) {
			throw new SQLException();
		}

		String validCols = "set\\s+((?:\\s*(([\\w\\s]+)\\s*=\\s*('[^']*'|\\d+))\\s*,)+)";
		updateCols += ",";
		m = Pattern.compile(validCols).matcher(updateCols);
		if (!m.find()) {
			throw new SQLException();
		}
		updateCols = m.group(1);
		int countCols = new StringTokenizer(" " + updateCols + " ", ",").countTokens() - 1;
		int checkCols = 0;
		String getCol = "(?:(([\\w\\s]+)\\s*=\\s*('[^']*'|\\d+))\\s*,)";
		m = Pattern.compile(getCol,Pattern.CASE_INSENSITIVE).matcher(updateCols);
		ArrayList<Object> updatedColumns = new ArrayList<>();
		ArrayList<Object> updatedValues = new ArrayList<>();
		while (m.find()) {
			String col =m.group(2).trim();
			String value = m.group(3);
			if(col.contains(" ")) {
				throw new SQLException();
			}
			value = value.contains("'") ? value.replaceAll("'", "") : value;
			updatedColumns.add(col);
			updatedValues.add(value);
			checkCols++;
		}
		if (checkCols != countCols) {
			throw new SQLException();
		}
		String operand = null, operator = null,value = null;
		if (condations == null || condations.trim().isEmpty()) {
		} else {
			condations = condations.endsWith(";") ? condations : condations + ";";
			String condationPattern = "\\s*(?:(\\w+)\\s*([><=])\\s*('[^']*'|\\d+))\\s*;";
			m = Pattern.compile(condationPattern, Pattern.CASE_INSENSITIVE).matcher(condations);
			if (!m.find()) {
				throw new SQLException();
			}
			operand = m.group(1);
			operator =  m.group(2);
			value = m.group(3);
			value = value.contains("'") ? value.replaceAll("'", "") : value;
		}
		updatedRows = getDB().update(tableName,updatedColumns, updatedValues, value,operand,operator);
		return true;
	}

	@Override
	public boolean isValid(String query) throws SQLException {
		return false;
	}

}
