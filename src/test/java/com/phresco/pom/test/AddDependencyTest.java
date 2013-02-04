/*
 * ###
 * phresco-pom
 * 
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
package com.phresco.pom.test;

import java.io.File;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.model.Dependency;
import com.phresco.pom.util.PomProcessor;

public class AddDependencyTest {

	@Before
	public void prepare() throws PhrescoPomException {
		
			File file = new File("pomTest.xml");
			if(file.exists()) {
				file.delete();
			}
			PomProcessor processor = new PomProcessor(file);
			Dependency dependency = new Dependency();
			dependency.setGroupId("com.suresh.marimuthu");
			dependency.setArtifactId("artifact");
			dependency.setVersion("2.3");
			dependency.setType("type");
			processor.addDependency(dependency);
			processor.addDependency("com.suresh.marimuthu1", "artifact1" ,"2.3");
			processor.addDependency("com.suresh.marimuthu2", "artifact2" ,"2.3");
			processor.save();
	}
	
	@Test
	public void validAddDependency() throws PhrescoPomException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		processor.addDependency("com.photon.phresco", "artifact","4.1.1");
		processor.save();
		Assert.assertEquals(4, processor.getModel().getDependencies().getDependency().size());
	}
	@After
	public void delete(){
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}
}
