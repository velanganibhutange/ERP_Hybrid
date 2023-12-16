package driverFactory;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {
WebDriver driver;
String inputpath ="./FileInput/Controller.xlsx";
String Outputpath ="./FileOutPut/HybridResults.xlsx";
String TestCases ="MasterTestCases";
ExtentReports report;
ExtentTest logger;
public void startTest() throws Throwable
{
	String Module_Status="";
	//create reference object to call all excel methods
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	//iterate all rows in testcases sheet
	for(int i=1;i<=xl.rowCount(TestCases);i++)
	{
		if(xl.getCellData(TestCases, i, 2).equalsIgnoreCase("Y"))
		{
			//reading corresponding sheet or test case
			String TCModule =xl.getCellData(TestCases, i, 1);
			report = new ExtentReports("./target/Reports/"+TCModule+FunctionLibrary.generateDate()+".html");
			logger = report.startTest(TCModule);
			//iterate all rows in TCModule
			for(int j=1;j<=xl.rowCount(TCModule);j++)
			{
				//read cell from TCModule
				String Description = xl.getCellData(TCModule, j, 0);
				String Object_Type =xl.getCellData(TCModule, j, 1);
				String Locator_Type = xl.getCellData(TCModule, j, 2);
				String Locator_Value = xl.getCellData(TCModule, j, 3);
				String TestData = xl.getCellData(TCModule, j, 4);
				try {
					if(Object_Type.equalsIgnoreCase("startBrowser"))
					{
						driver = FunctionLibrary.startBrowser();
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("openUrl"))
					{
						FunctionLibrary.openUrl(driver);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("waitForElement"))
					{
						FunctionLibrary.waitForElement(driver, Locator_Type, Locator_Value, TestData);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("typeAction"))
					{
						FunctionLibrary.typeAction(driver, Locator_Type, Locator_Value, TestData);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("clickAction"))
					{
						FunctionLibrary.clickAction(driver, Locator_Type, Locator_Value);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("validateTitle"))
					{
						FunctionLibrary.validateTitle(driver, TestData);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("closeBrowser"))
					{
						FunctionLibrary.closeBrowser(driver);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("mouseClick"))
					{
						FunctionLibrary.mouseClick(driver);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("categoryTable"))
					{
						FunctionLibrary.categoryTable(driver, TestData);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("dropDownAction"))
					{
						FunctionLibrary.dropDownAction(driver, Locator_Type, Locator_Value, TestData);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("capturestock"))
					{
						FunctionLibrary.capturestock(driver, Locator_Type, Locator_Value);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("stockTable"))
					{
						FunctionLibrary.stockTable(driver);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("captureSupp"))
					{
						FunctionLibrary.capturesupplier(driver, Locator_Type, Locator_Value);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("supplierTable"))
					{
						FunctionLibrary.supplierTable(driver);
						logger.log(LogStatus.INFO, Description);
					}
					//write as pass into TCModule
					xl.setCellData(TCModule, j, 5, "Pass", Outputpath);
					logger.log(LogStatus.PASS, Description);
					Module_Status="True";
					
				}catch(Exception e)
				{
					System.out.println(e.getMessage());
					//write as Fail into TCModule
					xl.setCellData(TCModule, j, 5, "Fail", Outputpath);
					logger.log(LogStatus.FAIL, Description);
					Module_Status="False";
				}
				if(Module_Status.equalsIgnoreCase("True"))
				{
					xl.setCellData(TestCases, i, 3, "Pass", Outputpath);
				}
				else
				{
					xl.setCellData(TestCases, i, 3, "Fail", Outputpath);	
				}
				report.endTest(logger);
				report.flush();
			}
		}
		else
		{
			//write blocked which testcases are flaged to N
			xl.setCellData(TestCases, i, 3, "Blocked", Outputpath);
		}
	}
}
}














