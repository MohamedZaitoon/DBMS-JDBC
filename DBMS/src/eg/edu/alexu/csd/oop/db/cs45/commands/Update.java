package eg.edu.alexu.csd.oop.db.cs45.commands;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.alexu.csd.oop.db.Command;

public class Update implements Command {

	private String validation1 = "\\s*update\\s+(\\w+)\\s+set\\s+"
			+ "(((\\w+)\\s*=\\s*('.*'|\\d+)\\s*,\\s*)*)?((\\w+)\\s*=\\s*('.*'|\\d+)\\s+)"
			+ "(where\\s+(\\w+)\\s*([<>=])?\\s*('.*'|\\d+)?)?";
	private String basicPattern = "\\s*update\\s+(\\w+)\\s+set\\s+(.*)\\s+(where\\s+(.*)\\s*)?";
	

	@Override
	public boolean execute(String query) throws SQLException {
		String q = query.toString().trim();
		Pattern p = Pattern.compile(basicPattern, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(query);
		if (!m.find()) {
			throw new SQLException();
		}
		String tableName = m.group(1);
		String updateCols = m.group(2);
		String condation = m.group(4);
		String cols = "((\\w+)\\s*=\\s*())";
		return false;
	}

	@Override
	public boolean isValid(String query) throws SQLException {
		Pattern p = Pattern.compile(basicPattern, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(query);
		if (!m.find()) {
			throw new SQLException();
		}
		return true;
	}

}
