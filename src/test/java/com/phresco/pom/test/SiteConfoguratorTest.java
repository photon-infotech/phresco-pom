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

import static org.easymock.EasyMock.expect;
import static org.powermock.api.easymock.PowerMock.replay;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.easymock.PowerMock;

import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.model.Model;
import com.phresco.pom.model.Profile;
import com.phresco.pom.model.Model.Profiles;
import com.phresco.pom.model.ReportPlugin;
import com.phresco.pom.model.ReportPlugin.ReportSets;
import com.phresco.pom.model.ReportSet;
import com.phresco.pom.site.ReportCategories;
import com.phresco.pom.site.Reports;
import com.phresco.pom.util.AndroidPomProcessor;
import com.phresco.pom.util.PomProcessor;
import com.phresco.pom.util.SiteConfigurator;

public class SiteConfoguratorTest {

	private File file,file1;
	private SiteConfigurator configurator;
	private List<Reports> report;
	private List<ReportCategories> reportCategories;
	private List<Reports> reports;

	@Before
	public void prepare() throws IOException {
		file = new File("pomTest.xml");
		file1 = new File("pomTest2.xml");

		/*if(file.exists()) {
			file.delete();
		}*/
		configurator = new SiteConfigurator();
		report = new ArrayList<Reports>();
		report.add(Reports.COBERTURA);
		report.add(Reports.JAVADOC);
		report.add(Reports.JDEPEND);
		report.add(Reports.JXR);
		report.add(Reports.LINK_CHECK);
		report.add(Reports.PMD);
		report.add(Reports.PROJECT_INFO);
		report.add(Reports.SUREFIRE_REPORT);
		reportCategories = new ArrayList<ReportCategories>();
		reportCategories.add(ReportCategories.CIM);
		reportCategories.add(ReportCategories.INFO_DEPENDENCIES);
		reportCategories.add(ReportCategories.INFO_INDEX);
		reportCategories.add(ReportCategories.INFO_MODULE);
		reportCategories.add(ReportCategories.LICENSE);
		reportCategories.add(ReportCategories.SUMMARY);
		configurator.addReportPlugin(report, reportCategories, file);
	}

	@Test
	public void addReportPlginTest() {
		configurator.addReportPlugin(report, reportCategories, file);
		Assert.assertEquals("cobertura-maven-plugin",configurator.getReports(file).get(0).getArtifactId());
	}

	@Test
	public void addReportPlginExcTest() throws IOException  {
		File file2 =  Mockito.spy(new File("pomTestnew1.xml"));
		Mockito.doThrow(IOException.class).when(file2).createNewFile();
		SiteConfigurator configurator1 = new SiteConfigurator();
		configurator1.addReportPlugin(report, reportCategories, file2);
		file2.delete();
	}

	@Test
	public void addReportPlginExc1Test() throws PhrescoPomException, JAXBException {
		PomProcessor  pompro =Mockito.spy(new PomProcessor(file));
		Mockito.doThrow(new JAXBException("")).when(pompro).addSitePlugin();
		configurator.addReportPlugin(report, reportCategories, file);

	}

	@Test
	public void getReportTest() {
		reports = configurator.getReports(file);
		if(reports != null) {
			Assert.assertTrue("Test Success", true);
		}
	}

	@Test
	public void getReportB1Test() {
		reports = configurator.getReports(file1);
		if(reports != null) {
			Assert.assertTrue("Test Success", true);
		}
	}
	@Test
	public void removeReportPluginTest() {
		reports = new ArrayList<Reports>();
		reports.add(Reports.COBERTURA);
		reports.add(Reports.JAVADOC);
		configurator.removeReportPlugin(reports, file);
		Assert.assertEquals(6, configurator.getReports(file).size());
	}

	@Test
	public void removeReportPluginExcTest() throws IOException {
		reports = new ArrayList<Reports>();
		reports.add(Reports.COBERTURA);
		reports.add(Reports.JAVADOC);
		File file2 =  Mockito.spy(new File("pomTestnew1.xml"));
		Mockito.doThrow(IOException.class).when(file2).createNewFile();
		configurator.removeReportPlugin(reports, file2);
		file2.delete();
	}

	@Test
	public void removeReportCategory() {
		reportCategories = new ArrayList<ReportCategories>();
		reportCategories.add(ReportCategories.LICENSE);
		reportCategories.add(ReportCategories.CIM);
		configurator.removeReportCategory(file, reportCategories);
		List<Reports> reportsList = configurator.getReports(file);
		List<ReportCategories> reportCategories = null;
		for (Reports reports : reportsList) {
			if(reports.getArtifactId().equals(Reports.PROJECT_INFO.getArtifactId())) {
				reportCategories = reports.getReportCategories();
			}
		}
		Assert.assertEquals(reportCategories.size(), 4);
	}

	@Test
	public void removeReportCategoryExc() throws IOException {
		reportCategories = new ArrayList<ReportCategories>();
		reportCategories.add(ReportCategories.LICENSE);
		reportCategories.add(ReportCategories.CIM);
		File file2 =  Mockito.spy(new File("pomTestnew1.xml"));
		Mockito.doThrow(IOException.class).when(file2).createNewFile();
		configurator.removeReportCategory(file2, reportCategories);
		file2.delete();

	}

	@Test
	public void addReportPluginTest() {
		configurator.addReportPlugin(report, file);

	}

	@Test
	public void setReportSetBranch() throws PhrescoPomException, ParserConfigurationException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Class[] cArg = new Class[5];
		cArg[0] = List.class;
		cArg[1] = ReportPlugin.class;
		cArg[2] = ReportSets.class;
		cArg[3] = ReportSet.class;
		cArg[4] = com.phresco.pom.model.ReportSet.Reports.class;
		ReportSets reprtSets=new ReportSets();
		Method privateStringMethod = SiteConfigurator.class.getDeclaredMethod("setReportSet",cArg);
		privateStringMethod.setAccessible(true);
		privateStringMethod.invoke(configurator,null,null,reprtSets,null,null);
	}

	@After
	public void delete() {
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
		if(file1.exists()) {
			file1.delete();
		}
	}
}
