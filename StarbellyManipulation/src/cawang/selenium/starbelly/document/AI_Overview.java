package cawang.selenium.starbelly.document;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.*;
import org.openqa.selenium.*;

import cawang.selenium.starbelly.*;
import cawang.selenium.starbelly.tool.DriverTool;
import cawang.selenium.starbelly.tool.ElementDictionary;
import cawang.selenium.starbelly.tool.ManipulationTool;

/**
 * Execution Flow for AI_Engagement Panel
 * 
 */
public class AI_Overview implements IPanel {
	private DriverTool driverTool;
	private ManipulationTool manipulationTool;

	public AI_Overview(DriverTool driverTool) {
		this.driverTool = driverTool;
		this.manipulationTool=new ManipulationTool(driverTool);
	}
	
	private String panelName="AI_Overview";

	/**
	 * Main Execution Flow for AI_Overview Panel
	 * 
	 */
	@Test
	@Override
	public void test() throws InterruptedException{
		int contentIndex=1;
		System.out.println("[Info] Begin AI_Overview Panel!--------------------------");
		driverTool.waitForElementPresent(By.cssSelector(ElementDictionary.EXPAND_ALL_AI), 30);
		Thread.sleep(10000); //Wait another 10s to make sure.
		System.out.println("[Info] AI_Overview Ready");
		
		driverTool.captureScreenshot(contentIndex+++"_Oringinal"); //GetContent
		
		driverTool.getDriver().findElement(
				By.cssSelector(ElementDictionary.EXPAND_ALL_AI)).click();
		System.out.println("[Action] Expanded All");
		
		manipulationTool.selectFilter("Access Method", "mstr288", new int[]{1,2});
		manipulationTool.selectFilter("Interface Usage", "mstr305", new int[]{1, 2, 3, 4, 5});
		manipulationTool.selectFilter("Primary Interface", "mstr293", new int[]{1, 2, 3, 4, 5});
		manipulationTool.selectFilter("Gender", "mstr214", new int[]{2,3});
		manipulationTool.selectFilter("Relationship Status", "mstr224", new int[]{2, 3, 4});
		manipulationTool.selectFilter("Education Level", "mstr229", new int[]{2, 3, 4});

		driverTool.getDriver().findElement(
				By.cssSelector(ElementDictionary.COLLAPSE_ALL_AI)).click();
		System.out.println("[Action] Collapse All");
		
		driverTool.getDriver().findElement(
				By.cssSelector(ElementDictionary.APLLY_FILTER_AI)).click();
		System.out.println("[Action] Apply Filter");
		
		/* Begin Apply Filter, wait for grey-waiting-mask appearing and disaapearing. */
		driverTool.waitForElementVisible(By.cssSelector("div.mojo-overlay-wait"), 30);
		System.out.println("[Info] Begin to Apply Filter");
		driverTool.waitForElementNotVisible(By.cssSelector("div.mojo-overlay-wait"), 30);
		System.out.println("[Info] End of Apply Filter");
		
		driverTool.captureScreenshot(contentIndex+++"_Filter"); //GetContent

		System.out.println("[Info] End for AI_Overview Panel!--------------------------");

	}

	@Override
	public void getContent(String fileName ) throws IOException {
		// TODO Auto-generated method stub
		
	}
}
