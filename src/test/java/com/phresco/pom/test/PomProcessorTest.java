package com.phresco.pom.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.model.Build;
import com.phresco.pom.model.Build.Plugins;
import com.phresco.pom.model.Dependency;
import com.phresco.pom.model.Model;
import com.phresco.pom.model.Model.Modules;
import com.phresco.pom.model.Model.Profiles;
import com.phresco.pom.model.Model.Properties;
import com.phresco.pom.model.Model.Repositories;
import com.phresco.pom.model.Parent;
import com.phresco.pom.model.Plugin;
import com.phresco.pom.model.Plugin.Configuration;
import com.phresco.pom.model.Plugin.Dependencies;
import com.phresco.pom.model.Plugin.Executions;
import com.phresco.pom.model.PluginExecution;
import com.phresco.pom.model.PluginManagement;
import com.phresco.pom.model.Profile;
import com.phresco.pom.model.ReportPlugin;
import com.phresco.pom.model.Reporting;
import com.phresco.pom.model.Scm;
import com.phresco.pom.util.PomConstants;
import com.phresco.pom.util.PomProcessor;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.Module;
public class PomProcessorTest {

	@Before
	public void setUp() throws PhrescoPomException{
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
		PomProcessor processor = new PomProcessor(file);
		processor.setGroupId("com.photon.phresco");
		processor.setArtifactId("test");
		processor.setVersion("1.1");
		processor.addDependency("com.suresh.marimuthu", "artifact" ,"2.3","test","jar","${basedir}/src/main");
		processor.save();
	}

	@Test(expected=PhrescoPomException.class)
	public void PomProcessorExc() throws PhrescoPomException, IOException{
		File mock = org.mockito.Mockito.mock(File.class);
		Mockito.when(mock.createNewFile()).thenThrow(IOException.class);
		PomProcessor processor = new PomProcessor(mock);

	}



	@Test
	public void PomProcessor() throws PhrescoPomException, IOException, JAXBException{
		File file = new File("pomTest.xml");
		InputStream in=new FileInputStream(file);
		PomProcessor processor = new PomProcessor(in);
		Assert.assertEquals("com.photon.phresco", processor.getGroupId());


	}


	@Test
	public void isDependencyAvailable() throws PhrescoPomException{
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		Boolean isDepFlag=processor.isDependencyAvailable("com.suresh.marimuthu", "artifact" ,"jar");
		Boolean expected=true;
		Assert.assertEquals(expected,isDepFlag);

	}

	@Test
	public void getDependency() throws PhrescoPomException{
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		Dependency dep=processor.getDependency("com.suresh.marimuthu", "artifact");
		Assert.assertEquals("artifact",dep.getArtifactId());

	}

	@Test
	public void getDependencyNull() throws PhrescoPomException{
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		Dependency dep=processor.getDependency(null,null);
		Assert.assertNull(dep);
	}
	@Test
	public void getDependencyNull1() throws PhrescoPomException{
		File file=new File("pomTest12.xml");
		PomProcessor processor = new PomProcessor(file);
		Dependency dep=processor.getDependency(null,null);
		file.delete();
		Assert.assertNull(dep);

	}

	@Test
	public void deletePluginDependency() throws PhrescoPomException{
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		processor.addPlugin("phresco", "photon","2.2");
		processor.addPlugin("Suresh", "marimuthu","1.1");
		processor.save();
		Boolean delDepFlag=processor.deletePluginDependency("phresco", "photon");
		Boolean expected=true;
		Assert.assertEquals(expected,delDepFlag);

	}
	@Test
	public void deletePluginDependencyNull() throws PhrescoPomException{
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		Build build=new Build();
		processor.getModel().setBuild(build);
		Boolean delDepFlag=processor.deletePluginDependency("phresco", "photon");
		processor.save();
		Boolean expected=false;
		Assert.assertEquals(expected,delDepFlag);

	}
	@Test
	public void deletePluginDependencyNull1() throws PhrescoPomException{
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		Build build=new Build();
		Plugins plugins=new Plugins();
		Plugin plugin=new Plugin();
		plugin.setArtifactId("photon");
		plugin.setGroupId("phresco");
		Dependencies depedencies=new Dependencies();
		plugin.setDependencies(depedencies);
		plugins.getPlugin().add(plugin);
		build.setPlugins(plugins);
		processor.getModel().setBuild(build);
		Boolean delDepFlag=processor.deletePluginDependency("phresco", "photon");
		processor.save();
		Boolean expected=true;
		Assert.assertEquals(expected,delDepFlag);

	}

	@Test
	public void deleteAllDependencies() throws PhrescoPomException{
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		processor.getModel().setPackaging("jar");
		processor.deleteAllDependencies("com.suresh.marimuthu", "artifact","jar");
		Assert.assertNull(processor.getDependency("com.suresh.marimuthu", "artifact")); 

	}


	@Test
	public void setModel() throws PhrescoPomException{
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		processor.setModel("com.photon.phresco", "test", "1.1", "model1", "jar", "test");
		Assert.assertEquals("test",processor.getModel().getArtifactId()); 

	}

	@Test
	public void setModelVersion() throws PhrescoPomException{
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		processor.setModelVersion("1.3");
		Assert.assertEquals("1.3",processor.getModel().getVersion()); 

	}

	@Test
	public void addConfiguration() throws PhrescoPomException{
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		List<Element> configList=new ArrayList<Element>();
		Element element = null;
		configList.add(element);
		Plugin plugin=processor.addPlugin("com.photon.phresco", "test","1.1");
		Configuration conf=processor.addConfiguration("com.photon.phresco", "test", configList);
		processor.save();
		Assert.assertEquals(conf,plugin.getConfiguration()); 

	}

	@Test
	public void addConfiguration1() throws PhrescoPomException{
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		List<Element> configList=new ArrayList<Element>();
		Element element = null;
		configList.add(element);
		Plugin plugin=processor.addPlugin("com.photon.phresco", "test","1.1");
		Configuration conf=processor.addConfiguration("com.photon.phresco", "test", configList,true);
		processor.save();
		Assert.assertEquals(conf,plugin.getConfiguration()); 

	}
	@Test
	public void addConfiguration1Null() throws PhrescoPomException{
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		List<Element> configList=new ArrayList<Element>();
		Element element = null;
		configList.add(element);
		Configuration conf=processor.addConfiguration("com.photon.phresco", "test", configList,true);
		processor.save();

	}



	@Test
	public void deleteConfiguration() throws PhrescoPomException, ParserConfigurationException, IOException{
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		List<Element> configList=new ArrayList<Element>();
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		Element element = document.createElement("test");
		configList.add(element);
		Plugin plugin=processor.addPlugin("com.photon.phresco", "test","1.1");
		processor.addExecutionConfiguration("com.photon.phresco", "test", "testid", "test","generate", configList, document);
		processor.deleteConfiguration("com.photon.phresco", "test","testid","generate");
		processor.save();
		Assert.assertEquals(0, plugin.getExecutions().getExecution().size());

	}

	@Test(expected=PhrescoPomException.class)
	public void deleteConfigurationExc() throws PhrescoPomException, ParserConfigurationException, IOException{
		PomProcessor mock_processor = org.mockito.Mockito.spy(new PomProcessor(new File("pomTest.xml")));
		List<Element> configList=new ArrayList<Element>();
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		Element element = document.createElement("test");
		configList.add(element);
		Plugin plugin=mock_processor.addPlugin("com.photon.phresco", "test","1.1");
		mock_processor.addExecutionConfiguration("com.photon.phresco", "test", "testid", "test","generate", configList, document);
		Mockito.when(mock_processor.getPlugin(Mockito.anyString(), Mockito.anyString())).thenThrow(PhrescoPomException.class);
		mock_processor.deleteConfiguration("com.photon.phresco", "test","testid","generate");
		mock_processor.save();

	}

	@Test
	public void getPluginExecutionConfiguration() throws PhrescoPomException, ParserConfigurationException{
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		List<Element> configList=new ArrayList<Element>();
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		Element element = document.createElement("test");
		configList.add(element);
		Plugin plugin=processor.addPlugin("com.photon.phresco", "test","1.1");
		processor.addExecutionConfiguration("com.photon.phresco", "test", "testid", "test","generate", configList, document);
		processor.save();
		com.phresco.pom.model.PluginExecution.Configuration conf=processor.getPluginExecutionConfiguration("com.photon.phresco", "test");
		Assert.assertNotNull(conf);

	}

	@Test
	public void getPluginExecutionConfigurationNull() throws PhrescoPomException, ParserConfigurationException{
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		List<Element> configList=new ArrayList<Element>();
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		Element element = document.createElement("test");
		configList.add(element);
		Plugin plugin=processor.addPlugin("com.photon.phresco", "test","1.1");
		Build build=new Build();
		Plugins plugins=new Plugins();
		build.setPlugins(plugins);
		plugins.getPlugin().add(plugin);
		processor.getModel().setBuild(build);
		processor.save();
		com.phresco.pom.model.PluginExecution.Configuration conf=processor.getPluginExecutionConfiguration("com.photon.phresco", "test");
		Assert.assertNull(conf);
	}


	@Test
	public void removeProperty() throws PhrescoPomException, ParserConfigurationException{
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		processor.setProperty("Photon", "Phresco");
		processor.save();
		processor.removeProperty("Photon");
		processor.save();
		Assert.assertEquals("", processor.getProperty("Photon"));

	}

	@Test
	public void removePropertyNull() throws PhrescoPomException, ParserConfigurationException{
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		processor.removeProperty("Photon");
		processor.save();
		Assert.assertEquals("", processor.getProperty("Photon"));

	}

	@Test
	public void removePropertyNull1() throws PhrescoPomException, ParserConfigurationException{
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		Properties props=new Properties();
		processor.getModel().setProperties(props);
		processor.removeProperty("Photon");
		processor.save();
		Assert.assertEquals("", processor.getProperty("Photon"));


	}
	@Test
	public void removePropertyNull2() throws PhrescoPomException, ParserConfigurationException{
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		Properties props=new Properties();
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		Element element = document.createElement("Photon11");
		props.getAny().add(element);
		processor.getModel().setProperties(props);
		processor.removeProperty("Photon");
		processor.save();
		Assert.assertEquals("", processor.getProperty("Photon"));

	}

	@Test
	public void getProperty() throws PhrescoPomException, ParserConfigurationException{
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		String str=processor.getProperty("Photon");
		Assert.assertEquals("", str);
	}

	@Test
	public void setProperty() throws PhrescoPomException, ParserConfigurationException{
		PomProcessor processor = new PomProcessor(new File("pomTest11.xml"));
		Properties props=new Properties();
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		Element property = document.createElement("Photon");
		props.getAny().add(property);
		processor.getModel().setProperties(props);
		processor.setProperty("Photon", "Phresco");
		processor.save();
		Assert.assertEquals("Photon", processor.getModel().getProperties().getAny().get(0).getNodeName());

	}

	@Test(expected=PhrescoPomException.class)
	public void setPropertyExc() throws PhrescoPomException, ParserConfigurationException{
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		Properties props=new Properties();
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		Element property = document.createElement("Photon");
		props.getAny().add(property);
		processor.getModel().setProperties(props);
		processor.setProperty("<>", "<>");
		processor.save();
	}


	@Test//(expected=DOMException.class)
	public void setPropertyExc1() throws PhrescoPomException, ParserConfigurationException{
		PomProcessor processor = new PomProcessor(new File("pomTest1.xml"));
		//DocumentBuilderFactory mock_DocumentBuilderFactory = org.mockito.Mockito.spy(DocumentBuilderFactory.newInstance());
		//Mockito.when(mock_DocumentBuilderFactory.newDocumentBuilder()).thenThrow(new ParserConfigurationException("asdad"));
		processor.setProperty("Photon", "Phresco");
		processor.save();

	}

	@Test
	public void addModuleNull() throws PhrescoPomException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		Modules modules=new Modules();
		modules.getModule().add("test");
		processor.getModel().setModules(modules);
		processor.addModule("test");
		processor.save();
		Assert.assertEquals("test", processor.getModel().getModules().getModule().get(0));
	}

	@Test
	public void getPomModule() throws PhrescoPomException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		Modules modules=new Modules();
		modules.getModule().add("test");
		processor.getModel().setModules(modules);
		processor.addModule("test");
		processor.getPomModule("test");
		processor.save();

	}


	@Test
	public void removeModuleNull1() throws PhrescoPomException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		Modules modules=new Modules();
		modules.getModule().add("test");
		processor.getModel().setModules(modules);
		processor.addModule("test");
		processor.removeModule("test");
		processor.save();

	}

	@Test
	public void removeModuleNull2() throws PhrescoPomException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		processor.removeModule("test");
		processor.save();

	}


	@Test
	public void setFinalName() throws PhrescoPomException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		processor.setFinalName("test");
		processor.save();
		Assert.assertEquals("test", processor.getFinalName());

	}
	@Test
	public void getFinalNameNull() throws PhrescoPomException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		String str=processor.getFinalName();
		processor.save();
		Assert.assertEquals(null, str);

	}

	@Test
	public void setName() throws PhrescoPomException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		processor.setName("test_name");
		processor.save();
		Assert.assertEquals("test_name", processor.getName());

	}

	@Test
	public void getNameNull() throws PhrescoPomException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		String name=processor.getName();
		Assert.assertEquals("", name);
	}

	@Test
	public void getGroupIDNull() throws PhrescoPomException {
		PomProcessor processor = new PomProcessor(new File("pomTest1.xml"));
		Parent parent=new Parent();
		parent.setGroupId("test");
		processor.getModel().setParent(parent);
		String name=processor.getGroupId();
		Assert.assertEquals("test", name);
	}

	@Test
	public void setGroupId() throws PhrescoPomException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		processor.setGroupId("test_group");
		processor.save();
		Assert.assertEquals("test_group", processor.getGroupId());

	}

	@Test
	public void setArtifactId() throws PhrescoPomException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		processor.setArtifactId("test_artifact");
		processor.save();
		Assert.assertEquals("test_artifact", processor.getArtifactId());

	}

	@Test
	public void getArtifactIdNull() throws PhrescoPomException {
		PomProcessor processor = new PomProcessor(new File("pomTest1.xml"));
		Parent parent=new Parent();
		parent.setArtifactId("test");
		processor.getModel().setParent(parent);
		String name=processor.getArtifactId();
		Assert.assertEquals("test", name);

	}



	@Test
	public void setVersion() throws PhrescoPomException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		processor.setVersion("1.1");
		processor.save();
		Assert.assertEquals("1.1", processor.getVersion());

	}

	@Test
	public void getVersionNull() throws PhrescoPomException {
		PomProcessor processor = new PomProcessor(new File("pomTest1.xml"));
		Parent parent=new Parent();
		parent.setVersion("2.4");
		processor.getModel().setParent(parent);
		String name=processor.getVersion();
	}


	@Test
	public void setPackaging() throws PhrescoPomException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		processor.setPackaging("jar");
		processor.save();
		Assert.assertEquals("jar", processor.getPackage());

	}

	@Test
	public void getPackageNull() throws PhrescoPomException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		String name=processor.getPackage();
		Assert.assertEquals("jar", name);
	}


	@Test
	public void getParent() throws PhrescoPomException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		Parent parent =new Parent();
		parent.setArtifactId("com.phresco.test");
		processor.getModel().setParent(parent);
		processor.save();
		Parent parent1=processor.getParent();
		Assert.assertEquals("com.phresco.test", processor.getModel().getParent().getArtifactId());

	}

	@Test
	public void getParentNull() throws PhrescoPomException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		Parent name=processor.getParent();
		Assert.assertNull(name);
	}
	@Test
	public void removeAllReportingPlugin() throws PhrescoPomException, JAXBException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		ReportPlugin reportPlugin=new ReportPlugin();
		reportPlugin.setArtifactId("phresco1");
		processor.siteReportConfig(reportPlugin);
		processor.save();
		processor.removeAllReportingPlugin();
		Assert.assertEquals(0, processor.getModel().getReporting().getPlugins().getPlugin().size());

	}
	@Test
	public void removeAllReportingPluginReturn1() throws PhrescoPomException, JAXBException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		Reporting reporting=new Reporting();
		com.phresco.pom.model.Reporting.Plugins plugins=new com.phresco.pom.model.Reporting.Plugins();
		reporting.setPlugins(plugins);
		processor.getModel().setReporting(reporting);
		processor.removeAllReportingPlugin();

	}

	@Test
	public void removeAllReportingPluginReturn2() throws PhrescoPomException, JAXBException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		Reporting reporting=new Reporting();
		processor.getModel().setReporting(reporting);
		processor.removeAllReportingPlugin();

	}
	@Test
	public void removeAllReportingPluginReturn3() throws PhrescoPomException, JAXBException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		processor.removeAllReportingPlugin();

	}

	@Test
	public void getReportPluginNull() throws PhrescoPomException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		List<ReportPlugin> name=processor.getReportPlugin();
		Assert.assertNull(name);
	}

	@Test
	public void getProjectInfoReportCategoriesNull() throws PhrescoPomException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		List<String> name=processor.getProjectInfoReportCategories();
		Assert.assertNull(name);

	}


	@Test
	public void getSitePlugin() throws PhrescoPomException, JAXBException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		processor.addSitePlugin();
		processor.addSitePlugin();
		processor.save();
		Plugin plugin1=processor.getSitePlugin(PomConstants.SITE_PLUGIN_ARTIFACT_ID);
		Assert.assertEquals("maven-site-plugin", plugin1.getArtifactId());

	}

	@Test
	public void addSitePlugineBranch() throws PhrescoPomException, JAXBException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		Build build=new Build();
		PluginManagement pluginMngmt=new PluginManagement();
		com.phresco.pom.model.PluginManagement.Plugins plugins=new com.phresco.pom.model.PluginManagement.Plugins(); 
		pluginMngmt.setPlugins(plugins);
		build.setPluginManagement(pluginMngmt);
		processor.getModel().setBuild(build);
		processor.addSitePlugin();
		processor.save();
		Plugin plugin1=processor.getSitePlugin(PomConstants.SITE_PLUGIN_ARTIFACT_ID);
		Assert.assertEquals("maven-site-plugin", plugin1.getArtifactId());

	}	

	@Test
	public void setSCM() throws PhrescoPomException, JAXBException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		processor.setSCM("test", "test1", "test", "test");
		processor.setSCM("test", "test1", "test", "test");
		processor.save();
		Assert.assertEquals("test", processor.getSCM().getConnection());

	}

	@Test
	public void setSCMBranch() throws PhrescoPomException, JAXBException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		processor.setSCM("", "", "", "");
		processor.save();
		Scm scm=processor.getSCM();
		Assert.assertNull(scm.getConnection());

	}

	@Test
	public void setSCMNull() throws PhrescoPomException, JAXBException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		processor.setSCM(null,null,null,null);
		processor.save();
		Scm scm=processor.getSCM();
		Assert.assertNull(scm.getConnection());
	}

	@Test
	public void getSCMNull() throws PhrescoPomException, JAXBException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		Scm scm=processor.getSCM();
		Assert.assertNull(scm);
	}

	@Test
	public void addRepositories() throws PhrescoPomException, JAXBException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		processor.addRepositories("rackspace-research","http://maven.research.rackspacecloud.com/content/groups/public/");
		processor.save();
		Assert.assertEquals("rackspace-research", processor.getModel().getRepositories().getRepository().get(0).getId());

	}

	@Test
	public void addRepositories1() throws PhrescoPomException, JAXBException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		Repositories repo=new Repositories();
		processor.getModel().setRepositories(repo);
		processor.addRepositories("rackspace-research","http://maven.research.rackspacecloud.com/content/groups/public/");
		processor.save();

	}
	@Test
	public void isPomValid() throws PhrescoPomException, JAXBException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		Boolean bool=processor.isPomValid();
		processor.save();
		org.junit.Assert.assertEquals(true, bool);

	}

	@Test
	public void isPomValidNull() throws PhrescoPomException, JAXBException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		processor.getModel().setArtifactId(null);
		Boolean bool=processor.isPomValid();
		processor.save();
		org.junit.Assert.assertEquals(false, bool);

	}
	@Test
	public void main() throws PhrescoPomException, JAXBException, IOException {
		String[] str = null;
		PomProcessor.main(str);

	}


	@Test
	public void getSourceDirectoryNull() throws PhrescoPomException{
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		Build build=new Build();
		processor.getModel().setBuild(build);
		processor.getSourceDirectory();
	}

	@Test
	public void removeSitePluginNull() throws PhrescoPomException{
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		Build build=new Build();
		processor.getModel().setBuild(build);
		Reporting report=new Reporting();
		com.phresco.pom.model.Reporting.Plugins plugins=new com.phresco.pom.model.Reporting.Plugins();
		ReportPlugin plugin=new ReportPlugin();
		plugin.setArtifactId("test");
		plugin.setGroupId("test");
		plugins.getPlugin().add(plugin);
		report.setPlugins(plugins);
		processor.getModel().setReporting(report);
		processor.removeSitePlugin("test","test");
	}

	@Test
	public void removeSitePluginNull1() throws PhrescoPomException{
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		Build build=new Build();
		processor.getModel().setBuild(build);
		Reporting report=new Reporting();
		com.phresco.pom.model.Reporting.Plugins plugins=new com.phresco.pom.model.Reporting.Plugins();
		ReportPlugin plugin=new ReportPlugin();
		plugin.setArtifactId("test1");
		plugin.setGroupId("test1");
		plugins.getPlugin().add(plugin);
		report.setPlugins(plugins);
		processor.getModel().setReporting(report);
		processor.removeSitePlugin("test","test");
	}
	@Test
	public void removeSitePluginReturn1() throws PhrescoPomException, JAXBException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		Reporting reporting=new Reporting();
		com.phresco.pom.model.Reporting.Plugins plugins=new com.phresco.pom.model.Reporting.Plugins();
		reporting.setPlugins(plugins);
		processor.getModel().setReporting(reporting);
		processor.removeSitePlugin("test","test");

	}

	@Test
	public void removeSitePluginReturn2() throws PhrescoPomException, JAXBException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		Reporting reporting=new Reporting();
		processor.getModel().setReporting(reporting);
		processor.removeSitePlugin("test","test");

	}
	@Test
	public void removeSitePluginReturn3() throws PhrescoPomException, JAXBException {
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		processor.removeSitePlugin("test","test");

	}


	@Test
	public void getProjectInfoReportNull() throws PhrescoPomException, ParserConfigurationException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		PomProcessor processor1 = new PomProcessor(new File("pomTest1.xml"));
		List<ReportPlugin> reportPlugin=new ArrayList<ReportPlugin>();
		Class[] cArg = new Class[1];
		cArg[0] = List.class;

		Method privateStringMethod = PomProcessor.class.getDeclaredMethod("getProjectInfoReport",cArg);
		privateStringMethod.setAccessible(true);
		privateStringMethod.invoke(null,reportPlugin);

	}

	@Test
	public void changeDependencyScopeNull() throws PhrescoPomException, ParserConfigurationException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		PomProcessor processor1 = new PomProcessor(new File("pomTest1.xml"));
		List<ReportPlugin> reportPlugin=new ArrayList<ReportPlugin>();
		Class[] cArg = new Class[3];
		cArg[0] = String.class;
		cArg[1] = String.class;
		cArg[2] = String.class;

		Method privateStringMethod = PomProcessor.class.getDeclaredMethod("changeDependencyScope",cArg);
		privateStringMethod.setAccessible(true);
		privateStringMethod.invoke(processor1,"test","test","test");

	}

	@Test
	public void changeDependencyScope() throws PhrescoPomException, ParserConfigurationException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		PomProcessor processor1 = new PomProcessor(new File("pomTest1.xml"));
		List<ReportPlugin> reportPlugin=new ArrayList<ReportPlugin>();
		Class[] cArg = new Class[3];
		cArg[0] = String.class;
		cArg[1] = String.class;
		cArg[2] = String.class;

		Method privateStringMethod = PomProcessor.class.getDeclaredMethod("changeDependencyScope",cArg);
		privateStringMethod.setAccessible(true);
		com.phresco.pom.model.Model.Dependencies dependencies=new com.phresco.pom.model.Model.Dependencies();
		Dependency depedency=new Dependency();
		depedency.setArtifactId("test");
		depedency.setGroupId("test");
		depedency.setScope("test");
		dependencies.getDependency().add(depedency);
		processor1.getModel().setDependencies(dependencies);
		privateStringMethod.invoke(processor1,"test","test","compile");

	}

	@Test
	public void changeDependencyScope1() throws PhrescoPomException, ParserConfigurationException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		PomProcessor processor1 = new PomProcessor(new File("pomTest1.xml"));
		List<ReportPlugin> reportPlugin=new ArrayList<ReportPlugin>();
		Class[] cArg = new Class[3];
		cArg[0] = String.class;
		cArg[1] = String.class;
		cArg[2] = String.class;

		Method privateStringMethod = PomProcessor.class.getDeclaredMethod("changeDependencyScope",cArg);
		privateStringMethod.setAccessible(true);
		com.phresco.pom.model.Model.Dependencies dependencies=new com.phresco.pom.model.Model.Dependencies();
		Dependency depedency=new Dependency();
		depedency.setArtifactId("test");
		depedency.setGroupId("test");
		//depedency.setScope("test");
		dependencies.getDependency().add(depedency);
		processor1.getModel().setDependencies(dependencies);
		privateStringMethod.invoke(processor1,"test","test","compile");

	}



	@Test
	public void createProfiles() throws PhrescoPomException, ParserConfigurationException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		PomProcessor processor1 = new PomProcessor(new File("pomTest1.xml"));
		List<ReportPlugin> reportPlugin=new ArrayList<ReportPlugin>();
		Class[] cArg = new Class[1];
		cArg[0] = String.class;

		Method privateStringMethod = PomProcessor.class.getDeclaredMethod("createProfiles",cArg);
		privateStringMethod.setAccessible(true);
		Profiles profiles=new Profiles();
		Profile profile=new Profile();
		profile.setId("test");
		profiles.getProfile().add(profile);
		processor1.getModel().setProfiles(profiles);
		privateStringMethod.invoke(processor1,"test");

	}

	@Test
	public void saveExc() throws PhrescoPomException{
		PomProcessor processor = new PomProcessor(new File("pomTest.xml"));
		processor.getModel().setArtifactId("x10FFFF");
		processor.save();
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
		File file2 = new File("pomTest11.xml");
		if(file2.exists()) {
			file2.delete();
		}
	}
}
