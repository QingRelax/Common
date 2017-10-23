package com.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.log4j.Logger;

import com.common.persistence.ConnectionFactory;

public abstract class BaseDao {
	// OracleTypes.CURSOR = -10
	public final static int OracleTypesCursor = -10;
	static Logger log = Logger.getLogger(BaseDao.class);

	protected Connection getConnection() throws SQLException {
		Connection conn = ConnectionFactory.getInstance().getConnection();
		return conn;
	}

	protected int update(Connection conn, String sql, Object[] param) throws Exception {
		QueryRunner qRunner = new QueryRunner();
		int count = qRunner.update(conn, sql, param);
		return count;
	}

	protected void batchUpdate(Connection conn, String sql, Object[][] params) throws Exception {
		QueryRunner qRunner = new QueryRunner();
		qRunner.batch(conn, sql, params);
	}

	protected void closeResources(Connection conn) {
		closeResources(conn, null, null);
	}

	protected void closeResources(Connection conn, Statement ps) {
		closeResources(conn, ps, null);
	}

	protected void closeResources(Statement ps) {
		closeResources(null, ps, null);
	}

	protected void closeResources(Statement ps, ResultSet rs) {
		closeResources(null, ps, rs);
	}

	protected void closeResources(Connection conn, Statement ps, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException sqle) {
			log.error("[BaseDao] CLOSE Exception :" + sqle.getMessage());
		}
		try {
			if (ps != null) {
				ps.close();
			}
		} catch (SQLException sqle) {
			log.error("[BaseDao] CLOSE Exception :" + sqle.getMessage());
		}
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException sqle) {
			log.error("[BaseDao] CLOSE Exception :" + sqle.getMessage());
		}
	}

	/**
	 * Assume using standard audit field in the following sequence (Date is
	 * ignored since it use DB system datetime)
	 * 
	 * CREATE_BY_POST CREATE_BY_UI CREATE_BY_NAME LAST_UPDATED_POST
	 * LAST_UPDATED_UI LAST_UPDATED_NAME
	 * 
	 * @param ps
	 * @param index
	 * @param createdByPost
	 * @param createByUi
	 * @param createByName
	 * @param updateByPost
	 * @param updateByUi
	 * @param updateByName
	 */
	protected int setAuditFields(PreparedStatement ps, int index, Long createdByPost, String createByUi, String createByName,
			Long updateByPost, String updateByUi, String updateByName) throws SQLException {

		ps.setLong(index++, createdByPost);
		ps.setString(index++, createByUi);
		ps.setString(index++, createByName);
		ps.setLong(index++, updateByPost);
		ps.setString(index++, updateByUi);
		ps.setString(index++, updateByName);

		return index;
	}

}
