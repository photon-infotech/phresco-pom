package com.phresco.pom.test;

import java.io.File;

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
	public void prepare() {
	   file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}
	
	@Test
	public void addAndroidProfileTest() throws PhrescoPomException {
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
	}
	@After
	public void delete() {
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}
}
