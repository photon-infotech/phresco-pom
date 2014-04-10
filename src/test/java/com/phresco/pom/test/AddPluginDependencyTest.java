/**
 * Phresco Pom
 *
 * Copyright (C) 1999-2014 Photon Infotech Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.phresco.pom.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
	public void prepare() throws PhrescoPomException {
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			processor.addPlugin("com.photon.phresco.plugin", "phresco-mavn-plugin", "2.0");
			processor.save();
		
	}
	
	@Test
	public void validAddPluginDependency() throws PhrescoPomException {
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
	}
	
	@Test
	public void validAddPluginDependencyNull() throws PhrescoPomException {
		String artifactId = "";
		PomProcessor processor = new PomProcessor(new File("pomTest1.xml"));
		processor.addPlugin("com.photon.phresco.plugin", "phresco-mavn-plugin", "2.0");
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
		Assert.assertEquals("phresco",actual);
	}
	@After
	public void delete() throws IOException{
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
		File file1 = new File("pomTest1.xml");
		if(file1.exists()) {
			file1.delete();
		}
	}

}
