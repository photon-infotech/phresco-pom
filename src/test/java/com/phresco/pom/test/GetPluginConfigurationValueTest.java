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
