package com.phresco.pom.test;

import java.io.File;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.model.BuildBase;
import com.phresco.pom.model.Profile.Modules;
import com.phresco.pom.util.PomProcessor;

public class AddProfileWithBuildBaseTest {

	@Before
	public void prepare() {
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}

	@Test
	public void addProfileTest() throws PhrescoPomException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		BuildBase build = new BuildBase();
		build.setDefaultGoal("test");
		build.setFinalName("service");
		Modules modules = new Modules();
		modules.getModule().add("phresco-pom");
		processor.addProfile("phresco", build, modules);
		processor.save();
		String finalName = processor.getProfile("phresco").getBuild().getFinalName();
		Assert.assertEquals(finalName, "service");
	}

	@After
	public void delete() {
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}
}
