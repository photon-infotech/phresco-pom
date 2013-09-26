package com.phresco.pom.test;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.Test;

import com.phresco.pom.util.PomElementCreator;

public class PomElementCreatorTest {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void constructorTest() throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException{
		
		Constructor<PomElementCreator> reflected = PomElementCreator.class.getDeclaredConstructor();
		reflected.setAccessible(true);
		PomElementCreator initialize = reflected.newInstance();
	}

}
