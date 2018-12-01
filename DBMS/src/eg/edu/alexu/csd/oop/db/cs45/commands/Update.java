package eg.edu.alexu.csd.oop.db.cs45.commands;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Update extends UpdateQuery {

	private String basicPattern = "\\s*update\\s+(\\w+)\\s+set\\s+(.*)\\s+(where\\s+(.*)\\s*)?";
	@Override
	public boolean execute(String query) throws SQLException {
		if (getDB() == null) {
			throw new SQLException();
		}

		Pattern p = Pattern.compile(basicPattern, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(query);
		if (!m.find()) {
			throw new SQLException();
		}

		String tableName = m.group(1);
		String updateCols ="set "+ m.group(2).trim();
		String condations = m.group(4);
		
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
		m = Pattern.compile(getCol).matcher(updateCols);
		Map<String, String> updatedColumns = new HashMap<>();
		while (m.find()) {
			String col =m.group(2).trim();
			String value = m.group(3);
			if(col.contains(" ")) {
				throw new SQLException();
			}
			value = value.matches("'") ? value.replaceAll("'", "") : value;
			updatedColumns.put(col, value);
			checkCols++;
		}
		if (checkCols != countCols) {
			throw new SQLException();
		}
		Map<String, String> condation = new HashMap<>();
		if (condations == null || condations.trim().isEmpty()) {
			condation.put("condation", null);
		} else {
			condations = condations.endsWith(";") ? condations : condations + ";";
			String condationPattern = "\\s*(?:(\\w+)\\s*([><=])\\s*('[^']*'|\\d+))\\s*;";
			m = Pattern.compile(condationPattern, Pattern.CASE_INSENSITIVE).matcher(condations);
			if (!m.find()) {
				throw new SQLException();
			}
			String value = m.group(3);
			value = value.matches("'") ? value.replaceAll("'", "") : value;
			condation.put("column", m.group(1));
			condation.put("condation", m.group(2));
			condation.put("value", value);
		}
		updatedRows = getDB().update(tableName, updatedColumns, condation);
		return true;
	}

	@Override
	public boolean isValid(String query) throws SQLException {
		return false;
	}

}
