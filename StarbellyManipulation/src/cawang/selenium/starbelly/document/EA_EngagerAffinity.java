package cawang.selenium.starbelly.document;

import java.io.IOException;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;


import cawang.selenium.starbelly.tool.DriverTool;
import cawang.selenium.starbelly.tool.ElementDictionary;
import cawang.selenium.starbelly.tool.ManipulationTool;

public class EA_EngagerAffinity {

	private DriverTool driverTool;
	private ManipulationTool manipulationTool;

	public EA_EngagerAffinity(DriverTool driverTool) {
		this.driverTool = driverTool;
		this.manipulationTool=new ManipulationTool(driverTool);
	}

	@Test
	public void test() throws Exception {
		int contentIndex=1;
		System.out.println("[Info] Begin EA_EngagerAffinity Panel!------------------------------------");
		Thread.sleep(15000); //Wait for panel execution
		System.out.println("[Info] EA_EngagerAffinity Panel Ready");

		driverTool.captureScreenshot(contentIndex+++"_Oringinal"); //GetContent

		driverTool.getDriver().findElement(By.cssSelector(ElementDictionary.FILTER_EXPAND_COUNTRY_EA)).click();
		try {
			driverTool.getDriver().findElement(By.cssSelector(ElementDictionary.deleteSearch)).click();
		} catch (ElementNotVisibleException e1) {
			System.out.println("[Action] Not Deletable.");
		}
		driverTool.getDriver().findElement(By.cssSelector(ElementDictionary.FILTER_COLLAPSE_COUNTRY_EA)).click();
		
		System.out.println("[Action] Expand Country, Delete Search Item, Collapse Country.");
		
		driverTool.getDriver().findElement(By.cssSelector(ElementDictionary.APLLY_FILTER_EA)).click();
		System.out.println("[Action] Apply Filter");
		System.out.println("[Wait] Wait for 20s");
		Thread.sleep(15000);
		System.out.println("[Wait] Wait for 20s finished");
		
		driverTool.captureScreenshot(contentIndex+++"_Engager"); //GetContent
				
		new Select(driverTool.getDriver().findElement(By.xpath("//span/select"))).selectByVisibleText("Music");
		System.out.println("[Action] Select Music");
		
		Thread.sleep(10000); //Wait
		driverTool.captureScreenshot(contentIndex+++"_Engager_PageGroup"); //GetContent
		
		new Select(driverTool.getDriver().findElement(By.xpath("//div[7]/div[3]/span/select"))).selectByVisibleText("Musician/Band");
		System.out.println("[Action] Select Musician/Band");
		
		Thread.sleep(10000); //Wait
		driverTool.captureScreenshot(contentIndex+++"_Engager_PageCategory"); //GetContent
		
		manipulationTool.switchView("Grid View", 3, "");	
		Thread.sleep(5000); //Wait
		driverTool.captureScreenshot(contentIndex+++"_GridView"); //GetContent
		
		manipulationTool.switchView("", -1, "Engagers Overlap");
		Thread.sleep(5000);
		driverTool.captureScreenshot(contentIndex+++"_GridView_EngagersOverlap"); //GetContent
		
		manipulationTool.switchView("", -1, "Page Engagers");
		Thread.sleep(5000);
		driverTool.captureScreenshot(contentIndex+++"_GridView_PageEngagers"); //GetContent
		
		try {
			manipulationTool.selectSearchTree("Page", "", "Eminem", 3, "United States");
			Thread.sleep(5000); //Wait
			driverTool.captureScreenshot(contentIndex+++"_Engager_PageGroup_PageSearchBox"); //GetContent
		} catch (ElementNotVisibleException e) {
			System.out.println("[Warning] Search Box can't be interacted with, ElementNotVisibleException");
			contentIndex++;
		}
		
		
		manipulationTool.switchView("Heatmap View", 5, "");	
		Thread.sleep(25000); //Wait
		driverTool.getDriver().findElement(By.cssSelector(ElementDictionary.COLLAPSE_ALL_EA)).click(); //Have to move mouse to let Heatmap load
		driverTool.captureScreenshot(contentIndex+++"_Engager_PageGroup_PageSearchBox_Heatmap"); //GetContent
		
		manipulationTool.switchView("Bubble View", 4, "");	
		Thread.sleep(15000); //Wait
		driverTool.captureScreenshot(contentIndex+++"_Engager_PageGroup_PageSearchBox_Bubble"); //GetContent
		
		new Select(driverTool.getDriver().findElement(By.xpath("//div[9]/div[3]/span/select"))).selectByVisibleText("Page Likes");
		Thread.sleep(15000); //Wait
		driverTool.captureScreenshot(contentIndex+++"_Pagelikes"); //GetContent
		
		manipulationTool.switchView("Bubble View", 7, "");	
		Thread.sleep(15000); //Wait
		driverTool.captureScreenshot(contentIndex+++"_Pagelikes_Bubble"); //GetContent

		manipulationTool.switchView("Heatmap View", 8, "");	
		Thread.sleep(20000); //Wait
		driverTool.getDriver().findElement(By.cssSelector(ElementDictionary.COLLAPSE_ALL_EA)).click(); //Have to move mouse to let Heatmap load
		driverTool.captureScreenshot(contentIndex+++"_Pagelikes_Heatmap"); //GetContent

		driverTool.getDriver().findElement(By.cssSelector(ElementDictionary.CLEAR_ALL_EA)).click();
		System.out.println("[Action] Clear All Filter");
		
		Thread.sleep(15000); //Wait
		driverTool.captureScreenshot(contentIndex+++"_Pagelikes_Heatmap_NoFilter"); //GetContent
	
		manipulationTool.switchView("Bubble View", 7, "");	
		Thread.sleep(15000); //Wait
		driverTool.captureScreenshot(contentIndex+++"_Pagelikes_NoFilter_Bubble"); //GetContent
		
		manipulationTool.switchView("GridView", 6, "");	
		Thread.sleep(15000); //Wait
		driverTool.captureScreenshot(contentIndex+++"_Pagelikes_NoFilter_GridView"); //GetContent
		
		System.out.println("[Info]End of EA_EngagerAffinity Panel!------------------------------------");
	}


}
