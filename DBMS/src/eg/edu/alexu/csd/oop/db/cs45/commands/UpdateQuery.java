package eg.edu.alexu.csd.oop.db.cs45.commands;

import java.sql.SQLException;

import eg.edu.alexu.csd.oop.db.Command;
import eg.edu.alexu.csd.oop.db.cs45.DBMS;
import eg.edu.alexu.csd.oop.db.cs45.DataBase;

public abstract class UpdateQuery implements Command {
	protected int updatedRows;
	@Override
	public abstract boolean execute(String query) throws SQLException;

	@Override
	public abstract boolean isValid(String query) throws SQLException;

	public int getUpdatedRows() {
		int temp = updatedRows;
		updatedRows = 0;
		return temp;
	}
	protected DataBase getDB() { 
		return DBMS.getDatabase();
	}
}
