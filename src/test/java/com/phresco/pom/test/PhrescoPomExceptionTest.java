package com.phresco.pom.test;


import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.phresco.pom.exception.POMErrorCode;
import com.phresco.pom.exception.PhrescoPomException;

public class PhrescoPomExceptionTest {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void PhrescoPomException1Test() {
		PhrescoPomException exc=new PhrescoPomException();
		
	}

	@Test
	public void PhrescoPomException2Test() {
		Throwable cause=new Throwable();
		PhrescoPomException exc=new PhrescoPomException(POMErrorCode.BUILD_NOT_FOUND,"test",cause);
		
	}
	
	@Test
	public void PhrescoPomException3Test() {
		Throwable cause=new Throwable();
		PhrescoPomException exc=new PhrescoPomException("test",cause);
		
	}
	@Test
	public void PhrescoPomException4Test() {
		PhrescoPomException exc=new PhrescoPomException(POMErrorCode.BUILD_NOT_FOUND,"test");
		
	}
	@Test
	public void PhrescoPomException5Test() {
		Throwable cause=new Throwable();
		PhrescoPomException exc=new PhrescoPomException(POMErrorCode.BUILD_NOT_FOUND,cause);
		
	}
	
	@Test
	public void PhrescoPomException6Test() {
		Throwable cause=new Throwable();
		PhrescoPomException exc=new PhrescoPomException(POMErrorCode.BUILD_NOT_FOUND,cause);
		POMErrorCode code=exc.getErrorCode();
		Assert.assertEquals("BUILD NOT AVAILABLE",exc.getErrorCode().getMessage());
		
	}
	@Test
	public void PhrescoPomException7Test() {
		Throwable cause=new Throwable();
		PhrescoPomException exc=new PhrescoPomException(POMErrorCode.BUILD_NOT_FOUND,cause);
		POMErrorCode code=exc.getErrorCode();
		Assert.assertEquals("POME1003",exc.getErrorCode().getCode());
	}
}
