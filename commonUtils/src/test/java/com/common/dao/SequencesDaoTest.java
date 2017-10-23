package com.common.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.junit.Test;

public class SequencesDaoTest extends BaseDaoTest {

	Logger log = Logger.getLogger(SequencesDaoTest.class);
	
	@Test
	public void testGetNextSeqByName() {		
		
		Random r = new Random();
		int noOfIds = r.nextInt(50);
		
		List<Long> idList = null;
		
		try {
			Connection conn = getConnection();
			idList = new SequencesDao().getNextSequence(conn,"TST_SEQ_S", noOfIds);
			conn.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		assertNotNull(idList);
		assertEquals(noOfIds, idList.size());
		
		Long i = null;
		
		for (Long id : idList) {
			assertNotNull(id);
			log.info(id);
			if (i == null) {
				i = id;
			}
			else {
				assertEquals(id, ++i);
				i = id;
			}
		}
	}
}
