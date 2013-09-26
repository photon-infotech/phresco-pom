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
import java.io.IOException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.model.Build;
import com.phresco.pom.model.Build.Plugins;
import com.phresco.pom.model.Plugin;
import com.phresco.pom.util.PomProcessor;

public class DeletePluginTest {
	
	@Before
	public void prepare() throws IOException{
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}
	
	@Test
	public void deletePluginTest() throws PhrescoPomException{
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		processor.addPlugin("com.photon.phresco.plugin", "phresco-maven-plugin", "1.2.0.3001");
		processor.save();
		processor.deletePlugin("com.photon.phresco.plugin", "phresco-maven-plugin");
		processor.save();
		Plugin plugin = processor.getPlugin("com.photon.phresco.plugin", "phresco-maven-plugin");
		Plugin actual = plugin;
		Plugin expected = null;
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void deletePluginTestBranch() throws PhrescoPomException{
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		Build build=new Build();
		Plugins plugins=new Plugins();
		Plugin plugin=new Plugin();
		plugins.getPlugin().add(plugin);
		build.setPlugins(plugins);
		processor.getModel().setBuild(build);
		processor.deletePlugin("com.photon.phresco.plugin", "phresco-maven-plugin");
		processor.save();
		Plugin plugin1 = processor.getPlugin("com.photon.phresco.plugin", "phresco-maven-plugin");
		Plugin actual = plugin1;
		Plugin expected = null;
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
