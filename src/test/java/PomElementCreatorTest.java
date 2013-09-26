import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.phresco.pom.model.Activation;
import com.phresco.pom.model.BuildBase;
import com.phresco.pom.util.PomElementCreator;



public class PomElementCreatorTest {

	

	@Test
	public void createBuildBaseTest(){
		BuildBase build=PomElementCreator.createBuildBase("test", "D:\temp1");
		Assert.assertEquals("test", build.getDefaultGoal());

	}
	
	@Test
	public void createActivationTest(){
		Activation activation=PomElementCreator.createActivation(true,"jdk");
		Assert.assertEquals("jdk", activation.getJdk());

	}
	
	@Test
	public void createProfileModuleTest(){
		com.phresco.pom.model.Profile.Modules modules=PomElementCreator.createProfileModule("java");
		Assert.assertEquals("java", modules.getModule().get(0));
	}
	
	
}
