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
package com.phresco.pom.util;

public final class POMErrorCode {
	
	private static int codeValue;
	private int code;
	
	private String message;
	
	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	/**
	 * @param code
	 * @param message
	 */
	private POMErrorCode(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public static final POMErrorCode DEPENDENCY_NOT_FOUND = new POMErrorCode(codeValue, "DEPENDENCY NOT AVAILABLE"); 

	public static final POMErrorCode MODULE_NOT_FOUND = new POMErrorCode(codeValue, "MODULE NOT AVAILABLE");
	
	public static final POMErrorCode BUILD_NOT_FOUND = new POMErrorCode(codeValue, "BUILD NOT AVAILABLE");
	
	public static final POMErrorCode PLUGIN_NOT_FOUND = new POMErrorCode(codeValue, "PLUGIN NOT AVAILABLE");
	
	public static final POMErrorCode PROPERTY_NOT_FOUND = new POMErrorCode(codeValue, "PROPERTY NOT AVAILABLE");
	
	public static final POMErrorCode SOURCE_DIRECTORY_NOT_FOUND = new POMErrorCode(codeValue, "SOURCE DIRECTORY NOT AVAILABLE");
	
	public static final POMErrorCode PROFILE_NOT_FOUND = new POMErrorCode(codeValue, "PROFILE NOT AVAILABLE");
	
	public static final POMErrorCode KEYSTORE_NOT_FOUND = new POMErrorCode(codeValue, "KEYSTORE VALUE NOT AVAILABLE");
	
	public static final POMErrorCode PROFILE_ID_NOT_FOUND = new POMErrorCode(codeValue, "PROFILE ID NOT AVAILABLE");
	
	public static final POMErrorCode MODULE_EXIST = new POMErrorCode(codeValue, "MODULE ALREADY EXIST");
	
	public static final POMErrorCode PROFILE_ID_EXIST = new POMErrorCode(codeValue, "PROFILE ID ALREADY EXIST");
}
