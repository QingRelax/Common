package com.common.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.log4j.Logger;

public class SequencesDao extends BaseDao {

	private static Logger log = Logger.getLogger(SequencesDao.class);

	public long getNextSequence(String seqName) throws Exception {
		long seqNumber = 0;
		Connection con = null;
		try {
			log.debug("[getNextSequence] seqName = " + seqName);
			con = this.getConnection();
			seqNumber = getNextSequence(con, seqName);
			log.debug("[getNextSequence] seqNumber =" + seqNumber);
		} catch (Exception e) {
			con.rollback();
			throw e;
		} finally {
			DbUtils.close(con);
		}
		return seqNumber;
	}
	
	public Long getNextSequence(Connection conn, String seqName) throws Exception {
		List<Long> seqs = getNextSequence(conn, seqName, 1);
		return seqs.get(0);
	}
	
	public List<Long> getNextSequence(Connection conn, String seqName, int noOfSeqs) throws Exception {
		List<Long> results = new ArrayList<Long>();
		
		log.debug("[getNextSequence] seqName = " + seqName + " no of seq = " + noOfSeqs);
		
		try {
			String sql = "SELECT " + seqName + ".NEXTVAL FROM DUAL connect by level<= ?";
			QueryRunner runner = new QueryRunner();
			List<BigDecimal> seqs = runner.query(conn, sql, new ColumnListHandler<BigDecimal>(), new Object[]{noOfSeqs});
			
			if (CollectionUtils.isEmpty(seqs) || seqs.size() != noOfSeqs) {
				throw new Exception("Failed to retrieve " + noOfSeqs + " sequence of " + seqName);
			}
			
			for (BigDecimal seq : CollectionUtils.emptyIfNull(seqs)) {
				results.add(seq.longValue());
			}
		} catch (Exception e) {
			throw e;
		} 
		return results;
	}
}
