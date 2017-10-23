package com.common.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import com.common.utils.DateUtil;

public class SysDateDao extends BaseDao {

	private static Logger log = Logger.getLogger(SysDateDao.class);

	public Date getSysDate() {
		Date sysDate = null;

		String queryString = "SELECT SYSTIMESTAMP FROM DUAL";

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(queryString);
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				sysDate = rs.getTimestamp("SYSTIMESTAMP");
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
			sysDate = Calendar.getInstance().getTime();
		} finally {
			DbUtils.closeQuietly(conn, ps, rs);
		}

		return sysDate;
	}

	public Date getWorkingDayByStartDateAndCountDay(Date startDate, int countValue) {

		Date workingDate = null;

		Connection conn = null;
		CallableStatement call = null;

		try {
			conn = getConnection();
			call = conn.prepareCall("{? = call utl.add_workingday(?,?) }");
			call.registerOutParameter(1, Types.DATE);
			call.setDate(2, DateUtil.convertToDBDate(startDate));
			call.setInt(3, countValue);
			call.execute();
			workingDate = call.getDate(1);
			log.debug("[getWorkingDayByStartDateAndCountDay] workingDate = " + workingDate);
		} catch (Exception e) {
			log.error("[Exception]: ", e);
		} finally {
			DbUtils.closeQuietly(conn, call, null);
		}
		return workingDate;
	}

}
