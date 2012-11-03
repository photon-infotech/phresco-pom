package com.phresco.pom.test;

import java.io.File;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;

public class SetSourceDirectoryTest {
	
	@Before
	public void prepare() {
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}
	
	@Test
	public void setSourceDirectoryTest() throws PhrescoPomException{
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		processor.setSourceDirectory("src");
		processor.save();
		String sourceDirectory = processor.getSourceDirectory();
		Assert.assertEquals(sourceDirectory, "src");
	}
	
	@After
	public void delete() {
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}

}
