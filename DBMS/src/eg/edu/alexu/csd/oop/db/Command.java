package eg.edu.alexu.csd.oop.db;

import java.sql.SQLException;

public interface Command {
	public boolean execute(String query)  throws SQLException;
	public boolean isValid(String query) throws SQLException;
}
