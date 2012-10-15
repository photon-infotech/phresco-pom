package com.phresco.pom.test;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.model.BuildBase;
import com.phresco.pom.model.Profile;
import com.phresco.pom.model.Profile.Modules;
import com.phresco.pom.util.AndroidPomProcessor;

public class AndroidPomProcessorTest {

	private File file;
	
	@Before
	public void prepare() throws IOException {
	   file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}
	
	@Test
	public void addAndroidProfileTest() {
		try {
			AndroidPomProcessor processor = new AndroidPomProcessor(file);
			BuildBase build = new BuildBase();
			build.setDefaultGoal("install");
			build.setFinalName("service");
			Modules modules = new Modules();
			modules.getModule().add("phresco-pom");
			processor.addProfile("phresco", build, modules);
			Profile profile = processor.getProfile("phresco");
			if(profile.getBuild().getDefaultGoal().equals("install")) {
				Assert.assertTrue(true);
			} else {
				Assert.fail();
			}
		} catch (JAXBException e) {
		} catch (IOException e) {
		} catch (PhrescoPomException e) {
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
