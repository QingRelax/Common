package com.common.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import org.apache.log4j.Logger;

public class GenNumberDao extends BaseDao {
	private static Logger log = Logger.getLogger(GenNumberDao.class);

	public String generateNumber(Connection conn, String numberType,
			String numberSubType) throws Exception {
		log.debug("[generateNumber(?,?,?)] numberType:" + numberType
				+ ",numberSubType=" + numberSubType);
		String number = "";
		CallableStatement call = null;
		try {
			call = conn.prepareCall("{? = call UTL.gen_number(?,?) }");
			call.registerOutParameter(1, Types.VARCHAR);
			call.setString(2, numberType);
			call.setString(3, numberSubType);
			call.execute();
			number = call.getString(1);
			log.debug("[generateNumber(?,?,?)] number:" + number);
		} catch (Exception e) {
			log.error("[generateNumber] has exception:" + e.getMessage());
			throw e;
		} finally {
			closeResources(call);
		}
		return number;
	}

	public String generateNumber(String numberType, String numberSubType)
			throws Exception {
		Connection conn = null;
		CallableStatement call = null;
		String number = "";
		try {
			log.trace("[generateNumber(?,?)] numberType:" + numberType
					+ ",numberSubType=" + numberSubType);
			conn = this.getConnection();
			call = conn.prepareCall("{? = call UTL.gen_number(?,?) }");
			call.registerOutParameter(1, Types.VARCHAR);
			call.setString(2, numberType);
			call.setString(3, numberSubType);
			call.execute();
			number = call.getString(1);
		} catch (Exception e) {
			log.error("[generateNumber(?,?)] has exception:" + e.getMessage());
			try {
				conn.rollback();
			} catch (Exception sqle) {
				throw sqle;
			}
			throw e;
		} finally {
			closeResources(conn, call);
		}
		log.debug("[generateNumber(?,?)] generateNumber:" + number);
		return number;
	}

	public String generateGF277No(Connection con, Long organizationId)
			throws Exception {
		log.debug("[generateGF277No] organizationId=" + organizationId);
		String sql = "";
		CallableStatement call = null;
		try {
			sql = "{? = call UTL.GEN_GF277_NUMBER(?)}";
			call = con.prepareCall(sql);
			call.registerOutParameter(1, Types.VARCHAR);
			call.setLong(2, organizationId);
			call.execute();
			return call.getString(1);
		} catch (Exception e) {
			log.error("[generateGF277No] has exception:" + e.getMessage());
			throw e;
		} finally {
			closeResources(call);
		}
	}

	public String generateGF278No(Connection con, Long organizationId)
			throws Exception {
		log.debug("[generateGF278No] organizationId=" + organizationId);
		String sql = "";
		CallableStatement call = null;
		try {
			sql = "{? = call UTL.GEN_GF278_NUMBER(?)}";
			call = con.prepareCall(sql);
			call.registerOutParameter(1, Types.VARCHAR);
			call.setLong(2, organizationId);
			call.execute();
			return call.getString(1);
		} catch (Exception e) {
			log.error("[generateGF278No] has exception:" + e.getMessage());
			throw e;
		} finally {
			closeResources(call);
		}
	}

}
