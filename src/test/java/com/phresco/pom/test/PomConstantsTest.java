package com.phresco.pom.test;


import java.io.File;

import org.junit.Before;
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.phresco.pom.model.PluginExecution;
import com.phresco.pom.model.PluginExecution.Goals;
import com.phresco.pom.util.PomConstants;
import com.phresco.pom.util.PomProcessor;

import static org.powermock.api.easymock.PowerMock.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PomProcessor.class)
public class PomConstantsTest {
	PomConstants pomConsts;
	@Before
	public void setUp() throws Exception {
		

	}
	@Test
	public void test() throws Exception{
		Whitebox.invokeConstructor(PomConstants.class);
		
	}

}
