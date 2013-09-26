package com.phresco.pom.test;


import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.phresco.pom.android.AndroidProfile;
import com.phresco.pom.model.Activation;
import com.phresco.pom.model.Build.Plugins;
import com.phresco.pom.model.PluginExecution.Configuration;
import com.phresco.pom.model.Plugin;

public class AndroidProfileTest {
	AndroidProfile profile;
	@Before
	public void setUp() throws Exception {
		profile=new AndroidProfile();
	}

	@Test
	public void getActivationTest(){
		Activation activation=new Activation();
		activation.setJdk("jdk");
		profile.setActivation(activation);
		Assert.assertEquals("jdk", profile.getActivation().getJdk());
	}
	
	@Test
	public void getPluginsTest(){
		Plugins plugins=new Plugins();
		Plugin plugin=new Plugin();
		plugin.setArtifactId("test");
		plugins.getPlugin().add(plugin);
		profile.setPlugins(plugins);
		Assert.assertEquals("test", profile.getPlugins().getPlugin().get(0).getArtifactId());
	}
	
	@Test
	public void getConfigTest(){
		com.phresco.pom.model.Plugin.Configuration configuration=new com.phresco.pom.model.Plugin.Configuration();
		profile.setConfig(configuration);
		Assert.assertEquals(configuration, profile.getConfig());
	}
	
	@Test
	public void getProfileNameTest(){
		profile.setProfileName("test");
		Assert.assertEquals("test",profile.getProfileName());
	}
	
	@Test
	public void isVerboseTest(){
		profile.setVerbose(true);
		Assert.assertEquals(true,profile.isVerbose());
	}
	@Test
	public void isVerifyTest(){
		profile.setVerify(true);
		Assert.assertEquals(true,profile.isVerify());
	}
	
}
