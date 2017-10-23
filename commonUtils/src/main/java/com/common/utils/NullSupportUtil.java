package com.common.utils;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class NullSupportUtil {
	// properly sets database null for primitive types long, double (used for ID, quantity, and price)
	
	public static void setInteger(PreparedStatement ps, int index, Integer value) throws SQLException {
		if (value == null)
			ps.setNull(index, java.sql.Types.INTEGER);
		else
			ps.setDouble(index, value);
	}
	
	public static void setLong(PreparedStatement ps, int index, Long value) throws SQLException {
		if (value == null)
			ps.setNull(index, java.sql.Types.BIGINT);
		else
			ps.setLong(index, value);
	}
	
	/*
	public static void setDouble(PreparedStatement ps, int index, Double value) throws SQLException {
		if (value == null)
			ps.setNull(index, java.sql.Types.DOUBLE);
		else
			ps.setDouble(index, value);
	}
	*/

	public static void setBigDecimal(PreparedStatement ps, int index, BigDecimal value) throws SQLException {
		if (value == null)
			ps.setNull(index, java.sql.Types.DECIMAL);
		else
			ps.setBigDecimal(index, value);
	}

	public static void setTimestamp(PreparedStatement ps, int index, Date value) throws SQLException {
		if (value == null)
			ps.setNull(index, java.sql.Types.TIMESTAMP);
		else
			ps.setTimestamp(index, DateUtil.convertToDBTimestamp(value));
	}
	
	public static void setString(PreparedStatement ps, int index, String value) throws SQLException {
		if (value == null)
			ps.setNull(index, java.sql.Types.VARCHAR);
		else
			ps.setString(index, value);
	}
}
