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
package com.phresco.pom.exception;

/**
 * POM error codes
 * @author arunachalam
 *
 */
public final class POMErrorCode {
	
	/**
	 * error code 
	 */
	private String code;
	
	/**
	 * Error message
	 */
	private String message;
	
	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	/**
	 * @param code
	 * @param message
	 */
	private POMErrorCode(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public static final POMErrorCode DEPENDENCY_NOT_FOUND = new POMErrorCode("POME1001", "DEPENDENCY NOT AVAILABLE"); 

	public static final POMErrorCode MODULE_NOT_FOUND = new POMErrorCode("POME1002", "MODULE NOT AVAILABLE");
	
	public static final POMErrorCode BUILD_NOT_FOUND = new POMErrorCode("POME1003", "BUILD NOT AVAILABLE");
	
	public static final POMErrorCode PLUGIN_NOT_FOUND = new POMErrorCode("POME1004", "PLUGIN NOT AVAILABLE");
	
	public static final POMErrorCode PROPERTY_NOT_FOUND = new POMErrorCode("POME1005", "PROPERTY NOT AVAILABLE");
	
	public static final POMErrorCode SOURCE_DIRECTORY_NOT_FOUND = new POMErrorCode("POME1006", "SOURCE DIRECTORY NOT AVAILABLE");
	
	public static final POMErrorCode PROFILE_NOT_FOUND = new POMErrorCode("POME1007", "PROFILE NOT AVAILABLE");
	
	public static final POMErrorCode KEYSTORE_NOT_FOUND = new POMErrorCode("POME1008", "KEYSTORE VALUE NOT AVAILABLE");
	
	public static final POMErrorCode PROFILE_ID_NOT_FOUND = new POMErrorCode("POME1009", "PROFILE ID NOT AVAILABLE");
	
	public static final POMErrorCode MODULE_EXIST = new POMErrorCode("POME1010", "MODULE ALREADY EXIST");
	
	public static final POMErrorCode PROFILE_ID_EXIST = new POMErrorCode("POME1011", "PROFILE ID ALREADY EXIST");
	
	public static final POMErrorCode INTERNAL_ERROR = new POMErrorCode("POME1000", "INTERNAL ERROR");
	
}
