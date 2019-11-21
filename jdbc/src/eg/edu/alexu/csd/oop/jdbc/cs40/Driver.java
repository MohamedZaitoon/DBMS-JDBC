package eg.edu.alexu.csd.oop.jdbc.cs40;

import java.sql.Connection;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public abstract class Driver implements java.sql.Driver{

	@Override
	public abstract boolean acceptsURL(String arg0) throws SQLException;

	@Override
	public abstract Connection connect(String arg0, Properties arg1) throws SQLException;

	@Override
	public abstract DriverPropertyInfo[] getPropertyInfo(String arg0, Properties arg1) throws SQLException;
	
	@Override
	public int getMajorVersion() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getMinorVersion() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new UnsupportedOperationException();
	}

	

	@Override
	public boolean jdbcCompliant() {
		throw new UnsupportedOperationException();
	}

}
