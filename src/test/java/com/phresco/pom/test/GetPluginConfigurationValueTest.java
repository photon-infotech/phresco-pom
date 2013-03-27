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
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.util.PomProcessor;

public class GetPluginConfigurationValueTest {

	@Before
	public void prepare() {
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}
	
	@Test
	public void getPluginConfigurationValueTest() throws PhrescoPomException, ParserConfigurationException {
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			processor.addPlugin("com.photon.phresco.plugin", "plugin-artifactID", "2.0");
			processor.save();
			DocumentBuilderFactory builder = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = builder.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			Element element = document.createElement("phresco");
			element.setTextContent("phresco");
			List<Element> configList = new ArrayList<Element>();
			configList.add(element);
			element = document.createElement("photon");
			element.setTextContent("photon");
			configList.add(element);
			processor.addConfiguration("com.photon.phresco.plugin", "plugin-artifactID", configList);
			processor.save();
			Element value = processor.getPluginConfigurationValue("com.photon.phresco.plugin", "plugin-artifactID", "phresco");
			String actual = value.getTextContent();
			String expected = "phresco";
			Assert.assertEquals(actual,expected);
	}
	
	@After
	public void delete(){
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}
}
