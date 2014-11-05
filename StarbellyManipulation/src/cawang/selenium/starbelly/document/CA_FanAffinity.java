package cawang.selenium.starbelly.document;

import java.io.IOException;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;

import cawang.selenium.starbelly.tool.DriverTool;
import cawang.selenium.starbelly.tool.ElementDictionary;


public class CA_FanAffinity implements IPanel{
	private DriverTool driverTool;

	public CA_FanAffinity(DriverTool driverTool) {
		this.driverTool = driverTool;
	}
	@Test
	@Override
	public void test() throws InterruptedException {
		int contentIndex=1;
		System.out.println("[Info] Begin CA_FanAffinity Panel!------------------------------------");
		driverTool.waitForElementPresent(By.xpath("(//input[@value=''])[4]"), 20);
		System.out.println("[Info] CA_FanAffinity Ready");

		driverTool.getDriver().findElement(By.xpath("(//input[@value=''])[4]")).click();
		System.out.println("[Info] Click Bubble VIew");
		System.out.println("[Info] Wait 20s"); 
		Thread.sleep(20000);
		System.out.println("[Info] Bubble View Wait Finished");
		
		driverTool.captureScreenshot(contentIndex+++"_Oringal_BubbleView"); //Get Content
		
		new Select(driverTool.getDriver().findElement(By.xpath("//span/select"))).selectByVisibleText("Companies/Products");
		System.out.println("[Action] Select \"Companies/Products\"");		
		System.out.println("[Wait] Wait for 20s");
		Thread.sleep(20000);
		System.out.println("[Wait] Wait for 20s finished");
		
		driverTool.getDriver().findElement(By.cssSelector(ElementDictionary.COLLAPSE_ALL_CA)).click(); //Have to move mouse to let Heatmap load
		driverTool.captureScreenshot(contentIndex+++"_BubbleView_PageGroup"); //Get Content
		
		new Select(driverTool.getDriver().findElement(By.xpath("//div[10]/div[3]/span/select"))).selectByVisibleText("Company");
		System.out.println("[Action] Select \"Company\"");		
		System.out.println("[Wait] Wait for 20s");
		Thread.sleep(20000);
		System.out.println("[Wait] Wait for 20s finished");
		
		driverTool.getDriver().findElement(By.cssSelector(ElementDictionary.COLLAPSE_ALL_CA)).click(); //Have to move mouse to let Heatmap load
		driverTool.captureScreenshot(contentIndex+++"_PageGroup_PageCategory"); //Get Content
		
		driverTool.getDriver().findElement(By.xpath("(//input[@value=''])[3]")).click();
		System.out.println("[Action] Switch to Grid View");
		driverTool.waitForElementPresent(By.xpath("//input[@value='Page Size']"), 5);
		System.out.println("[Info] Grid View Ready");
		driverTool.captureScreenshot(contentIndex+++"_PageGroup_PageCategory_GridView"); //Get Content

		driverTool.getDriver().findElement(By.xpath("//input[@value='Page Size']")).click();
		Thread.sleep(5000);
		System.out.println("[Action] Switch to \"Page Size\" Grid");
		driverTool.captureScreenshot(contentIndex+++"_PageGroup_PageCategory_GridView_PageSize"); //Get Content
		
		driverTool.getDriver().findElement(By.xpath("//input[@value='Fan Overlap']")).click();
		Thread.sleep(5000);
		System.out.println("[Action] Switch to \"Fan Overlap\" Grid");
		driverTool.captureScreenshot(contentIndex+++"_PageGroup_PageCategory_GridView_FanOverlap"); //Get Content
		
		driverTool.getDriver().findElement(By.xpath("//input[@value='Affinity']")).click();
		Thread.sleep(5000);
		System.out.println("[Action] Switch to \"Affinity\" Grid");
		driverTool.captureScreenshot(contentIndex+++"_PageGroup_PageCategory_GridView_Affinity"); //Get Content

		try {
			driverTool.waitForElementPresent(By.xpath("//input[@class='mstrmojo-ObjectItem-input'][1]"), 3);
			System.out.println("[Info] Search Box Present");
			driverTool.getDriver().findElement(
					By.xpath("//input[@class='mstrmojo-ObjectItem-input'][1]"))
					.click();
			System.out.println("[Action] Click Search Box");

			driverTool.getDriver().findElement(By.cssSelector("//input[@class='mstrmojo-ObjectItem-input'][1]")).clear();
			driverTool.getDriver().findElement(By.xpath("//input[@class='mstrmojo-ObjectItem-input'][1]")).sendKeys("Disney");
			System.out.println("[Action] Type \"Disney\"");
			driverTool.keyUp(Keys.ALT);
			System.out.println("[Action] KeyUp, Triggered Search Tree Calculation");
			driverTool.waitForElementPresent(By
					.xpath("//div[@class='mstrmojo-ListBase2-itemsContainer']/div[3]/div/span"), 3);
			System.out.println("[Info] Search Tree Appear");
			driverTool
			.mouseDown(By
					.xpath("//div[@class='mstrmojo-ListBase2-itemsContainer']/div[3]/div/span"));
			System.out
			.println("[Action] MouseDown, select \"Disney\", index [3]");
			System.out.println("[Wait] Wait for 5s");
			Thread.sleep(5000);
			System.out.println("[Wait] Wait for 5s finished");
			
			driverTool.captureScreenshot(contentIndex+++"_SearchBox"); //Get Content
		} catch (ElementNotVisibleException e) {
			System.out.println("[Warning] Search Box can't be interacted with, ElementNotVisibleException");
			contentIndex++;
		}

		driverTool.getDriver().findElement(By.xpath("(//input[@value=''])[5]")).click();
		driverTool.getDriver().findElement(By.cssSelector(ElementDictionary.COLLAPSE_ALL_CA)).click(); //Have to move mouse to let Heatmap load
		System.out.println("[Action] Switch to Heatmap View");
		
		System.out.println("[Wait] Wait for 20s");
		Thread.sleep(20000);
		System.out.println("[Wait] Wait for 20s finished");
		
		driverTool.getDriver().findElement(By.cssSelector(ElementDictionary.COLLAPSE_ALL_CA)).click(); //Have to move mouse to let Heatmap load
		driverTool.captureScreenshot(contentIndex+++"_HeatmapView"); //Get Content
		
		System.out.println("[Info] End for CA_FanAffinity Panel!-----------------------------------");

	}
	@Override
	public void getContent(String fileName) throws IOException {
		// TODO Auto-generated method stub

	}


}
