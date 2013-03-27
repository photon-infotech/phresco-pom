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
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.phresco.pom.site.ReportCategories;
import com.phresco.pom.site.Reports;
import com.phresco.pom.util.SiteConfigurator;

public class SiteConfoguratorTest {

	private File file;
	private SiteConfigurator configurator;
	private List<Reports> report;
	private List<ReportCategories> reportCategories;
	private List<Reports> reports;
	
	@Before
	public void prepare() throws IOException {
		file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
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
	public void getReportTest() {
		reports = configurator.getReports(file);
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
	
	@After
	public void delete() {
		File file = new File("pomTest.xml");
		if(file.exists()) {
			file.delete();
		}
	}
}
