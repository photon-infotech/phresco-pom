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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.model.Build;
import com.phresco.pom.model.Build.Extensions;
import com.phresco.pom.model.Build.Plugins;
import com.phresco.pom.model.Plugin;
import com.phresco.pom.model.PluginExecution;
import com.phresco.pom.model.Plugin.Executions;
import com.phresco.pom.model.PluginExecution.Configuration;
import com.phresco.pom.model.PluginExecution.Goals;
import com.phresco.pom.util.PomProcessor;

public class AddExecutionConfigurationTest {
	
	@Before
	public void prepare() throws IOException{
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}
	
	@Test
	public void addExecutionConfigurationTest() throws PhrescoPomException, ParserConfigurationException {
			String id = "";
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			Element element = document.createElement("test");
			element.setTextContent("test");
			List<Element> configList = new ArrayList<Element>();
			configList.add(element);
			processor.addExecutionConfiguration("com.photon.phresco.plugin", "phresco-plugin", "test", "package", "clean", configList, document);
			processor.save();
			Plugin plugin = processor.getPlugin("com.photon.phresco.plugin", "phresco-plugin");
			List<PluginExecution> execution = plugin.getExecutions().getExecution();
			for (PluginExecution pluginExecution : execution) {
					id = pluginExecution.getId();
			}
			String actual = id;
			String expected = "test";
			Assert.assertEquals(actual,expected);
	}
	
	@Test
	public void addExecutionConfiguration1Test() throws PhrescoPomException, ParserConfigurationException { //to test rivate static void addArtifactItem()
			String id = "";
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			Element element = document.createElement("test");
			element.setTextContent("test");
			List<Element> configList = new ArrayList<Element>();
			configList.add(element);
			Configuration config=new Configuration();
			processor.addExecutionConfiguration("com.photon.phresco.plugin", "phresco-plugin", "test", "package", "clean", configList, document);
			processor.addExecutionConfiguration("com.photon.phresco.plugin", "phresco-plugin", "test", "package", "clean", configList, document);
			processor.save();
			Plugin plugin = processor.getPlugin("com.photon.phresco.plugin", "phresco-plugin");
			List<PluginExecution> execution = plugin.getExecutions().getExecution();
			for (PluginExecution pluginExecution : execution) {
					id = pluginExecution.getId();
			}
			String actual = id;
			String expected = "test";
			Assert.assertEquals(actual,expected);
	}

	@Test //to check is isGoalFound()
	public void isGoalFound1() throws PhrescoPomException, ParserConfigurationException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		PomProcessor processor1 = new PomProcessor(new File("pomTest1.xml"));
		PluginExecution pluginExecution=new PluginExecution();
		String name ="test";
		Class[] cArg = new Class[2];
        cArg[0] = PluginExecution.class;
        cArg[1] = String.class;
		Method privateStringMethod = PomProcessor.class.getDeclaredMethod("isGoalFound",cArg);
		privateStringMethod.setAccessible(true);
		Boolean bool=(Boolean) privateStringMethod.invoke(null,pluginExecution, name);
		org.junit.Assert.assertEquals(true, bool);
	}
	
	
	@Test //to check is isGoalFound()
	public void isGoalFound2() throws PhrescoPomException, ParserConfigurationException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		PomProcessor processor1 = new PomProcessor(new File("pomTest1.xml"));
		PluginExecution pluginExecution =new PluginExecution();
		String name ="test";
		Class[] cArg = new Class[2];
        cArg[0] = PluginExecution.class;
        cArg[1] = String.class;

		Method privateStringMethod = PomProcessor.class.getDeclaredMethod("isGoalFound",cArg);
		pluginExecution.setGoals(null);
		privateStringMethod.setAccessible(true);
		privateStringMethod.invoke(null,pluginExecution, name);
		Boolean bool=(Boolean) privateStringMethod.invoke(null,pluginExecution, name);
		org.junit.Assert.assertEquals(true, bool);   
	}
	
	@Test //to check is isGoalFound()
	public void isGoalFound3() throws PhrescoPomException, ParserConfigurationException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		PomProcessor processor1 = new PomProcessor(new File("pomTest1.xml"));
		PluginExecution pluginExecution =new PluginExecution();
		String name ="test";
		Class[] cArg = new Class[2];
        cArg[0] = PluginExecution.class;
        cArg[1] = String.class;

		Method privateStringMethod = PomProcessor.class.getDeclaredMethod("isGoalFound",cArg);
		Goals goals=new Goals();
		pluginExecution.setGoals(goals);
		privateStringMethod.setAccessible(true);
		privateStringMethod.invoke(null,pluginExecution, name);
		Boolean bool=(Boolean) privateStringMethod.invoke(null,pluginExecution, name);
		org.junit.Assert.assertEquals(false, bool);   
	}
	
	
	@Test(expected=PhrescoPomException.class)
	public void addExecutionConfigurationTestExc() throws PhrescoPomException, ParserConfigurationException, IOException {
		PomProcessor mock_processor = org.mockito.Mockito.spy(new PomProcessor(new File("pomTest.xml")));
		String id = "";
		//PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		Element element = document.createElement("test");
		element.setTextContent("test");
		List<Element> configList = new ArrayList<Element>();
		configList.add(element);
		Mockito.when(mock_processor.getPlugin("com.photon.phresco.plugin", "phresco-plugin")).thenThrow(PhrescoPomException.class);
		mock_processor.addExecutionConfiguration("com.photon.phresco.plugin", "phresco-plugin", "test", "package", "clean", configList, document);
		mock_processor.save();
	}
	
	@After
	public void delete(){
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
