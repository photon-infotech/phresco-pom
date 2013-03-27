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
package com.phresco.pom.util;

public final class PomConstants {
	
	private PomConstants() {
	}

	public static final String ALIAS = "alias";
	
	public static final String KEYPASS = "keypass";
	
	public static final String STOREPASS = "storepass";
	
	public static final String KEYSTORE = "keystore";
	
	public static final String FINAL_NAME = "${project.artifactId}-${project.version}";
	
	public static final String SITE_PLUGIN_ARTIFACT_ID = "maven-site-plugin";
	
	public static final String SITE_PLUGIN_VERSION = "3.1";
	
	public static final String DOXIA_GROUPID = "org.apache.maven.doxia";
	
	public static final String DOXIA_ARTIFACT_ID = "doxia-module-confluence";
	
	public static final String DOXIA_VERSION = "1.2"; 
	
	public static final String MVN_SCOPE_SYSTEM = "system"; 
	
	public static final String DEFAULT_PACKAGING = "jar";
	
	public static final String DEFAULT_SCOPE  = "compile";

}
