/*
 * ###
 * phresco-pom
 * 
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
package com.phresco.pom.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AddDependencyTest.class,
	AddModulesTest.class,
	AddPluginTest.class,
	AddPropertiesTest.class,
	ChangeDependencyVersionTest.class,
	ChangeDependencyVersionTest.class,
	DeleteDependencyTest.class,
	GetModelTest.class,
	GetPluginTest.class,
	RemoveModuleTest.class,
	RemoveAllDependencyTest.class, 
	GetModulesTest.class,
	GetPropertyTest.class,
	ChangePluginVersionTest.class,
	AddProfileTest.class,
	DependencySystemPathTest.class, 
	DeletePluginTest.class,
	AddExecutionConfigurationTest.class,
	GetPluginConfigurationValueTest.class,
	AddPluginDependencyTest.class,
	SetSourceDirectoryTest.class,
	SetSCMTest.class,
	AndroidPomProcessorTest.class}
	 )
public class AllTests {

}
