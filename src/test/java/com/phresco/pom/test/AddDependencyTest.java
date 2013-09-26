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

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.model.Dependency;
import com.phresco.pom.model.Model;
import com.phresco.pom.model.Model.Dependencies;
import com.phresco.pom.util.PomConstants;
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
			dependency.setScope("compile");
			processor.addDependency(dependency);
			processor.addDependency("com.suresh.marimuthu1", "artifact1" ,"2.3");
			processor.addDependency("com.photon.phresco", "artifact","4.1.1","test","jar","${basedir}/src/main");
			processor.save();
	}
	
	@Test
	public void validAddDependency() throws PhrescoPomException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		processor.addDependency("com.photon.phresco11", "artifact","4.1.1");
		processor.save();
		Assert.assertEquals(4, processor.getModel().getDependencies().getDependency().size());
	}


	@Test
	public void validAddDependency1() throws PhrescoPomException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		processor.addDependency("com.photon.phresco23", "artifact","4.1.1",PomConstants.MVN_SCOPE_SYSTEM);
		processor.save();
		Assert.assertEquals(4, processor.getModel().getDependencies().getDependency().size());
	}
	@Test
	public void validAddDependency2() throws PhrescoPomException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		processor.addDependency("com.photon.phresco1", "artifact","4.1.1","test","jar","${basedir}/src/main");
		processor.save();
		Assert.assertEquals(4, processor.getModel().getDependencies().getDependency().size());
	}
	
	@Test
	public void validAddDependency2Branch() throws PhrescoPomException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		processor.addDependency("com.photon.phresco1", "artifact","4.1.1","test","jar","${basedir}/src/main");
		processor.addDependency("com.photon.phresco1", "artifact","4.1.1","test","jar",null);
		processor.save();
		Assert.assertEquals(4, processor.getModel().getDependencies().getDependency().size());
	}
	@Test
	public void validAddDependency2Branch1() throws PhrescoPomException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		processor.addDependency("com.photon.phresco1", "artifact","4.1.1","compile","jar","${basedir}/src/main");
		processor.save();
		Assert.assertEquals(4, processor.getModel().getDependencies().getDependency().size());
	}
	
	@Test
	public void validAddDependencyAvailable() throws PhrescoPomException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		processor.addDependency("com.photon.phresco", "artifact","4.1.1","test","jar","${basedir}/src/main");
		processor.save();
		Assert.assertEquals(3, processor.getModel().getDependencies().getDependency().size());
	}
	@Test
	public void validAddDependencyAvailable1() throws PhrescoPomException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		Dependency dependency = new Dependency();
		dependency.setGroupId("com.suresh.marimuthu");
		dependency.setArtifactId("artifact");
		dependency.setVersion("2.3");
		dependency.setType("type");
		processor.addDependency(dependency);
		Assert.assertEquals(3, processor.getModel().getDependencies().getDependency().size());
	}
	
	
	@Test
	public void validAddDependencyAvailableExc() throws PhrescoPomException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		Dependency dependency = new Dependency();
		dependency.setGroupId("com.suresh.marimuthu");
		dependency.setArtifactId("artifact");
		dependency.setVersion("2.3");
		dependency.setType("type");
		dependency.setScope("compile");
		processor.addDependency(dependency);
		Assert.assertEquals(3, processor.getModel().getDependencies().getDependency().size());
	
	}
	@Test
	public void validAddDependency3() throws PhrescoPomException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		Dependencies depedencies=new Dependencies();
		Dependency depedency=new Dependency();
		depedency.setArtifactId("artifact");
		depedency.setGroupId("com.photon.phresco11");
		depedencies.getDependency().add(depedency);
		processor.getModel().setDependencies(depedencies);
		processor.addDependency("com.photon.phresco11", "artifact","4.1.1");
		processor.save();
		Assert.assertEquals(1, processor.getModel().getDependencies().getDependency().size());
	}
	@After
	public void delete(){
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}
}
