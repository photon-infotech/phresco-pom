package com.phresco.pom.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.model.Dependency;
import com.phresco.pom.util.PomProcessor;

public class DependencySystemPathTest {
	
	@Before
	public void prepare() throws IOException{
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}
	
	@Test
	public void validSetDependencySystemPath() throws ParserConfigurationException, PhrescoPomException {
		try {
			String systemPath = "";
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			processor.addDependency("com.photon.phresco.test", "com.photon.phresco.test", "1.2.0");
			processor.setDependencySystemPath("com.photon.phresco.test", "com.photon.phresco.test", "SystemPath");
			processor.save();
			List<Dependency> dependencyList = processor.getModel().getDependencies().getDependency();
			for (Dependency dependency : dependencyList) {
				systemPath = dependency.getSystemPath();
			}
			String actual = systemPath;
			String expected = "SystemPath";
			Assert.assertEquals(expected, actual);
		} catch (JAXBException e) {
			Assert.fail("Change Version Failed!");
		} catch (IOException e) {
			Assert.fail("Change Version Failed!");
		}
	}
	
	@Test
	public void invalidSetDependencySystemPath() throws ParserConfigurationException, PhrescoPomException {
		try {
			String systemPath = "";
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			processor.addDependency("com.photon.phresco.test", "com.photon.phresco.test", "1.2.0");
			processor.setDependencySystemPath("com.photon.phresco.test", "com.photon.phresco", "SystemPath");
			processor.save();
			List<Dependency> dependencyList = processor.getModel().getDependencies().getDependency();
			for (Dependency dependency : dependencyList) {
				systemPath = dependency.getSystemPath();
			}
			String actual = systemPath;
			String expected = null;
			Assert.assertEquals(expected, actual);
		} catch (JAXBException e) {
			Assert.fail("Dependency Not valid!");
		} catch (IOException e) {
			Assert.fail("Dependency Not valid!");
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
