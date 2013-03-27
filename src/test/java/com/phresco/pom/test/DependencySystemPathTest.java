/**
 * Phresco Pom
 *
 * Copyright (C) 1999-2013 Photon Infotech Inc.
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
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.model.Dependency;
import com.phresco.pom.util.PomProcessor;

public class DependencySystemPathTest {
	
	@Before
	public void prepare() {
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}
	
	@Test
	public void validSetDependencySystemPath() throws PhrescoPomException {
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
	}
	
	@Test
	public void invalidSetDependencySystemPath() throws PhrescoPomException {
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
	}
	
	@After
	public void delete(){
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}

}
