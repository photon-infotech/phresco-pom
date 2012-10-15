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

public class SetSCMTest {

	@Before
	public void prepare() throws IOException {
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}
	
	@Test
	public void setSCMTest() throws ArrayIndexOutOfBoundsException, PhrescoPomException{
		try {
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			processor.setSCM("test", "test", "http://localhost:8080", "phresco");
			processor.save();
			String tag = processor.getModel().getScm().getTag();
			Assert.assertEquals(tag,"phresco");
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
