package cawang.selenium.starbelly.document;

import java.io.IOException;

import org.junit.*;
import org.openqa.selenium.*;

import cawang.selenium.starbelly.tool.DriverTool;
import cawang.selenium.starbelly.tool.ElementDictionary;
import cawang.selenium.starbelly.tool.ManipulationTool;

/**
 * Execution Flow for AI_Engagement Panel
 *
 */
public class AI_Engagement implements IPanel {
	private DriverTool driverTool;
	private ManipulationTool manipulationTool;
	private String panelName="AI_Engagement";

	public AI_Engagement(DriverTool driverTool) {
		this.driverTool = driverTool;
		this.manipulationTool=new ManipulationTool(driverTool);
	}
	/**
	 * Main Execution Flow for AI_Engagement Panel
	 *
	 */
	@Test
	@Override
	public void test() throws InterruptedException {
		int contentIndex=1;
		driverTool.waitForElementPresent(By.xpath("//div[3]/div/div[2]/div"), 3);
		Thread.sleep(15000); //Wait longer to make sure
		System.out.println("[Info] AI_Engagement Execution Finished!");
		
		driverTool.captureScreenshot(contentIndex+++"_Original"); //GetContent

		driverTool.getDriver().findElement(By.cssSelector(ElementDictionary.FILTER_EXPAND_USAGE_AI)).click();
		System.out.println("[Action] Expand Usage (AI_User_Activity)");
		manipulationTool.selectFilter("Usage (AI User Activity)", "mstr283", new int[]{2});

		driverTool.getDriver().findElement(By.cssSelector(ElementDictionary.APLLY_FILTER_AI)).click();
		System.out.println("[Action] Apply Filter");		
		System.out.println("[Wait] Wait for 20s");	
		Thread.sleep(20000); // Wait for Filter Applying, 10 seconds
		System.out.println("[Wait] Wait for 20s finished");	
		
		driverTool.captureScreenshot(contentIndex+++"_Filter"); //GetContent
		
		driverTool.getDriver().findElement(	By.cssSelector(ElementDictionary.FILTER_COLLAPSE_USAGE_AI)).click();
		System.out.println("[Action] Collapse Usage (AI_User_Activity)");
		
		System.out.println("[Info] End for AI_Engagement Panel!-----------------------------------");
	}
	@Override
	public void getContent(String fileName) throws IOException {
		// TODO Auto-generated method stub
		
	}
	

}
