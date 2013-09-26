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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.phresco.pom.android.AndroidProfile;
import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.model.BuildBase;
import com.phresco.pom.model.BuildBase.Plugins;
import com.phresco.pom.model.Model.Profiles;
import com.phresco.pom.model.Plugin;
import com.phresco.pom.model.Plugin.Executions;
import com.phresco.pom.model.PluginExecution;
import com.phresco.pom.model.PluginExecution.Configuration;
import com.phresco.pom.model.Profile;
import com.phresco.pom.model.Profile.Modules;
import com.phresco.pom.util.AndroidPomProcessor;
import com.phresco.pom.util.PomConstants;
import com.phresco.pom.util.PomProcessor;

import org.mockito.Mockito;
import org.mockito.Mockito.*;
public class AndroidPomProcessorTest {

	private File file;
	
	@Before
	public void prepare() {
	   file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}
	
	@Test
	public void addAndroidProfileTest() throws PhrescoPomException {
			AndroidPomProcessor processor = new AndroidPomProcessor(file);
			BuildBase build = new BuildBase();
			build.setDefaultGoal("install");
			build.setFinalName("service");
			Modules modules = new Modules();
			modules.getModule().add("phresco-pom");
			processor.addProfile("phresco", build, modules);
			Profile profile = processor.getProfile("phresco");
			if(profile.getBuild().getDefaultGoal().equals("install")) {
				Assert.assertTrue(true);
			} else {
				Assert.fail();
			}
	}
	
	@Test
	public void setProfileTest() throws PhrescoPomException, ParserConfigurationException {
			AndroidPomProcessor processor = new AndroidPomProcessor(file);
			Plugin plugin=new Plugin();
			AndroidProfile androidProfile=new AndroidProfile();
			androidProfile.setKeystore("test");
			androidProfile.setKeypass("test");
			androidProfile.setStorepass("test");
			PluginExecution execution=new PluginExecution();
			com.phresco.pom.model.PluginExecution.Configuration conf=new com.phresco.pom.model.PluginExecution.Configuration(); 
			execution.setConfiguration(conf);
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element element = doc.createElement("test");
			Element element1 = doc.createElement("test1");
			List<Element> additionalConfig=new ArrayList<Element>();
			additionalConfig.add(element1);
			processor.setProfile("test_id", "test_goal", plugin, androidProfile, execution, element, additionalConfig);
			Assert.assertEquals("test_id",processor.getModel().getProfiles().getProfile().get(0).getId());
	}
	

	
	
	@Test
	public void setProfileNullTest() throws PhrescoPomException, ParserConfigurationException {
			AndroidPomProcessor processor = new AndroidPomProcessor(file);
			Plugin plugin=new Plugin();
			AndroidProfile androidProfile=new AndroidProfile();
			androidProfile.setKeystore("test");
			androidProfile.setKeypass("test");
			androidProfile.setStorepass("test");
			PluginExecution execution=new PluginExecution();
			com.phresco.pom.model.PluginExecution.Configuration conf=new com.phresco.pom.model.PluginExecution.Configuration(); 
			execution.setConfiguration(conf);
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element element = doc.createElement("test");
			Element element1 = doc.createElement("test1");
			List<Element> additionalConfig=new ArrayList<Element>();
			additionalConfig.add(element1);
			processor.setProfile("test_id", null, plugin, androidProfile, execution, null, additionalConfig);
			Assert.assertEquals("test_id",processor.getModel().getProfiles().getProfile().get(0).getId());

			
	}
	
	@Test(expected=PhrescoPomException.class)
	public void setProfileNull1Test() throws PhrescoPomException, ParserConfigurationException {
			AndroidPomProcessor processor = new AndroidPomProcessor(file);
			Plugin plugin=new Plugin();
			AndroidProfile androidProfile=new AndroidProfile();
			androidProfile.setKeystore("test");
			androidProfile.setKeypass("test");
			androidProfile.setStorepass(null);
			PluginExecution execution=new PluginExecution();
			com.phresco.pom.model.PluginExecution.Configuration conf=new com.phresco.pom.model.PluginExecution.Configuration(); 
			execution.setConfiguration(conf);
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element element = doc.createElement("test");
			Element element1 = doc.createElement("test1");
			List<Element> additionalConfig=new ArrayList<Element>();
			additionalConfig.add(element1);
			processor.setProfile("test_id", null, plugin, androidProfile, execution, null, additionalConfig);
			Assert.assertEquals("test_id",processor.getModel().getProfiles().getProfile().get(0).getId());

	}
	

	@Test(expected=PhrescoPomException.class)
	public void setProfileFullNullTest() throws PhrescoPomException, ParserConfigurationException {
			AndroidPomProcessor processor = new AndroidPomProcessor(file);
			Plugin plugin=new Plugin();
			AndroidProfile androidProfile=new AndroidProfile();
			androidProfile.setKeystore("test");
			androidProfile.setKeypass(null);
			androidProfile.setStorepass(null);
			PluginExecution execution=new PluginExecution();
			com.phresco.pom.model.PluginExecution.Configuration conf=new com.phresco.pom.model.PluginExecution.Configuration(); 
			execution.setConfiguration(conf);
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element element = doc.createElement("test");
			Element element1 = doc.createElement("test1");
			List<Element> additionalConfig=new ArrayList<Element>();
			additionalConfig.add(element1);
			processor.setProfile("test_id", "test", plugin, androidProfile, execution, null, additionalConfig);
	}
	
	
	
	@Test
	public void addProfilePluginTest() throws PhrescoPomException, ParserConfigurationException, JAXBException {
		AndroidPomProcessor processor = new AndroidPomProcessor(file);
		Profiles profiles = new Profiles();
		Profile profile =new Profile();
		profile.setId("test_plugin");
		processor.getModel().setProfiles(profiles);
		processor.getModel().getProfiles().getProfile().add(profile);
		BuildBase build=new BuildBase();
		build.setFinalName("test_finalname");
		com.phresco.pom.model.BuildBase.Plugins plugins = new Plugins();
		build.setPlugins(plugins);
		Plugin plugin = new Plugin();
		plugin.setArtifactId("phresco");
		plugin.setGroupId("photon");
		build.getPlugins().getPlugin().add(plugin);
		processor.getModel().getProfiles().getProfile().get(0).setBuild(build);
		//build.getPlugins().getPlugin().add(plugin);
		List<Element> additionalConfig = new ArrayList<Element>();
		processor.addProfilePlugin("test_plugin", plugin, additionalConfig);
		processor.save();
		Assert.assertEquals("phresco", processor.getModel().getProfiles().getProfile().get(0).getBuild().getPlugins().getPlugin().get(0).getArtifactId());
	}
	
	
	
	@Test(expected=PhrescoPomException.class)
	public void setProfileTestEx1Test() throws PhrescoPomException, ParserConfigurationException, JAXBException {
		AndroidPomProcessor processor = new AndroidPomProcessor(file);
		Plugin plugin=new Plugin();
		AndroidProfile androidProfile=new AndroidProfile();
		PluginExecution execution=new PluginExecution();
		com.phresco.pom.model.PluginExecution.Configuration conf=new com.phresco.pom.model.PluginExecution.Configuration(); 
		execution.setConfiguration(conf);
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		Element element = doc.createElement("test");
		Element element1 = doc.createElement("test1");
		List<Element> additionalConfig=new ArrayList<Element>();
		additionalConfig.add(element1);
		processor.setProfile("test_id", "test_goal", plugin, androidProfile, execution, element, additionalConfig);
		Assert.assertEquals("test_id", processor.getProfile("test_id").getId());

	}
	@Test
	public void setProfileTestEx2Test() throws PhrescoPomException, ParserConfigurationException, JAXBException {
		AndroidPomProcessor processor = new AndroidPomProcessor(file);
		DocumentBuilderFactory mock_DocumentBuilderFactory = org.mockito.Mockito.spy(DocumentBuilderFactory.newInstance());
		Plugin plugin=new Plugin();
		AndroidProfile androidProfile=new AndroidProfile();
		androidProfile.setKeystore("test");
		androidProfile.setKeypass("test");
		androidProfile.setStorepass("test");
		PluginExecution execution=new PluginExecution();
		com.phresco.pom.model.PluginExecution.Configuration conf=new com.phresco.pom.model.PluginExecution.Configuration(); 
		execution.setConfiguration(conf);
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		Element element = doc.createElement("test");
		Element element1 = doc.createElement("test1");
		List<Element> additionalConfig=new ArrayList<Element>();
		additionalConfig.add(element1);
		Mockito.when(mock_DocumentBuilderFactory.newDocumentBuilder()).thenThrow(ParserConfigurationException.class);
		processor.setProfile("test_id", "test_goal", plugin, androidProfile, execution, element, additionalConfig);
		processor.setProfile("test_id", "test_goal", plugin, androidProfile, execution, element, additionalConfig);
		Assert.assertEquals("test_id", processor.getProfile("test_id").getId());

	}
	
	
	@Test(expected=PhrescoPomException.class)
	public void addProfilePluginExcTest() throws PhrescoPomException, ParserConfigurationException, JAXBException {
		AndroidPomProcessor processor = new AndroidPomProcessor(file);
		Profiles profiles = new Profiles();
		Profile profile =new Profile();
		profile.setId("test_plugin");
		processor.getModel().setProfiles(profiles);
		BuildBase build=new BuildBase();
		build.setFinalName("test_finalname");
		com.phresco.pom.model.BuildBase.Plugins plugins = new Plugins();
		build.setPlugins(plugins);
		Plugin plugin = new Plugin();
		plugin.setArtifactId("phresco");
		plugin.setGroupId("photon");
 		List<Element> additionalConfig = new ArrayList<Element>();
		processor.save();
		processor.addProfilePlugin("test_plugin", plugin, additionalConfig);	
		}
	
	@Test
	public void hasSigningTest() throws PhrescoPomException, ParserConfigurationException, JAXBException {
		AndroidPomProcessor processor = new AndroidPomProcessor(file);
		Profiles profiles = new Profiles();
		Profile profile =new Profile();
		profile.setId("test_plugin");
		processor.getModel().setProfiles(profiles);
		processor.getModel().getProfiles().getProfile().add(profile);
		BuildBase build=new BuildBase();
		Plugins plugins = new Plugins();
		Plugin plugin=new Plugin();
		plugin.setArtifactId("Phresco");
		plugin.setGroupId("photon");
		build.setPlugins(plugins);
		Executions executions =new Executions();
		plugin.setExecutions(executions);
		PluginExecution execution=new PluginExecution();
		plugin.getExecutions().getExecution().add(execution);
		build.getPlugins().getPlugin().add(plugin);
		processor.getModel().getProfiles().getProfile().get(0).setBuild(build);
		Assert.assertEquals(true, processor.hasSigning());
	}
	

	
	@Test
	public void hasSigningNullTest() throws PhrescoPomException, ParserConfigurationException, JAXBException {
		AndroidPomProcessor processor = new AndroidPomProcessor(file);
		Profiles profiles = new Profiles();
		Profile profile =new Profile();
		profile.setId("test_plugin");
		processor.getModel().setProfiles(profiles);
		processor.getModel().getProfiles().getProfile().add(profile);
		BuildBase build=new BuildBase();
		Plugins plugins = new Plugins();
		Plugin plugin=new Plugin();
		plugin.setArtifactId("Phresco");
		plugin.setGroupId("photon");
		build.setPlugins(plugins);
		Executions executions =new Executions();
		plugin.setExecutions(executions);
		PluginExecution execution=new PluginExecution();
		Configuration conf=new Configuration();
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		Element element = document.createElement(PomConstants.KEYSTORE);
		conf.getAny().add(element);
		execution.setConfiguration(conf);
		plugin.getExecutions().getExecution().add(execution);
		build.getPlugins().getPlugin().add(plugin);
		processor.getModel().getProfiles().getProfile().get(0).setBuild(build);
		Assert.assertEquals(false, processor.hasSigning());

	}
	
	/*@Test
	public void hasSigningNull3Test() throws PhrescoPomException, ParserConfigurationException, JAXBException {
		AndroidPomProcessor processor = new AndroidPomProcessor(file);
		processor.hasSigning();
	}*/
	
	@Test
	public void getProfileElementTest() throws PhrescoPomException, ParserConfigurationException, JAXBException {
		AndroidPomProcessor processor = new AndroidPomProcessor(file);
		Profiles profiles = new Profiles();
		Profile profile =new Profile();
		profile.setId("test_plugin");
		processor.getModel().setProfiles(profiles);
		processor.getModel().getProfiles().getProfile().add(profile);
		BuildBase build=new BuildBase();
		Plugins plugins = new Plugins();
		Plugin plugin=new Plugin();
		plugin.setArtifactId("Phresco");
		plugin.setGroupId("photon");
		build.setPlugins(plugins);
		Executions executions =new Executions();
		plugin.setExecutions(executions);
		PluginExecution execution=new PluginExecution();
		Configuration conf=new Configuration();
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		Element element = document.createElement(PomConstants.KEYSTORE);
		element.setTextContent("test");
		conf.getAny().add(element);
		Element element1 = document.createElement(PomConstants.STOREPASS);
		element1.setTextContent("test");
		conf.getAny().add(element1);
		Element element2 = document.createElement(PomConstants.KEYPASS);
		element2.setTextContent("test");
		conf.getAny().add(element2);
		Element element3 = document.createElement(PomConstants.ALIAS);
		element3.setTextContent("test");
		conf.getAny().add(element3);
		execution.setConfiguration(conf);
		execution.setConfiguration(conf);
		plugin.getExecutions().getExecution().add(execution);
		build.getPlugins().getPlugin().add(plugin);
		processor.getModel().getProfiles().getProfile().get(0).setBuild(build);
		List<Element> additionalConfig = new ArrayList<Element>();
		processor.save();
		String expected=processor.getProfileElement("test_plugin").getKeypass();
		Assert.assertEquals("test", expected);
	}
	
	@Test
	public void getProfileElementTest1() throws PhrescoPomException, ParserConfigurationException, JAXBException {
		AndroidPomProcessor processor = new AndroidPomProcessor(file);
		Profiles profiles = new Profiles();
		Profile profile =new Profile();
		profile.setId("test_plugin");
		processor.getModel().setProfiles(profiles);
		processor.getModel().getProfiles().getProfile().add(profile);
		BuildBase build=new BuildBase();
		Plugins plugins = new Plugins();
		Plugin plugin=new Plugin();
		plugin.setArtifactId("Phresco");
		plugin.setGroupId("photon");
		build.setPlugins(plugins);
		Executions executions =new Executions();
		plugin.setExecutions(executions);
		PluginExecution execution=new PluginExecution();
		Configuration conf=new Configuration();
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		Element element = document.createElement(PomConstants.KEYSTORE);
		element.setTextContent("test");
		conf.getAny().add(element);
		Element element1 = document.createElement(PomConstants.STOREPASS);
		element1.setTextContent("test");
		conf.getAny().add(element1);
		Element element2 = document.createElement(PomConstants.KEYPASS);
		element2.setTextContent("test");
		conf.getAny().add(element2);
		Element element3 = document.createElement(PomConstants.ALIAS);
		element3.setTextContent("test");
		conf.getAny().add(element3);
		execution.setConfiguration(conf);
		execution.setConfiguration(conf);
		//plugin.getExecutions().getExecution().add(execution);
		build.getPlugins().getPlugin().add(plugin);
		processor.getModel().getProfiles().getProfile().get(0).setBuild(build);
		List<Element> additionalConfig = new ArrayList<Element>();
		processor.save();
		Assert.assertEquals("release", processor.getProfileElement("test_plugin").getProfileName());
	}
	
	@Test
	public void getProfileElementBranchTest() throws PhrescoPomException, ParserConfigurationException, JAXBException {
		AndroidPomProcessor processor = new AndroidPomProcessor(file);
		Profiles profiles = new Profiles();
		Profile profile =new Profile();
		profile.setId("test_plugin");
		BuildBase build=new BuildBase();
		Plugins plugins = new Plugins();
		Plugin plugin=new Plugin();
		plugin.setArtifactId("Phresco");
		plugin.setGroupId("photon");
		Executions executions=new Executions();
		plugin.setExecutions(executions);
		plugins.getPlugin().add(plugin);
		build.setPlugins(plugins);
		profile.setBuild(build);
		profiles.getProfile().add(profile);
		processor.getModel().setProfiles(profiles);
		List<Element> additionalConfig = new ArrayList<Element>();
		processor.save();
		Assert.assertEquals("release", processor.getProfileElement("test_plugin").getProfileName());

	}
	
	@Test
	public void getSigningProfileTest() throws PhrescoPomException, ParserConfigurationException, JAXBException {
		AndroidPomProcessor processor = new AndroidPomProcessor(file);
		Profiles profiles = new Profiles();
		Profile profile =new Profile();
		profile.setId("test_plugin");
		processor.getModel().setProfiles(profiles);
		processor.getModel().getProfiles().getProfile().add(profile);
		BuildBase build=new BuildBase();
		Plugins plugins = new Plugins();
		Plugin plugin=new Plugin();
		plugin.setArtifactId("Phresco");
		plugin.setGroupId("photon");
		build.setPlugins(plugins);
		Executions executions =new Executions();
		plugin.setExecutions(executions);
		PluginExecution execution=new PluginExecution();
		Configuration conf=new Configuration();
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		Element element = document.createElement("keystore");
		conf.getAny().add(element);
		execution.setConfiguration(conf);
		plugin.getExecutions().getExecution().add(execution);
		build.getPlugins().getPlugin().add(plugin);
		processor.getModel().getProfiles().getProfile().get(0).setBuild(build);
		String expected=processor.getSigningProfile();
		Assert.assertEquals("test_plugin",expected);

	}
	
	@Test
	public void getSigningProfileNullTest() throws PhrescoPomException, ParserConfigurationException, JAXBException {
		AndroidPomProcessor processor = new AndroidPomProcessor(file);
		Profiles profiles = new Profiles();
		Profile profile =new Profile();
		profile.setId("test_plugin");
		processor.getModel().setProfiles(profiles);
		processor.getModel().getProfiles().getProfile().add(profile);
		BuildBase build=new BuildBase();
		Plugins plugins = new Plugins();
		Plugin plugin=new Plugin();
		plugin.setArtifactId("Phresco");
		plugin.setGroupId("photon");
		build.setPlugins(plugins);
		Executions executions =new Executions();
		plugin.setExecutions(executions);
		PluginExecution execution=new PluginExecution();
		Configuration conf=new Configuration();
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		Element element = document.createElement("test");
		conf.getAny().add(element);
		execution.setConfiguration(conf);
		plugin.getExecutions().getExecution().add(execution);
		build.getPlugins().getPlugin().add(plugin);
		processor.getModel().getProfiles().getProfile().get(0).setBuild(build);
		String expected=processor.getSigningProfile();
		Assert.assertEquals("", expected);
	}
	
	@Test
	public void removeProfileBranch1() throws PhrescoPomException, ParserConfigurationException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		AndroidPomProcessor processor1 = new AndroidPomProcessor(file);
		Profiles profiles=new Profiles();
		processor1.getModel().setProfiles(profiles);
		Class[] cArg = new Class[1];
        cArg[0] = String.class;

		Method privateStringMethod = AndroidPomProcessor.class.getDeclaredMethod("removeProfile",cArg);
		privateStringMethod.setAccessible(true);
		privateStringMethod.invoke(processor1,"test");
		    
	}
	
	

	@Test
	public void removeProfileBranch2() throws PhrescoPomException, ParserConfigurationException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		AndroidPomProcessor processor1 = new AndroidPomProcessor(file);
		Profiles profiles=new Profiles();
		Profile profile=new Profile();
		profile.setId("photon");
		profiles.getProfile().add(profile);
		processor1.getModel().setProfiles(profiles);
		Class[] cArg = new Class[1];
        cArg[0] = String.class;

		Method privateStringMethod = AndroidPomProcessor.class.getDeclaredMethod("removeProfile",cArg);
		privateStringMethod.setAccessible(true);
		privateStringMethod.invoke(processor1,"test");
		    
	}
	
	@Test
	public void processProfilesBranch() throws PhrescoPomException, ParserConfigurationException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		AndroidPomProcessor processor1 = new AndroidPomProcessor(file);
		AndroidProfile profile=new AndroidProfile();
		List<Element> elements=new ArrayList<Element>();
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		Element element = doc.createElement("test");
		Class[] cArg = new Class[2];
        cArg[0] = AndroidProfile.class;
        cArg[1]= List.class;
        elements.add(element);
		Method privateStringMethod = AndroidPomProcessor.class.getDeclaredMethod("processProfiles",cArg);
		privateStringMethod.setAccessible(true);
		privateStringMethod.invoke(processor1,profile,elements);
		    
	}
	
	@After
	public void delete() {
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}
}
