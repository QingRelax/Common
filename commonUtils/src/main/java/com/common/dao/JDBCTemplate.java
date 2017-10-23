package com.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.common.extractor.Extractor;
import com.common.persistence.ConnectionFactory;
import com.common.rowmapper.RowMapper;

public class JDBCTemplate {
	private static Logger log = Logger.getLogger(JDBCTemplate.class);

	/** return null if find nothing ,else return a list with given class **/

	@SuppressWarnings("rawtypes")
	public static List findAll(String sql, Object[] args, RowMapper rowMapper) {
		Connection connection = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		List results = null;
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			ptmt = connection.prepareStatement(sql);
			if (args != null) {
				for (int i = 0; i < args.length; i++)
					ptmt.setObject(i + 1, args[i]);
				rs = ptmt.executeQuery();
				if (rs != null) {
					results = (List) rowMapper.mapRow(rs);
				}
			}

		} catch (SQLException ex) {
			log.error("[executeQuery ] has Exception:" + ex.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ptmt != null) {
					ptmt.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (SQLException sqe) {
				log.error("exception occurs while trying to close resource:"
						+ sqe.getMessage());
			}
		}
		return results == null || results.size() == 0 ? null : results;
	}

	@SuppressWarnings("rawtypes")
	public static List findAll(String sql, Object[] args, Extractor extractor) {
		log.debug("[JDBCTemplate] [findAll]:" + sql);
		Connection connection = null;
		PreparedStatement ptmt = null;
		List results = null;
		ResultSet rs = null;
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			ptmt = connection.prepareStatement(sql);
			if (args != null) {
				for (int i = 0; i < args.length; i++)
					ptmt.setObject(i + 1, args[i]);
			}
			rs = ptmt.executeQuery();
			if (rs != null) {
				results = (List) extractor.extract(rs);
			}

		} catch (SQLException ex) {
			log.error("[executeQuery ] has Exception:" + ex.getMessage());
			throw new RuntimeException(ex);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ptmt != null) {
					ptmt.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (SQLException sqe) {
				log.error("exception occurs while trying to close resource:"
						+ sqe.getMessage());
				throw new RuntimeException(sqe);
			}
		}
		log.debug("[JDBCTemplate] [findAll] excute ends");
		return results == null || results.size() == 0 ? null : results;
	}
	
	
	public static Object find(String sql, Object[] args, Extractor extractor){
		log.debug("[JDBCTemplate] [find]:" + sql);
		Connection connection = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		Object result = null;
		try {
			connection = ConnectionFactory.getInstance().getConnection();
			ptmt = connection.prepareStatement(sql);
			if (args != null) {
				for (int i = 0; i < args.length; i++)
					ptmt.setObject(i + 1, args[i]);
			}
			rs = ptmt.executeQuery();
			if (rs != null) {
				result = extractor.extract(rs);
			}
			
		} catch (SQLException ex) {
			log.error("[executeQuery ] has Exception:" + ex.getMessage());
			
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ptmt != null) {
					ptmt.close();
				}
				if (connection != null) {
					connection.close();
				}
				
			} catch (SQLException sqe) {
				log.error("exception occurs while trying to close resource:"
						+ sqe.getMessage());
			}
		}
		log.debug("[JDBCTemplate] [find] excute ends");
		return result;
	}

	public static void delete(String sql, Object[] args) {
		log.debug("[JDBCTemplate] [delete]:" + sql);
		Connection connection = null;
		PreparedStatement ptmt = null;

		try {
			connection = ConnectionFactory.getInstance().getConnection();
			connection.setAutoCommit(false);
			ptmt = connection.prepareStatement(sql);
			if (args != null) {
				for (int i = 0; i < args.length; i++)
					ptmt.setObject(i + 1, args[i]);
			}
			ptmt.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			log.error("[JDBCTemplate] [delete] has Exception :"
					+ e.getMessage());
			try {
				connection.rollback();
			} catch (SQLException e1) {
				log.error("[JDBCTemplate] [delete] has Exception :"
						+ e1.getMessage());
			}
			log.error("[deleteReport] SQLException=" + e.getMessage());
		} finally {
			try {

				if (ptmt != null) {
					ptmt.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (SQLException sqe) {
				log.error("[JDBCTemplate] [delete] exception occurs while trying to close resource:"
						+ sqe.getMessage());
			}
		}
		log.debug("[JDBCTemplate] [delete] excute ends");
	}
	
	public static int update(String sql, Object[] parameters){
		if(log.isInfoEnabled()){
		log.info("[JDBCTemplate] [update]sql=" + sql);
		}
		Connection connection = null;
		PreparedStatement ptmt = null;
		int id = -1;
		try {
			
			connection = ConnectionFactory.getInstance().getConnection();
			connection.setAutoCommit(false);
			ptmt = connection.prepareStatement(sql);
			if (parameters != null) {
				for (int i = 0; i < parameters.length; i++)
					ptmt.setObject(i + 1, parameters[i]);
			
			}
			id = ptmt.executeUpdate();
			connection.commit();
			log.info("[updateReport] end.");
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				log.error("[JDBCTemplate] [update] catch SQLException :"
						+ e1.getMessage());
			}
			log.error("[JDBCTemplate] [update] SQLException=" + e.getMessage());
		} catch(Exception ex){
			log.error("[JDBCTemplate] [update] catch Exception :"
					+ ex.getMessage());
			
			throw new RuntimeException(ex);
		}finally {
			try {
				if (ptmt != null) {
					ptmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e) {
				log.error("[JDBCTemplate] [update] Exception=" + e.getMessage());
				log.error("[Exception]: ", e);
			}
		}
		return id;
	}
	
}
