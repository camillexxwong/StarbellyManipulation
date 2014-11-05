package cawang.selenium.starbelly.document;

import java.io.IOException;

import org.junit.*;
import org.openqa.selenium.*;

import cawang.selenium.starbelly.tool.DriverTool;
import cawang.selenium.starbelly.tool.ElementDictionary;
import cawang.selenium.starbelly.tool.ManipulationTool;
public class EA_Locations {

	private DriverTool driverTool;
	private ManipulationTool manipulationTool;
	private String imgXPath = "div.gmnoprint > div > img";

	public EA_Locations(DriverTool driverTool) {
		this.driverTool = driverTool;
		this.manipulationTool=new ManipulationTool(driverTool);
	}

	@Test
	public void test() throws Exception {
		int contentIndex=1;
		
		System.out.println("[Info] Begin EA_Locations Panel!------------------------------------");
		driverTool.waitForElementPresent(By.cssSelector(imgXPath), 20);
		System.out.println("[Info] EA_Locations Ready!");

		driverTool.captureScreenshot(contentIndex+++"_Oringinal"); //GetContent

		driverTool.getDriver().findElement(By.cssSelector(ElementDictionary.CLEAR_ALL_EA)).click();
		System.out.println("[Action] Clear All Filter");
		try {
			driverTool.waitForElementNotPresent(By.cssSelector(imgXPath), 10);
		} catch (NoSuchElementException e) {
			System.out.println("[Warning] IMG not present failed");
		}
		System.out.println("[Info] Begin to Apply Filter!");
		driverTool.waitForElementPresent(By.cssSelector(imgXPath), 20);
		System.out.println("[Info] End of Apply Filter!");
		Thread.sleep(5000);

		driverTool.captureScreenshot(contentIndex+++"_NoFilter"); //GetContent

		manipulationTool.click(By.cssSelector(ElementDictionary.EXPAND_ALL_EA));
		System.out.println("[Action] Expand All");
		manipulationTool.selectFilter("Gender", "mstr146", new int[]{2,3});
		manipulationTool.selectFilter("Engagement Type", "mstr201", new int[]{3,4,5,6,7});
		manipulationTool.selectFilter("Fan Type", "mstr206", new int[]{1,2});
		manipulationTool.click(By.cssSelector(ElementDictionary.COLLAPSE_ALL_EA));
		System.out.println("[Action] Collapse All");

		manipulationTool.selectSearchTree("Country", "mstr172", "Uni", 3, "United States");

		driverTool.getDriver().findElement(By.cssSelector(ElementDictionary.APLLY_FILTER_EA)).click();
		System.out.println("[Action] Apply Filter");
		try {
			driverTool.waitForElementNotPresent(By.cssSelector(imgXPath), 10);
		} catch (NoSuchElementException e) {
			System.out.println("[Warning] IMG not present failed");
		}
		System.out.println("[Info] Begin to Apply Filter!");
		driverTool.waitForElementPresent(By.cssSelector(imgXPath), 20);
		System.out.println("[Info] End of Apply Filter!");
		Thread.sleep(5000);

		driverTool.captureScreenshot(contentIndex+++"_Filter"); //GetContent

		driverTool.getDriver().findElement(By.cssSelector(ElementDictionary.FILTER_COLLAPSE_COUNTRY_EA)).click();
		System.out.println("[Action] Collapse Country");

		System.out.println("[Info] End of EA_EngagerAffinity Panel!------------------------------------");
	}

}
