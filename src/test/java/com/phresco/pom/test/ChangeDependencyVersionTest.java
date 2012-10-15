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
import java.io.IOException;

import javax.xml.bind.JAXBException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;

public class ChangeDependencyVersionTest {

	@Before
	public void prepare() throws PhrescoPomException {
		AddDependencyTest addTest = new AddDependencyTest();
		addTest.prepare();
	}

	@Test
	public void validChangeDependencyVersion() {
		try {
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			processor.changeDependencyVersion("com.suresh.marimuthu", "artifact","2.2.2");
			processor.save();
			String actual = processor.getModel().getDependencies().getDependency().get(0).getVersion();
			String expected = "2.2.2";
			Assert.assertEquals(expected, actual);

		} catch (JAXBException e) {
			Assert.fail("Change Version Failed!");
		} catch (IOException e) {
			Assert.fail("Change Version Failed!");
		} catch (PhrescoPomException e) {
			Assert.fail("Change Version Failed!");
		}
	}

	@Test
	public void invalidChangeDependencyVersion() {
		try {
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			processor.changeDependencyVersion("com.photon.invalid", "artifact","2.2");
			processor.save();
			Assert.assertTrue("Invalid Dependency values", true);
		} catch (JAXBException e) {
			Assert.fail("Change Version Failed!");
		} catch (IOException e) {
			Assert.fail("Change Version Failed!");
		} catch (PhrescoPomException e) {
			
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
