package com.phresco.pom.test;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;

public class SetSourceDirectoryTest {
	
	@Before
	public void prepare() throws IOException {
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}
	
	@Test
	public void setSourceDirectoryTest() throws ArrayIndexOutOfBoundsException, PhrescoPomException{
		try {
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			processor.setSourceDirectory("src");
			processor.save();
			String sourceDirectory = processor.getSourceDirectory();
			Assert.assertEquals(sourceDirectory, "src");
		} catch (JAXBException e) {
		} catch (IOException e) {
		}
	}
	
	@After
	public void delete() {
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}

}
