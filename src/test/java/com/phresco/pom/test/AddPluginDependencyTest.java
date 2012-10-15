package com.phresco.pom.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.model.Dependency;
import com.phresco.pom.model.Plugin;
import com.phresco.pom.util.PomProcessor;

public class AddPluginDependencyTest {
	
	@Before
	public void prepare() throws IOException {
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
		try {
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			processor.addPlugin("com.photon.phresco.plugin", "phresco-mavn-plugin", "2.0");
			processor.save();
		} catch (JAXBException e) {
		} catch (PhrescoPomException e) {
		}
		
	}
	
	@Test
	public void validAddPluginDependency() throws PhrescoPomException{
		try {
			String artifactId = "";
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			Dependency dependency = new Dependency();
			dependency.setArtifactId("phresco");
			dependency.setGroupId("photon");
			processor.addPluginDependency("com.photon.phresco.plugin", "phresco-mavn-plugin", dependency);
			processor.save();
			Plugin plugin = processor.getPlugin("com.photon.phresco.plugin", "phresco-mavn-plugin");
			List<Dependency> dep = plugin.getDependencies().getDependency();
			for (Dependency depend : dep) {
				artifactId = depend.getArtifactId();
			}
			String actual = artifactId;
			String expected = "phresco";
			Assert.assertEquals(actual, expected);
		} catch (JAXBException e) {
			Assert.fail("Add Dependency Failed!");
		} catch (IOException e) {
			Assert.fail("Add Dependency Failed!");
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
