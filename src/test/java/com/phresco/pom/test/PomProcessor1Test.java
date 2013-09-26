package com.phresco.pom.test;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.phresco.pom.exception.PhrescoPomException;
import com.phresco.pom.model.Model;
import com.phresco.pom.util.PomProcessor;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(JAXBContext.class)
public class PomProcessor1Test {
	File fileNew;
	@Before
	public void setUp() throws Exception {
		 fileNew=new File("pomNew1.xml");
	}

	@Test(expected=PhrescoPomException.class)
	public void PomProcessorExc() throws  PhrescoPomException, JAXBException, IOException{
		BufferedOutputStream out=new BufferedOutputStream(new FileOutputStream(fileNew));
		out.write("<project".getBytes());
		out.close();
		mockStatic(JAXBContext.class);
		expect(JAXBContext.newInstance(Model.class)).andThrow(new JAXBException(""));
		replay(JAXBContext.class);
		PomProcessor processor = new PomProcessor(new File("pomNew1.xml"));
	}

	@Test(expected=PhrescoPomException.class)
	public void saveExc() throws  PhrescoPomException, JAXBException{
		PomProcessor processor = new PomProcessor(new File("pomNew.xml"));
		mockStatic(JAXBContext.class);
		expect(JAXBContext.newInstance(Model.class)).andThrow(new JAXBException(""));
		replay(JAXBContext.class);
		processor.save();
	}

	@Test
	public void setPropertyExc1() throws PhrescoPomException, ParserConfigurationException{
		PomProcessor processor = new PomProcessor(new File("pomNew.xml"));
		DocumentBuilderFactory  docBuilder =PowerMock.createPartialMock(DocumentBuilderFactory.class, "newDocumentBuilder");
		expect(docBuilder.newDocumentBuilder()).andThrow(new ParserConfigurationException());
		replay(docBuilder);
		processor.setProperty("Photon", "Phresco");
		processor.save();
	}
	@After
	public void delete() throws IOException{
		File file = new File("pomNew.xml");
		if(file.exists()) {
			file.delete();
		}
		if(fileNew.exists()) {
			fileNew.delete();
		}
	}

}
