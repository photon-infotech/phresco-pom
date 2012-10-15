package com.phresco.pom.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
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
import com.phresco.pom.model.Plugin;
import com.phresco.pom.model.PluginExecution;
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
	public void addExecutionConfigurationTest() {
		try {
			String id = "";
			PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			Element element = document.createElement("test");
			element.setTextContent("test");
			List<Element> configList = new ArrayList<Element>();
			configList.add(element);
			processor.addExecutionConfiguration("com.photon.phresco.plugin", "phresco-plugin", "test", "package", "clean", configList, false, document);
			processor.save();
			Plugin plugin = processor.getPlugin("com.photon.phresco.plugin", "phresco-plugin");
			List<PluginExecution> execution = plugin.getExecutions().getExecution();
			for (PluginExecution pluginExecution : execution) {
					id = pluginExecution.getId();
			}
			String actual = id;
			String expected = "test";
			Assert.assertEquals(actual,expected);
		} catch (IOException e) {
			Assert.fail("Get Plugin Failed!");
		} catch (JAXBException e) {
		    Assert.fail("Get Plugin Failed!");
		} catch (PhrescoPomException e) {
			Assert.fail("Get Plugin Failed!");
		} catch (ParserConfigurationException e) {
			Assert.fail();
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
