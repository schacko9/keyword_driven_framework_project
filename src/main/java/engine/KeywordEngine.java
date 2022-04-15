package engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import base.Base;

public class KeywordEngine {
		
		public Base base;
		public  WebDriver driver;
		public Properties prop;
		public static Workbook workbook;
		public static Sheet sheet;
		public Row row;
		public Cell cell;
		public WebElement element;		
		
		
		public void startExecution(String Path, String OutputPath, String SheetName) throws EncryptedDocumentException, IOException {
		
			String LocatorName = null;
			String LocatorValue = null;
			
			
			FileInputStream fis = new FileInputStream(Path);	
			FileOutputStream fos = new FileOutputStream(OutputPath);
			
			workbook = WorkbookFactory.create(fis);
			sheet = workbook.getSheet(SheetName);
		
			int k = 0;																								// Column Tracking
			for(int i=0; i<sheet.getLastRowNum(); i++) {
				try {
					String Locator = sheet.getRow(i+1).getCell(k+1).toString().trim();
					
					if(!Locator.equalsIgnoreCase("NA")) {
						LocatorName = Locator.split("=")[0].trim();
						LocatorValue = Locator.split("=")[1].trim();
					}
					
					String Action= sheet.getRow(i+1).getCell(k+2).toString().trim();	
					String Value = sheet.getRow(i+1).getCell(k+3).toString().trim();
	
					System.out.println(Action + ", " + Value);
	
					
					switch (Action) {																				// Switch Case on the Action Values that doesnt have a Locator attached to it
					case "open browser": {
						base = new Base();
						base.initializeProperties();
						if(Value.isEmpty() || Value.equals("NA")) {
							try{
								driver = base.initializeDriver(prop.getProperty("browser"));
								
								row = sheet.getRow(i+1);
								cell = row.createCell(k+4);
								cell.setCellValue("PASS");					
							}
							catch(Exception e) {
								row = sheet.getRow(i+1);
								cell = row.createCell(k+4);
								cell.setCellValue("FAIL");	
							}
						}
													
						else if(!Value.isEmpty() || !Value.equals("NA")){
							try{
								driver = base.initializeDriver(Value);
								
								row = sheet.getRow(i+1);
								cell = row.createCell(k+4);
								cell.setCellValue("PASS");
							}
							catch(Exception e) {
								row = sheet.getRow(i+1);
								cell = row.createCell(k+4);
								cell.setCellValue("FAIL");	
							}		
						}
						else {
							row = sheet.getRow(i+1);
							cell = row.createCell(k+4);
							cell.setCellValue("FAIL");					
						}
						LocatorValue = null;																			// Setting to Null so on the next iteration these values can be used without error
						break;
					}
					
					case "enter url": {
						if(Value.isEmpty() || Value.equals("NA")) {
							try{
								driver.get(prop.getProperty("url"));
								
								row = sheet.getRow(i+1);
								cell = row.createCell(k+4);
								cell.setCellValue("PASS");
							}
							catch(Exception e) {
								row = sheet.getRow(i+1);
								cell = row.createCell(k+4);
								cell.setCellValue("FAIL");	
							}		
							
						}
						else if(!Value.isEmpty() || !Value.equals("NA")){
							try{
								driver.get(Value);
								
								row = sheet.getRow(i+1);
								cell = row.createCell(k+4);
								cell.setCellValue("PASS");
							}
							catch(Exception e) {
								row = sheet.getRow(i+1);
								cell = row.createCell(k+4);
								cell.setCellValue("FAIL");	
							}	
							
						}
						else {
							row = sheet.getRow(i+1);
							cell = row.createCell(k+4);
							cell.setCellValue("FAIL");	
						}
						LocatorValue = null; 																			// Setting to Null so on the next iteration these values can be used without error
						break;
					}
					
					case "quit": {
						try{
							driver.quit();
							
							row = sheet.getRow(i+1);
							cell = row.createCell(k+4);
							cell.setCellValue("PASS");	
						}
						catch(Exception e) {
							row = sheet.getRow(i+1);
							cell = row.createCell(k+4);
							cell.setCellValue("FAIL");	
						}	
						break;
					}						
					default:
						break;
					}			
					
					
					switch (LocatorName) {																			
					case "id": {
						element = driver.findElement(By.id(LocatorValue));
						if(Action.equalsIgnoreCase("sendkeys")) {
							try{
								element.sendKeys(Value);
								
								row = sheet.getRow(i+1);
								cell = row.createCell(k+4);
								cell.setCellValue("PASS");	
							}
							catch(Exception e) {
								row = sheet.getRow(i+1);
								cell = row.createCell(k+4);
								cell.setCellValue("FAIL");	
							}		
							
						}
						else if(Action.equalsIgnoreCase("click")){
							try{
								element.click();
								
								row = sheet.getRow(i+1);
								cell = row.createCell(k+4);
								cell.setCellValue("PASS");
							}
							catch(Exception e) {
								row = sheet.getRow(i+1);
								cell = row.createCell(k+4);
								cell.setCellValue("FAIL");	
							}	
								
						}
						else {
							row = sheet.getRow(i+1);
							cell = row.createCell(k+4);
							cell.setCellValue("FAIL");						
						}
						LocatorName = null;																			// Setting to Null so on the next iteration these values can be used without error
						break;
					}
					
					case "linkText": {
						try{
							element = driver.findElement(By.linkText(LocatorValue));
							element.click();
							
							row = sheet.getRow(i+1);
							cell = row.createCell(k+4);
							cell.setCellValue("PASS");
						}
						catch(Exception e) {
							row = sheet.getRow(i+1);
							cell = row.createCell(k+4);
							cell.setCellValue("FAIL");	
						}		
						
						LocatorName = null;																			// Setting to Null so on the next iteration these values can be used without error
						break;
					}
					
					case "class": {
						try{
							element = driver.findElement(By.className(LocatorValue));
							element.click();
							
							row = sheet.getRow(i+1);
							cell = row.createCell(k+4);
							cell.setCellValue("PASS");
							
						}
						catch(Exception e) {
							row = sheet.getRow(i+1);
							cell = row.createCell(k+4);
							cell.setCellValue("FAIL");	
						}
						LocatorName = null;																			// Setting to Null so on the next iteration these values can be used without error
						break;
					}
					default:
						break;
					}
				}
				catch(Exception e) {
					
				}			
			}
			workbook.write(fos);		

			
		}
}
