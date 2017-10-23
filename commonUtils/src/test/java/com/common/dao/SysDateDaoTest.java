package com.common.dao;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;

public class SysDateDaoTest extends BaseDaoTest {

	Logger log = Logger.getLogger(SysDateDaoTest.class);
	
	@Test
	public void testGetSysDate() {		
		Date dbDate = new SysDateDao().getSysDate();
		
		assertNotNull(dbDate);
		log.info(SimpleDateFormat.getInstance().format(dbDate));
		
		Calendar javaCalendar = Calendar.getInstance();		
		Calendar dbCalendar = Calendar.getInstance();
		dbCalendar.setTime(dbDate);
		
		assertEquals(javaCalendar.get(Calendar.YEAR), dbCalendar.get(Calendar.YEAR));
		assertEquals(javaCalendar.get(Calendar.MONTH), dbCalendar.get(Calendar.MONTH));
		assertEquals(javaCalendar.get(Calendar.DATE), dbCalendar.get(Calendar.DATE));
	}

}
