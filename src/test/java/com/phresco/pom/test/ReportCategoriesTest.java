package com.phresco.pom.test;


import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.phresco.pom.site.ReportCategories;

public class ReportCategoriesTest {
	ReportCategories reportCategories;
	@Before
	public void setUp() throws Exception {
		 reportCategories=new ReportCategories("test",true);
	}

	
	
	@Test
	public void isEnabledTest(){
		Assert.assertEquals(true, reportCategories.isEnabled());
	}
	@Test
	public void setEnabledTest(){
		ReportCategories reportCategories1=new ReportCategories("test",false);
		reportCategories1.setEnabled(true);
		Assert.assertEquals(true, reportCategories1.isEnabled());
	}
}
