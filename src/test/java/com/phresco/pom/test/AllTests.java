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

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AddDependencyTest.class,
	AddExecutionConfigurationTest.class,
	AddModulesTest.class,
	AddPluginDependencyTest.class,
	AddPluginTest.class,
	AddProfileTest.class,
	AddProfileWithBuildBaseTest.class,
	AddPropertiesTest.class,
	AndroidPomProcessorTest.class,
	AndroidProfileTest.class,
	ChangeDependencyVersionTest.class,
	ChangePluginVersionTest.class,
	DeleteDependencyTest.class,
	DeletePluginTest.class,
	DependencySystemPathTest.class,
	GetModelTest.class,
	GetModulesTest.class,
	GetPluginTest.class,
	GetPluginConfigurationValueTest.class,
	GetPropertyTest.class,
	PhrescoPomExceptionTest.class,
	PomConstantsTest.class,
	PomElementCreatorTest.class,
	PomProcessor1Test.class,
	PomProcessorTest.class,
	RemoveAllDependencyTest.class,
	RemoveModuleTest.class,
	ReportCategoriesTest.class,
	ReportsTest.class,
	SetSCMTest.class,
	SetSourceDirectoryTest.class,
	SiteConfoguratorTest.class,
	SiteMessagesTest.class }
	 )
public class AllTests {
// intentionally blank. All tests were added via annotations
}
