package com.phresco.pom.test;


import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.phresco.pom.site.SiteMessages;
import com.phresco.pom.util.PomElementCreator;
import com.phresco.pom.util.PomProcessor;

public class SiteMessagesTest {

	@Before
	public void setUp() throws Exception {
	}

	
	@Test()
	public void getStringExcTest(){
		String str=SiteMessages.getString("test");

	}
	
	@Test()
	public void constructorTest() throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException{
		Constructor<SiteMessages> reflected = SiteMessages.class.getDeclaredConstructor();
		reflected.setAccessible(true);
		SiteMessages initialize = reflected.newInstance();

	}
}
