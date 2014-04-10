/**
 * Phresco Pom
 *
 * Copyright (C) 1999-2014 Photon Infotech Inc.
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
package com.phresco.pom.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.Binder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.model.Build;
import com.phresco.pom.model.Build.Plugins;
import com.phresco.pom.model.BuildBase;
import com.phresco.pom.model.Dependency;
import com.phresco.pom.model.Model;
import com.phresco.pom.model.Model.Dependencies;
import com.phresco.pom.model.Model.Modules;
import com.phresco.pom.model.Model.Profiles;
import com.phresco.pom.model.Model.Properties;
import com.phresco.pom.model.Model.Repositories;
import com.phresco.pom.model.Parent;
import com.phresco.pom.model.Plugin;
import com.phresco.pom.model.Plugin.Configuration;
import com.phresco.pom.model.Plugin.Executions;
import com.phresco.pom.model.PluginExecution;
import com.phresco.pom.model.PluginExecution.Goals;
import com.phresco.pom.model.PluginManagement;
import com.phresco.pom.model.Profile;
import com.phresco.pom.model.ReportPlugin;
import com.phresco.pom.model.ReportSet;
import com.phresco.pom.model.Reporting;
import com.phresco.pom.model.Repository;
import com.phresco.pom.model.Scm;
import com.phresco.pom.site.ReportCategories;
import com.phresco.pom.site.Reports;


// TODO: Auto-generated Javadoc
/**
 * 
 * 
 * Example
 *      PomProcessor processor = new PomProcessor(new File("D:\\POM\\pom.xml"));
		processor.addDependency("com.suresh.marimuthu", "artifact" ,"2.3");
		processor.save();
 * 
 * @author suresh_ma
 *
 */

public class PomProcessor {

	/** The model. */
	private Model model;

	/** The file. */
	private File file;
	
	/** Binder. */
	private Binder<Node> binder;
	
	/** Document. */
	private Document document;

	/**
	 * Instantiates a new pom processor.
	 *
	 * @param pomFile the pom file
	 * @throws PhrescoPomException the phresco pom exception
	 */
	@SuppressWarnings("rawtypes")
	public PomProcessor(File pomFile) throws PhrescoPomException {
		try {
			if(pomFile.exists()){
				JAXBContext jaxbContext = JAXBContext.newInstance(Model.class);
		        FileInputStream stream = new FileInputStream(pomFile);
		        DocumentBuilder db = getDocumentBuilder();
		        document = db.parse(stream);
		        
		        binder = jaxbContext.createBinder();
		        model = (Model) binder.unmarshal(document);
			} else {
				pomFile.createNewFile();
				model = new Model();
			}
			file = pomFile;
		} catch (JAXBException e) {
			throw new PhrescoPomException(e);
		} catch (IOException e) {
			throw new PhrescoPomException(e);
		} catch (SAXException e) {
			throw new PhrescoPomException(e);
		} catch (PhrescoPomException e) {
			throw new PhrescoPomException(e);
		}
	}
	
	public DocumentBuilder getDocumentBuilder() throws PhrescoPomException {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(Boolean.TRUE);
			return dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new PhrescoPomException(e);
		}
	}
	
	/**
	 * Instantiates a new pom processor from inputstream.
	 *
	 * @param inputStream the input stream
	 * @throws JAXBException the jAXB exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws PhrescoPomException 
	 */
	@SuppressWarnings("rawtypes")
	public PomProcessor(InputStream inputStream) throws PhrescoPomException {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Model.class);
			DocumentBuilder db = getDocumentBuilder();
			document = db.parse(inputStream);
			binder = jaxbContext.createBinder();
	        model = (Model) binder.unmarshal(document);
		} catch (SAXException e) {
			throw new PhrescoPomException(e);
		} catch (IOException e) {
			throw new PhrescoPomException(e);
		} catch (JAXBException e) {
			throw new PhrescoPomException(e);
		}
	}
	
	/**
	 * Adds dependency.
	 *
	 * @param groupId the group id
	 * @param artifactId the artifact id
	 * @param version the version
	 * @param scope the scope
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public void addDependency(String groupId, String artifactId, String version, String scope) throws PhrescoPomException {
		addDependency(groupId, artifactId, version, scope, null, null);
	} 
	
	/**
	 * Adds the dependency.
	 *
	 * @param groupId the group id
	 * @param artifactId the artifact id
	 * @param version the version
	 * @param scope the scope
	 * @param type the type
	 * @param systemPath the system path
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public void addDependency(String groupId, String artifactId, String version, String scope, String type,String systemPath) throws  PhrescoPomException {
		if(isDependencyAvailable(groupId, artifactId,type)){
            changeDependencyVersion(groupId, artifactId, version);
            changeDependencyScope(groupId, artifactId, scope);
            if(StringUtils.isNotEmpty(systemPath)) {
            	setDependencySystemPath(groupId, artifactId, systemPath);
            }
            return;
        }
        Dependency dependency = new Dependency();
        dependency.setArtifactId(artifactId);
        dependency.setGroupId(groupId);
        dependency.setVersion(version);
        if(StringUtils.isNotEmpty(scope)){
        	if(!scope.equals(PomConstants.DEFAULT_SCOPE)) {
        		dependency.setScope(scope);
        	}
            if(scope.equals(PomConstants.MVN_SCOPE_SYSTEM)) {
            	dependency.setSystemPath(systemPath);
            }
        }
        
        if(StringUtils.isNotBlank(type)){
            dependency.setType(type);
        }
        addDependency(dependency);
    } 
	
	/**
	 * Checks if is dependency available.
	 *
	 * @param groupId the group id
	 * @param artifactId the artifact id
	 * @return true, if is dependency available
	 * @throws PhrescoPomException the phresco pom exception
	 */
	/**
	 * @param groupId
	 * @param artifactId
	 * @return
	 * @throws PhrescoPomException
	 */
	public boolean isDependencyAvailable(String groupId, String artifactId, String type) throws PhrescoPomException{
        if(model.getDependencies()==null) {
                return false;
        }
        boolean check = false;
        List<Dependency> list = model.getDependencies().getDependency();
         
          for(Dependency dependency : list){
                if (StringUtils.isNotEmpty(type)) {
                	if(dependency.getGroupId().equals(groupId) && dependency.getArtifactId().equals(artifactId) &&
                              (dependency.getType() != null && dependency.getType().equals(type))){
                		        return true;
                		       
                      } else {
                        	   check = false;
                      }
                } else if (dependency.getGroupId().equals(groupId) && dependency.getArtifactId().equals(artifactId)) {
                	          return true;
                	          
             }
        }
        return check;
   }

	/**
	 * Adds the dependency.
	 *
	 * @param dependency the dependency
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public void addDependency(Dependency dependency) throws PhrescoPomException {
		String groupId = dependency.getGroupId();
		String artifactId = dependency.getArtifactId();
		String version = dependency.getVersion();
		if(isDependencyAvailable(groupId, artifactId, dependency.getType())){
			changeDependencyVersion(groupId, artifactId, version);
			changeDependencyScope(groupId, artifactId, dependency.getScope());
			return;
		}
		Dependencies dependencies = model.getDependencies();
		if(dependencies == null) {
			dependencies = new Dependencies();
			model.setDependencies(dependencies);
		}
		List<Dependency> dependencyList = dependencies.getDependency();
		dependencyList.add(dependency);
	}

	/**
	 * Change dependency version.
	 *
	 * @param groupId the group id
	 * @param artifactId the artifact id
	 * @param version the version
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public void changeDependencyVersion(String groupId, String artifactId,String version) throws PhrescoPomException {
		if(model.getDependencies()==null){
			return;
		}
		List<Dependency> list = model.getDependencies().getDependency();
		for(Dependency dependency : list){
			if(dependency.getGroupId().equals(groupId) && dependency.getArtifactId().equals(artifactId)){
				dependency.setVersion(version);
			} 
		}
	}
	
	
	private void changeDependencyScope(String groupId, String artifactId,String scope) throws PhrescoPomException {
		if(model.getDependencies()==null){
			return;
		}
		List<Dependency> list = model.getDependencies().getDependency();
		for(Dependency dependency : list){
			if(dependency.getGroupId().equals(groupId) && dependency.getArtifactId().equals(artifactId)){
				 if(StringUtils.isNotEmpty(scope)){
			        if(!scope.equals(PomConstants.DEFAULT_SCOPE)) {
			        	dependency.setScope(scope);
			        }
			        if(StringUtils.isNotEmpty(dependency.getScope())) {
				        if(scope.equals(PomConstants.DEFAULT_SCOPE) && !dependency.getScope().equals(PomConstants.DEFAULT_SCOPE)) {
				        	dependency.setScope(null);
				        }
			        }
				 }
			} 
		}
	}
	
	/**
	 * Sets the dependency system path.
	 *
	 * @param groupId the group id
	 * @param artifactId the artifact id
	 * @param systemPath the system path
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public void setDependencySystemPath(String groupId, String artifactId,String systemPath) throws PhrescoPomException {
		if(model.getDependencies()==null){
			return;
		}
		List<Dependency> list = model.getDependencies().getDependency();
		for(Dependency dependency : list){
			if(dependency.getGroupId().equals(groupId) && dependency.getArtifactId().equals(artifactId)){
				dependency.setSystemPath(systemPath);
			} 
		}
	}
	
	/**
	 * Gets the dependency.
	 *
	 * @param groupId the group id
	 * @param artifactId the artifact id
	 * @return the dependency
	 */
	public Dependency getDependency(String groupId, String artifactId) {
		if(model.getDependencies()!=null) {
			List<Dependency> list = model.getDependencies().getDependency();
			for(Dependency dependency : list) {
				if(dependency.getGroupId().equals(groupId) && dependency.getArtifactId().equals(artifactId)){
					return dependency;
				} 
			}
		} return null;
	}

	/**
	 * Adds the dependency.
	 *
	 * @param groupId the group id
	 * @param artifactId the artifact id
	 * @param version the version
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public void addDependency(String groupId, String artifactId, String version) throws  PhrescoPomException {
		if(isDependencyAvailable(groupId, artifactId, null)){
			changeDependencyVersion(groupId, artifactId, version);
		}
		addDependency(groupId, artifactId, version, "");
	}

	/**
	 * Delete dependency.
	 *
	 * @param groupId the group id
	 * @param artifactId the artifact id
	 * @return the boolean
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public Boolean deleteDependency(String groupId, String artifactId, String packaging) throws PhrescoPomException{
		boolean isFound = false;
		if(model.getDependencies()== null) {
			return isFound;
		}
		List<Dependency> list = model.getDependencies().getDependency();
		for(Dependency dependency : list){
			if(dependency.getGroupId().equals(groupId) && dependency.getArtifactId().equals(artifactId) 
					&& StringUtils.isNotEmpty(dependency.getType()) && dependency.getType().equals(packaging)){
				model.getDependencies().getDependency().remove(dependency);
				isFound = true;
				break;
			} else if (dependency.getGroupId().equals(groupId) && dependency.getArtifactId().equals(artifactId)) {
				model.getDependencies().getDependency().remove(dependency);
				isFound = true;
				break;
			}
		}
		if(model.getDependencies().getDependency().isEmpty()){
			model.setDependencies(null);
		}
		return isFound;
	}
	
	/**
	 * Delete plugin dependency.
	 *
	 * @param groupId the group id
	 * @param artifactId the artifact id
	 * @return the boolean
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public Boolean deletePluginDependency(String groupId, String artifactId) throws PhrescoPomException {
		boolean isFound = false;
		if(model.getBuild().getPlugins() == null) {
			return isFound;
		}
		Plugins plugins = model.getBuild().getPlugins();
		List<Plugin> pluginList = plugins.getPlugin();
		for (Plugin plugin : pluginList) {
			if (plugin.getGroupId().equals(groupId) && plugin.getArtifactId().equals(artifactId)) {
				if(plugin.getDependencies() != null) {
					plugin.setDependencies(null);
				}
				isFound = true;
			} 
		}
		return isFound;
	}

	/**
	 * Delete all dependencies.
	 *
	 * @param groupId the group id
	 * @param artifactId the artifact id
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public void deleteAllDependencies(String groupId,String artifactId, String packaging) throws PhrescoPomException{
		boolean flag = true;
		while(flag){
			flag = deleteDependency(groupId, artifactId, packaging);
		}
	}

	/**
	 * Sets the model.
	 *
	 * @param groupId the group id
	 * @param artifactId the artifact id
	 * @param version the version
	 * @param name the name
	 * @param packaging the packaging
	 * @param description the description
	 */
	public void setModel(String groupId,String artifactId,String version,String name,String packaging,String description){

		model.setGroupId(groupId);
		model.setArtifactId(artifactId);
		model.setVersion(version);
		model.setName(name);
		model.setPackaging(packaging);
		model.setDescription(description);
	}
	
	/**
	 * Sets the model version.
	 *
	 * @param version the new model version
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public void setModelVersion(String version) throws PhrescoPomException{
		model.setVersion(version);
	}

	/**
	 * Gets the model.
	 *
	 * @return Model
	 */
	public Model getModel() {
		return model;
	}

	/**
	 * Removes the all dependencies.
	 *
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public void removeAllDependencies() throws PhrescoPomException {

		if(model.getDependencies() == null){
			return;
		}
		List <Dependency> list = model.getDependencies().getDependency();
		model.getDependencies().getDependency().removeAll(list);
		model.setDependencies(null);
	}

	/**
	 * Adds the plugin.
	 *
	 * @param groupId the group id
	 * @param artifactId the artifact id
	 * @param version the version
	 * @return the plugin
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public Plugin addPlugin(String groupId,String artifactId, String version) throws PhrescoPomException {
		Plugin existingPlugin = getPlugin(groupId, artifactId);
		if(existingPlugin != null) {
			existingPlugin.setVersion(version);
			return existingPlugin;
		}
		Build build = model.getBuild();
		if(build == null){
			build = new Build();
			model.setBuild(build);
		}
		Plugins plugins = build.getPlugins();
		if(plugins == null ) {
			plugins = new Plugins();
			build.setPlugins(plugins);
		}
		Plugin plugin = new Plugin();
		plugin.setArtifactId(artifactId);
		plugin.setGroupId(groupId);
		plugin.setVersion(version);
		plugins.getPlugin().add(plugin);
		return plugin;
	}
	
	/**
	 * Delete plugin.
	 *
	 * @param groupId the group id
	 * @param artifactId the artifact id
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public void deletePlugin(String groupId, String artifactId) throws PhrescoPomException {
		Plugin plugin = getPlugin(groupId, artifactId);
		if(plugin == null) {
			return;
		} 
		model.getBuild().getPlugins().getPlugin().remove(plugin);
		if(model.getBuild().getPlugins().getPlugin().isEmpty()){
			model.getBuild().setPlugins(null);
		}
	}

	/**
	 * Adds the configuration.
	 *
	 * @param pluginGroupId the plugin group id
	 * @param pluginArtifactId the plugin artifact id
	 * @param configList the config list
	 * @return the configuration
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public Configuration addConfiguration(String pluginGroupId,String pluginArtifactId, List<Element> configList) throws PhrescoPomException {
		return addConfiguration(pluginGroupId, pluginArtifactId, configList, false);
	}
	
	
	/**
	 * Adds the configuration.
	 *
	 * @param pluginGroupId the plugin group id
	 * @param pluginArtifactId the plugin artifact id
	 * @param configList the config list
	 * @param overwrite the overwrite
	 * @return the configuration
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public Configuration addConfiguration(String pluginGroupId,String pluginArtifactId, List<Element> configList, boolean overwrite) throws PhrescoPomException {
		Plugin plugin = getPlugin(pluginGroupId, pluginArtifactId);
		if (plugin == null) {
			return null;
		}
		Configuration configuration = plugin.getConfiguration();
		
		if (configuration == null) {
			configuration = new Configuration();
			plugin.setConfiguration(configuration);
		}
		if (overwrite) {
			configuration.getAny().addAll(configList);
		} else {			
			plugin.getConfiguration().getAny().clear();
			configuration.getAny().addAll(configList);
		}
		return configuration;
	}
	
	/**
	 * Adds the execution configuration.
	 *
	 * @param pluginGroupId the plugin group id
	 * @param pluginArtifactId the plugin artifact id
	 * @param executionId the execution id
	 * @param phase the phase
	 * @param goal the goal
	 * @param configList the config list
	 * @param document the document
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public void addExecutionConfiguration(String pluginGroupId, String pluginArtifactId, String executionId,
			String phase, String goal, List<Element> configList, Document document) throws PhrescoPomException {
		boolean addExecution = false;
		try {
			Plugin plugin = getPlugin(pluginGroupId, pluginArtifactId);
			if (plugin == null) {
				plugin = addPlugin(pluginGroupId, pluginArtifactId, "2.3");
			}

			Executions executions = plugin.getExecutions();

			if (executions == null) {
				executions = new Executions();
				plugin.setExecutions(executions);
			}
			PluginExecution execution = getExecution(executions, executionId, goal);
			
			if (execution == null) {
				addExecution = true;
				 execution = new PluginExecution();
				execution.setId(executionId);
				execution.setPhase(phase);
				Goals goals = new Goals();
				goals.getGoal().add(goal);
				execution.setGoals(goals);
			}
			com.phresco.pom.model.PluginExecution.Configuration configuration = new com.phresco.pom.model.PluginExecution.Configuration();
			if (execution.getConfiguration() == null) {
				execution.setConfiguration(configuration);
			}

			List<Element> configElementList = execution.getConfiguration().getAny();
			if (configElementList.isEmpty()) {	
				createArtifactItems(configList, document, plugin, executions,
						execution, configuration, addExecution);
			}
			else {
				addArtifactItem(configList, document, execution, configuration,
						configElementList);
			}
		} catch (PhrescoPomException e) {
			throw new PhrescoPomException(e);
		}
	}

	/**
	 * Adds the artifact item.
	 *
	 * @param configList the config list
	 * @param document the document
	 * @param execution the execution
	 * @param configuration the configuration
	 * @param configElementList the config element list
	 */
	private static void addArtifactItem(List<Element> configList, Document document,
			PluginExecution execution,
			com.phresco.pom.model.PluginExecution.Configuration configuration,
			List<Element> configElementList) {
		Element artifactItems;
		Element artifactItem;
		Element markersDirectory = null;
		for (Element configElement : configElementList) {
			if(configElement.getTagName().equals("markersDirectory")) {
				markersDirectory = configElement;
			}
			if (configElement.getTagName().equals("artifactItems")) {
				artifactItems = configElement;
				artifactItem = document.createElement("artifactItem");
				for (Element configListElement : configList) {
					artifactItem.appendChild(configListElement);
				}
				Element markersDirectoryImported = (Element) document.importNode(markersDirectory, Boolean.TRUE);
				Element imported = (Element) document.importNode(artifactItems, Boolean.TRUE);
				imported.appendChild(artifactItem);
				configuration.getAny().add(markersDirectoryImported);
				configuration.getAny().add(imported);
				execution.setConfiguration(configuration);
			}
		}
	}

	/**
	 * Creates the artifact items.
	 *
	 * @param configList the config list
	 * @param document the document
	 * @param plugin the plugin
	 * @param executions the executions
	 * @param execution the execution
	 * @param configuration the configuration
	 * @param addExecution the add execution
	 */
	private static void createArtifactItems(List<Element> configList, Document document, Plugin plugin, Executions executions,
			PluginExecution execution, com.phresco.pom.model.PluginExecution.Configuration configuration,
			boolean addExecution) {
		Element artifactItems;
		Element artifactItem;
		Element markersDirectory = document.createElement("markersDirectory");
		markersDirectory.setTextContent("do_not_checkin/markers");
		artifactItems = document.createElement("artifactItems");
		artifactItem = document.createElement("artifactItem");
		for (Element configElement : configList) {
			artifactItem.appendChild(configElement);
			artifactItems.appendChild(artifactItem);
		}
		configuration.getAny().add(markersDirectory);
		configuration.getAny().add(artifactItems);
		execution.setConfiguration(configuration);
		if (addExecution) {
			executions.getExecution().add(execution);
			plugin.setExecutions(executions);
		}
	}
	
	/**
	 * Delete configuration.
	 *
	 * @param pluginGroupId the plugin group id
	 * @param pluginArtifactId the plugin artifact id
	 * @param executionId the execution id
	 * @param goal the goal
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public void deleteConfiguration(String pluginGroupId, String pluginArtifactId, String executionId, String goal) throws PhrescoPomException {
		try {
			Plugin plugin = getPlugin(pluginGroupId, pluginArtifactId);
			if (plugin != null) {
				Executions executions = plugin.getExecutions();
				PluginExecution execution = getExecution(executions, executionId, goal);
				executions.getExecution().remove(execution);
			}
		} catch (PhrescoPomException e) {
			throw new PhrescoPomException(e);
		}
	}

	
	/**
	 * Gets the execution.
	 *
	 * @param executions the executions
	 * @param executionId the execution id
	 * @param goal the goal
	 * @return the execution
	 */
	private static PluginExecution getExecution(Executions executions, String executionId, String goal) {
		if (executions.getExecution() != null) {
			for (PluginExecution pluginExecution : executions.getExecution()) {
				if (pluginExecution != null && pluginExecution.getId().equals(executionId) && isGoalFound(pluginExecution, goal)) {
					return pluginExecution;
				}

			}
		}
		return null;
	}
	
	/**
	 * Checks if is goal found.
	 *
	 * @param pluginExecution the plugin execution
	 * @param name the name
	 * @return true, if is goal found
	 */
	private static boolean isGoalFound(PluginExecution pluginExecution, String name) {
		Goals goals = pluginExecution.getGoals();
		if (goals == null) {
			goals = new Goals();
			goals.getGoal().add(name);
			pluginExecution.setGoals(goals);
			return true;
		}
		for (String goal : goals.getGoal()) {
			if (goal != null && goal.equals(name)) {
				return true;
			}
		}
		return false;
	} 

	/**
	 * Gets the plugin configuration Element.
	 *
	 * @param pluginGroupId the plugin group id
	 * @param pluginArtifactId the plugin artifact id
	 * @param tagName the tag name
	 * @return the plugin configuration value
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public Element getPluginConfigurationValue(String pluginGroupId,String pluginArtifactId,String tagName) throws PhrescoPomException{
		Plugin plugin = getPlugin(pluginGroupId, pluginArtifactId);
		Configuration configuration = plugin.getConfiguration();
		if(model.getBuild() != null && model.getBuild().getPlugins() != null && configuration !=null) {
			for (Element config : configuration.getAny()) {
				if(tagName.equals(config.getTagName())){
					return config;
				}	
			}
		}
		return null;
	}
	
	/**
	 * Gets the plugin configuration.
	 *
	 * @param pluginGroupId the plugin group id
	 * @param pluginArtifactId the plugin artifact id
	 * @return the plugin configuration
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public com.phresco.pom.model.PluginExecution.Configuration getPluginExecutionConfiguration(String pluginGroupId,String pluginArtifactId) throws PhrescoPomException{
		Plugin plugin = getPlugin(pluginGroupId, pluginArtifactId);
		if(plugin.getExecutions() != null && plugin.getExecutions().getExecution() != null) {
			List<PluginExecution> execution = plugin.getExecutions().getExecution();
			for (PluginExecution pluginExecution : execution) {
				if(pluginExecution.getConfiguration() != null) {
					return pluginExecution.getConfiguration();
				}
			}
		}		
		return null;
	}
	
	
	/**
	 * @param groupId
	 * @param artifactId
	 * @param executionId
	 * @return
	 * @throws PhrescoPomException
	 */
	public com.phresco.pom.model.PluginExecution.Configuration getPluginExecutionConfiguration(String groupId, String artifactId, String executionId) throws PhrescoPomException {
		Plugin plugin = getPlugin(groupId, artifactId);
		if (plugin != null && plugin.getExecutions() != null && plugin.getExecutions().getExecution() != null) {
			List<PluginExecution> execution = plugin.getExecutions().getExecution();
			for (PluginExecution pluginExecution : execution) {
				if (pluginExecution.getId().equals(executionId)) {
					if(pluginExecution.getConfiguration() != null) {
						return pluginExecution.getConfiguration();
					}
				}
			}
		}
		return null;
	}

	/**
	 * Adds the plugin dependency.
	 *
	 * @param pluginGroupId the plugin group id
	 * @param pluginArtifactId the plugin artifact id
	 * @param dependency the dependency
	 * @return the com.phresco.pom.model. plugin. dependencies
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public com.phresco.pom.model.Plugin.Dependencies addPluginDependency(String pluginGroupId,String pluginArtifactId, Dependency dependency) throws PhrescoPomException{
		Plugin plugin = getPlugin(pluginGroupId, pluginArtifactId);
		com.phresco.pom.model.Plugin.Dependencies dependencies = plugin.getDependencies();
		if(dependencies == null){
			dependencies = new Plugin.Dependencies();
			plugin.setDependencies(dependencies);
		}
		plugin.getDependencies().getDependency().add(dependency);
		
		return dependencies;
	}

	/**
	 * Gets the plugin.
	 *
	 * @param groupId the group id
	 * @param artifactId the artifact id
	 * @return the plugin
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public Plugin getPlugin(String groupId,String artifactId) throws PhrescoPomException{

		if(model.getBuild() != null && model.getBuild().getPlugins() != null) {
			for (Plugin plugin : model.getBuild().getPlugins().getPlugin()) {
				if(groupId.equals(plugin.getGroupId()) && artifactId.equals(plugin.getArtifactId())) {
					return plugin;
				}
			}
		}
		return null;
	}

	/**
	 * Change plugin version.
	 *
	 * @param groupId the group id
	 * @param artifactId the artifact id
	 * @param version the version
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public void changePluginVersion(String groupId,String artifactId,String version) throws PhrescoPomException{
		if(model.getBuild() != null && model.getBuild().getPlugins() != null) {
			for (Plugin plugin : model.getBuild().getPlugins().getPlugin()) {
				if(groupId.equals(plugin.getGroupId()) && artifactId.equals(plugin.getArtifactId())) {
					plugin.setVersion(version);
				}
			}
		}
	}

	/**
	 * Sets the property.
	 *
	 * @param name the name
	 * @param value the value
	 * @throws PhrescoPomException the phresco pom exception
	 */
	
	public void setProperty(String name,String value) throws PhrescoPomException {
		try {
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element element = doc.createElement(name);
			element.setTextContent(value);
			
			if(model.getProperties()==null){
				Properties properties = new Properties();
				model.setProperties(properties);
			}
			for(Element proElement : model.getProperties().getAny()){
				if(proElement.getTagName().equals(name)){
					proElement.setTextContent(value);
					return;
				}
			}
			model.getProperties().getAny().add(element);
		} catch (DOMException e) {
			throw new PhrescoPomException(e);
		} catch (ParserConfigurationException e) {
			throw new PhrescoPomException(e);
		}
	}

	/**
	 * Gets the property.
	 *
	 * @param propertyName the property name
	 * @return the property
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public String getProperty(String propertyName) throws PhrescoPomException {
		if(model.getProperties()==null) {
			return "";
		}
		List<Element> property = model.getProperties().getAny();
		int size = model.getProperties().getAny().size();
		for(int i=0;i<size;i++) { 
			if(propertyName.equals(property.get(i).getTagName())) {
				String textContent = property.get(i).getTextContent();
				if (textContent.contains("${")) {
					textContent = getMavenVariableProp(textContent);
				}
				return textContent;
			}
		}
		return "";
	}
	
	/**
	 * Gets the property.
	 *
	 * @param propertyName the property name
	 * @return the property
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public String getPropertyValue(String propertyName) throws PhrescoPomException {
		if(model.getProperties()==null) {
			return "";
		}
		List<Element> property = model.getProperties().getAny();
		int size = model.getProperties().getAny().size();
		for(int i=0;i<size;i++) { 
			if(propertyName.equals(property.get(i).getTagName())) {
				String textContent = property.get(i).getTextContent();
				return textContent;
			}
		}
		return "";
	}
	
	private String getMavenVariableProp(String prop) throws PhrescoPomException {
		StringBuilder builder = new StringBuilder();
		String replaceString = prop.replace("${", "");
		String replace = replaceString.replace("}", "");
		String[] split = replace.split("/");
		for (String splittedProp : split) {
			if (StringUtils.isNotEmpty(splittedProp)) {
				builder.append(File.separator);
			}
			String propertyValue = getProperty(splittedProp);
			if (StringUtils.isNotEmpty(propertyValue)) {
				builder.append(propertyValue);
			} else {
				builder.append(splittedProp);
			}
		}
		return builder.toString();
	}
	
	/**
	 * Removes the property.
	 *
	 * @param propertyName the property name
	 * @return true, if successful
	 */
	public boolean removeProperty(String propertyName) {
		boolean status = false;
		if(model.getProperties()==null) {
			return status;
		}
		List<Element> property = model.getProperties().getAny();
		for (Element element : property) {
			if(propertyName.equals(element.getTagName())) {
				property.remove(element);
				return true;
			}
		}
		return status;
	}

	/**
	 * Adds the module.
	 *
	 * @param moduleName the module name
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public void addModule(String moduleName) throws PhrescoPomException {
		if(getPomModule(moduleName).equals(moduleName)){
			return;
		}
		Modules modules = new Modules();
		if(model.getModules()==null){
			model.setModules(modules);
		} 
		model.getModules().getModule().add(moduleName);
	}
	
	/**
	 * Gets the pom module.
	 *
	 * @param moduleName the module name
	 * @return the pom module
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public String getPomModule(String moduleName) throws PhrescoPomException{
		if(model.getModules() != null){
			for(String moduleNames : model.getModules().getModule()) {
				if(moduleName.equals(moduleNames)) {
					return moduleNames;
				}
			}
		}
		return "";
	}

	/**
	 * Gets the pom module.
	 *
	 * @return the pom module
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public Modules getPomModule() throws PhrescoPomException {
		return model.getModules();
	}

	/**
	 * Removes the module.
	 *
	 * @param moduleName the module name
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public void removeModule(String moduleName) throws PhrescoPomException {

		if(model.getModules() == null){
			return;
		}
		for(String moduleNames : model.getModules().getModule()) {
			if(moduleName.equals(moduleNames)) {
				model.getModules().getModule().remove(moduleNames);
				List<String> modules = model.getModules().getModule();
				model.setModules(null);
				for (String string : modules) {
					addModule(string);
				}
				break;
			}
		}
	}
	
	/**
	 * Adds the source directory.
	 *
	 * @param sourceDirectoryvalue the source directoryvalue
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public void setSourceDirectory(String sourceDirectoryvalue) throws PhrescoPomException{
		Build build = model.getBuild();
		if(build==null){
			 build = new Build();
			 model.setBuild(build);
		}
		build.setSourceDirectory(sourceDirectoryvalue);
		
	}

	/**
	 * Gets the source directory.
	 *
	 * @return the source directory
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public String getSourceDirectory() throws PhrescoPomException {
		if(model.getBuild().getSourceDirectory()==null){
			return "src";
		}
		String sourceDirectory = model.getBuild().getSourceDirectory();
		if (sourceDirectory.contains("${")) {
			String propertyKey = sourceDirectory.substring(sourceDirectory.indexOf("{") + 1, sourceDirectory.indexOf("}"));
			String property = getProperty(propertyKey);
			if (StringUtils.isNotEmpty(property)) {
				return property;
			}
			Profiles modProfiles = model.getProfiles();
			if (modProfiles != null) {
				List<Profile> profiles = modProfiles.getProfile();
				if (profiles != null) {
					for (Profile profile : profiles) {
						Boolean activeByDefault = profile.getActivation().isActiveByDefault();
						if (activeByDefault) {
							List<Element> any = profile.getProperties().getAny();
							for (Element element : any) {
								if (element.getTagName().equals(propertyKey)) {
									return element.getTextContent();
								}
							}
						}
					}
				}
			}
		}
		
		return sourceDirectory;
	}

	/**
	 * Adds the profile.
	 *
	 * @param id the id
	 * @param build the build
	 * @param modules the modules
	 * @return the profile
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public Profile addProfile(String id,BuildBase build,com.phresco.pom.model.Profile.Modules modules) throws PhrescoPomException {
		Profile profile = createProfiles(id);
		profile.setId(id);		
		profile.setBuild(build);
		profile.setModules(modules);
		model.getProfiles().getProfile().add(profile);
		return profile;
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws JAXBException the jAXB exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public static void main(String[] args) throws JAXBException, IOException, PhrescoPomException {
		PomProcessor p = new PomProcessor(new File("pomTest.xml"));
		p.getMavenVariableProp("/${phresco.test.dir}/functional");
	}

	/**
	 * Creates the profiles.
	 *
	 * @param id the id
	 * @return the profile
	 */
	private Profile createProfiles(String id) {
		Profiles profiles = model.getProfiles();
		if(profiles ==null) {
			profiles = new Profiles();
			model.setProfiles(profiles);
		} 
		
		Profile profile = new Profile();
		for(Profile tmpProfile : model.getProfiles().getProfile()){
			if(tmpProfile.getId().equals(id)){
				profile = tmpProfile;
				break;
			}
		}
		return profile;
	}

	/**
	 * Adds the profile.
	 *
	 * @param id the id
	 * @return the profile
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public Profile addProfile(String id) throws PhrescoPomException {
		Profile profile = createProfiles(id);
		profile.setId(id);	
		model.getProfiles().getProfile().add(profile);
		return profile;
	}

	/**
	 * Gets the profile.
	 *
	 * @param id the id
	 * @return the profile
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public Profile getProfile(String id) throws PhrescoPomException {
		if(model.getProfiles() != null && model.getProfiles().getProfile() != null) {
			for(Profile profile : model.getProfiles().getProfile()){
				if(id.equals(profile.getId())) {
					return profile;
				}	
			}
		}
		return null;
	}
	
	
	/**
	 * Site report config.
	 *
	 * @param reportPlugin the report plugin
	 */
	
	public void siteReportConfig(ReportPlugin reportPlugin) {
		com.phresco.pom.model.Reporting.Plugins plugins = new com.phresco.pom.model.Reporting.Plugins();
		if(model.getReporting()==null){
			model.setReporting(new Reporting());
			model.getReporting().setPlugins(plugins);
		}
		model.getReporting().getPlugins().getPlugin().add(reportPlugin);
	}
	
	/**
	 * Removes the site plugin.
	 *
	 * @param groupId the group id
	 * @param artifactId the artifact id
	 */
	public void removeSitePlugin(String groupId,String artifactId) {
		if(model.getReporting()==null || model.getReporting().getPlugins()==null){
			return;
		}
		com.phresco.pom.model.Reporting.Plugins plugins = model.getReporting().getPlugins();
		for (ReportPlugin plugin : plugins.getPlugin()) {
			if(groupId.equals(plugin.getGroupId()) && artifactId.equals(plugin.getArtifactId())){
				plugins.getPlugin().remove(plugin);
				if(model.getReporting().getPlugins().getPlugin().isEmpty()){
					model.setReporting(null);
				}
				return;
			}	
		}
	}
	
	/**
	 * Sets the final name.
	 *
	 * @param finalName the new final name
	 */
	public void setFinalName(String finalName){
		Build build = model.getBuild();
		if(build==null){
			 build = new Build();
			 model.setBuild(build);
		}
		model.getBuild().setFinalName(finalName);
	}
	
	/**
	 * Gets the final name.
	 *
	 * @return the final name
	 */
	public String getFinalName() {
		if(model.getBuild() != null){
			return model.getBuild().getFinalName();
		} 
		return null;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		if(model.getName() == null){
			return "";
		}
		return model.getName();
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name){
		model.setName(name);
	}
	
	/**
	 * Gets the group id.
	 *
	 * @return the group id
	 */
	public String getGroupId() {
		if(model.getGroupId() == null){
			return model.getParent().getGroupId();
		}
		return model.getGroupId();
	}
	
	/**
	 * Sets the group id.
	 *
	 * @param groupId the new group id
	 */
	public void setGroupId(String groupId){
		model.setGroupId(groupId);
	}
	
	/**
	 * Gets the artifact id.
	 *
	 * @return the artifact id
	 */
	public String getArtifactId() {
		if(model.getArtifactId() == null){
			return model.getParent().getArtifactId();
		}
		return model.getArtifactId();
	}
	
	/**
	 * Sets the artifact id.
	 *
	 * @param artifactId the new artifact id
	 */
	public void setArtifactId(String artifactId){
		model.setArtifactId(artifactId);
	}
	
	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public String getVersion(){
		if(model.getVersion() == null){
			return model.getParent().getVersion();
		}
		return model.getVersion();
	}
	
	/**
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	public void setVersion(String version){
		model.setVersion(version);
	}
	
	/**
	 * Gets the package.
	 *
	 * @return the package
	 */
	public String getPackage() {
		if(model.getPackaging() == null){
			return PomConstants.DEFAULT_PACKAGING;
		}
		return model.getPackaging();
	}
	
	/**
	 * Sets the packaging.
	 *
	 * @param packaging the new packaging
	 */
	public void setPackaging(String packaging){
		model.setPackaging(packaging);
	}
	
	/**
	 * Gets the parent.
	 *
	 * @return the parent
	 */
	public Parent getParent(){
		if (model.getParent() != null) {
			return model.getParent();
		}
		return null;
	}
	
	/**
	 * Gets the site plugin.
	 *
	 * @param artifactId the artifact id
	 * @return the site plugin
	 */
	
	public Plugin getSitePlugin(String artifactId){
		if(model.getBuild() != null && model.getBuild().getPluginManagement() != null) {
			for (Plugin plugin : model.getBuild().getPluginManagement().getPlugins().getPlugin()) {
				if(artifactId.equals(plugin.getArtifactId())) {
					return plugin;
				}
			}
		}
		return null;
	}
	
	/**
	 * Adds the site plugin.
	 *
	 * @throws JAXBException the jAXB exception
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public void addSitePlugin() throws JAXBException, PhrescoPomException{
		Plugin existingPlugin = getSitePlugin(PomConstants.SITE_PLUGIN_ARTIFACT_ID);
		if(existingPlugin != null) {
			existingPlugin.setVersion(PomConstants.SITE_PLUGIN_VERSION);
			return ;
		}
		Build build = model.getBuild();
		if(build == null){
			build = new Build();
			model.setBuild(build);
		}
		PluginManagement pluginManagement = build.getPluginManagement();
		if(pluginManagement == null){
			pluginManagement = new PluginManagement();
			build.setPluginManagement(pluginManagement);
		}
		com.phresco.pom.model.PluginManagement.Plugins plugins = build.getPluginManagement().getPlugins();
		if(plugins == null ) {
			plugins = new com.phresco.pom.model.PluginManagement.Plugins();
			build.getPluginManagement().setPlugins(plugins);
		}
		Plugin plugin = new Plugin();
		com.phresco.pom.model.Plugin.Dependencies dependencies = new com.phresco.pom.model.Plugin.Dependencies();
		plugin.setArtifactId(PomConstants.SITE_PLUGIN_ARTIFACT_ID);
		plugin.setVersion(PomConstants.SITE_PLUGIN_VERSION);
		plugin.setDependencies(dependencies);
		Dependency dependency = new Dependency();
		dependency.setGroupId(PomConstants.DOXIA_GROUPID);
		dependency.setArtifactId(PomConstants.DOXIA_ARTIFACT_ID);
		dependency.setVersion(PomConstants.DOXIA_VERSION);
		dependencies.getDependency().add(dependency);
		plugins.getPlugin().add(plugin);
	}
	
	/**
	 * Gets the report plugin.
	 *
	 * @return the report plugin
	 */
	public List<ReportPlugin> getReportPlugin() {
		if(model.getReporting() != null && model.getReporting().getPlugins().getPlugin() != null) {
			return model.getReporting().getPlugins().getPlugin();
		}
		return null;
	}
	
	/**
	 * Gets the project info report categories.
	 *
	 * @return the project info report categories
	 */
	public List<String> getProjectInfoReportCategories() {
		List<ReportPlugin> reportPlugin = getReportPlugin();
		if(reportPlugin != null){
			return getProjectInfoReport(reportPlugin);
		} return null;
	}

	/**
	 * Gets the project info report.
	 *
	 * @param reportPlugin the report plugin
	 * @return the project info report
	 */
	private static List<String> getProjectInfoReport(List<ReportPlugin> reportPlugin) {
		for (ReportPlugin reportPlugin2 : reportPlugin) {
			if(reportPlugin2.getGroupId().equals(Reports.PROJECT_INFO.getGroupId()) && reportPlugin2.getArtifactId().equals(Reports.PROJECT_INFO.getArtifactId())){
				List<ReportSet> reportSet = reportPlugin2.getReportSets().getReportSet();
				for (ReportSet reportSet2 : reportSet) {
					return reportSet2.getReports().getReport();
				}
			}
		} return null;
	}
	
	private ReportPlugin getReportPlugin(Reports reports) {
		if (model.getReporting() != null) {
			List<ReportPlugin> plugin = model.getReporting().getPlugins().getPlugin();
			for (ReportPlugin reportPlugin : plugin) {
				if (reports.getArtifactId().equals(reportPlugin.getArtifactId()) && reports.getGroupId().equals(reportPlugin.getGroupId())) {
					return reportPlugin;
				}
			}
		}
		return null;
	}
	
	/**
	 * Removes the project info report category.
	 *
	 * @param reportCategories the report categories
	 */
	public void removeProjectInfoReportCategory(List<ReportCategories> reportCategories){
		if (reportCategories != null) {
			List<String> removeList  = new ArrayList<String>();
			List<String> projectInfoReportCategories = getProjectInfoReportCategories();
			for (String string : projectInfoReportCategories) {
				for (ReportCategories reportCategoriesList : reportCategories) {
					if(reportCategoriesList.getName().equals(string)) {
						removeList.add(string);
					} 
				} 
			} projectInfoReportCategories.removeAll(removeList);
			ReportPlugin reportPlugin = getReportPlugin(Reports.PROJECT_INFO);
			List<ReportSet> reportSets = reportPlugin.getReportSets().getReportSet();
			reportSets.get(0).setReports(null);
			com.phresco.pom.model.ReportSet.Reports reports = new com.phresco.pom.model.ReportSet.Reports();
			if (projectInfoReportCategories != null) {
				for (String projectInfoReportCategorie : projectInfoReportCategories) {
					reports.getReport().add(projectInfoReportCategorie);
				}
				reportSets.get(0).setReports(reports);
			}
		}
	}
	
	/**
	 * Removes the all reporting plugin.
	 */
	public void removeAllReportingPlugin(){
		if(model.getReporting()==null || model.getReporting().getPlugins()==null){
			return;
		}
		com.phresco.pom.model.Reporting.Plugins plugins = model.getReporting().getPlugins();
		model.getReporting().getPlugins().getPlugin().removeAll(getReportPlugin());
	}
	
	/**
	 * Gets the sCM.
	 *
	 * @return the sCM
	 */
	public Scm getSCM() { 
		if(model.getScm() != null) { 
			return model.getScm();
		}
		return null;
	}
	
	/**
	 * Sets the scm.
	 *
	 * @param connection the connection
	 * @param developerConnection the developer connection
	 * @param url the url
	 * @param tag the tag
	 */
	
	public void setSCM(String connection, String developerConnection, String url, String tag) { 
		Scm scm = null;
		if(model.getScm() != null) { 
			scm = model.getScm();
			if (StringUtils.isNotEmpty(connection)) {
				scm.setConnection(connection);
			}
			if (StringUtils.isNotEmpty(developerConnection)) {
				scm.setDeveloperConnection(developerConnection);
			}
			if (StringUtils.isNotEmpty(url)) {
				scm.setUrl(url);
			}
			if (StringUtils.isNotEmpty(tag)) {
				scm.setTag(tag);
			}
		} else {
			scm = new Scm();
			if (StringUtils.isNotEmpty(connection)) {
				scm.setConnection(connection);
			}
			if (StringUtils.isNotEmpty(developerConnection)) {
				scm.setDeveloperConnection(developerConnection);
			}
			if (StringUtils.isNotEmpty(url)) {
				scm.setUrl(url);
			}
			if (StringUtils.isNotEmpty(tag)) {
				scm.setTag(tag);
			}			
			model.setScm(scm);
		}
	}
	
	/**
	 * Adds the repositories.
	 *
	 * @param repoId the repo id
	 * @param repoURL the repo url
	 */
	public void addRepositories(String repoId, String repoURL) {
		if(model.getRepositories() != null) {
			return;
		}
		if(model.getRepositories() == null) {
			model.setRepositories(new Repositories());
		}
		Repository repository = new Repository();
		repository.setId(repoId);
		repository.setUrl(repoURL);
		model.getRepositories().getRepository().add(repository);
	}
	
	/**
	 * To chech whether the pom is valid or not
	 */
	public boolean isPomValid() {
		if(model.getGroupId() != null && model.getArtifactId() != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Save.
	 *
	 * @throws PhrescoPomException the phresco pom exception
	 */
	public void save() throws PhrescoPomException  {
		try {
			if(document == null || binder == null) {
				saveMarshall();
				return;
			}
			Node updateXML = binder.updateXML(model);
			
	        TransformerFactory tf = TransformerFactory.newInstance();
	        Transformer t = tf.newTransformer();
	        t.setOutputProperty(OutputKeys.INDENT, "yes");
	        t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
	        FileOutputStream outputStream = new FileOutputStream(file);
	        t.transform(new DOMSource(updateXML), new StreamResult(outputStream));
	        outputStream.close();
		} catch (PropertyException e) {
			throw new PhrescoPomException(e);
		} catch (JAXBException e) {
			throw new PhrescoPomException(e);
		} catch (TransformerConfigurationException e) {
			throw new PhrescoPomException(e);
		} catch (TransformerException e) {
			throw new PhrescoPomException(e);
		} catch (FileNotFoundException e) {
			throw new PhrescoPomException(e);
		} catch (IOException e) {
			throw new PhrescoPomException(e);
		} catch (PhrescoPomException e) {
			throw new PhrescoPomException(e);
		} 
	}
	
	public void saveMarshall() throws PhrescoPomException {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Model.class);
			Marshaller marshal = jaxbContext.createMarshaller();
			marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshal.marshal(model, file);
		} catch (PropertyException e) {
			throw new PhrescoPomException(e);
		} catch (JAXBException e) {
			throw new PhrescoPomException(e);
		}
	}
}
