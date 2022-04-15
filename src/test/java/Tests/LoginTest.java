package Tests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.testng.annotations.Test;
import engine.KeywordEngine;

public class LoginTest {

	public  KeywordEngine KeywordEngine; 
	
	
	@Parameters({"Path","OutputPath", "SheetName"})
	@Test
	public void logintest(String Path, String OutputPath, String SheetName) throws EncryptedDocumentException, IOException {
		KeywordEngine = new KeywordEngine();
		KeywordEngine.startExecution(Path, OutputPath, SheetName);
		
		System.out.println("End of Test");
	}
	
	
}

