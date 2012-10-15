package com.phresco.pom.test;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.model.Plugin;
import com.phresco.pom.util.PomProcessor;

public class DeletePluginTest {
	
	@Before
	public void prepare() throws IOException{
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}
	
	@Test
	public void deletePluginTest() throws ParserConfigurationException, PhrescoPomException{
		try {
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			processor.addPlugin("com.photon.phresco.plugin", "phresco-maven-plugin", "1.2.0.3001");
			processor.save();
			processor.deletePlugin("com.photon.phresco.plugin", "phresco-maven-plugin");
			processor.save();
			Plugin plugin = processor.getPlugin("com.photon.phresco.plugin", "phresco-maven-plugin");
			Plugin actual = plugin;
			Plugin expected = null;
			Assert.assertEquals(expected, actual);
		} catch (JAXBException e) {
			Assert.fail("Plugin Not Found!");
		} catch (IOException e) {
			Assert.fail("Plugin Not Found!");
		}
	}
	
	@After
	public void delete(){
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}

}
