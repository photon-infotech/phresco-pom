package com.phresco.pom.test;


import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.phresco.pom.site.Reports;

public class ReportsTest {
	Reports reports;
	@Before
	public void setUp() throws Exception {
		 reports=new Reports();
	}

	@Test
	public void isEnabledTest(){
		reports.setEnabled(true);
		Assert.assertEquals(true, reports.isEnabled());
	}
	
	@Test
	public void getDisplayNameTest(){
		reports.setDisplayName("test");
		Assert.assertEquals("test", reports.getDisplayName());
	}

	@Test
	public void setVersionTest(){
		reports.setVersion("1.1");
	}
	
	@Test
	public void getTechIdTest(){
		reports.setTechId("test_id");
		Assert.assertEquals("test_id", reports.getTechId());
	}
	
	@Test
	public void getIdTest(){
		reports.setId("test_id11");
		Assert.assertEquals("test_id11", reports.getId());
	}	
	
}
