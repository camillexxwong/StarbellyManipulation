package cawang.selenium.starbelly.document;

import java.io.IOException;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import cawang.selenium.starbelly.tool.DriverTool;
import cawang.selenium.starbelly.tool.ElementDictionary;

public class AI_PageLikes implements IPanel  {
	private DriverTool driverTool;
	private String panelName="AI_PageLikes";

	public AI_PageLikes(DriverTool driverTool) {
		this.driverTool = driverTool;
	}

	@Test
	@Override
	public void test() throws InterruptedException {
		int contentIndex=1;
		driverTool.waitForElementPresent(By.xpath("(//input[@value=''])[2]"),30);
		System.out.println("[Info] AI_PageLikes Ready");

		driverTool.getDriver().findElement(By.xpath("(//input[@value=''])[2]")).click();
		System.out.println("[Action] Click Bubble View");
		System.out.println("[Wait] Wait for 25s");
		Thread.sleep(20000);
		System.out.println("[Wait] Wait for 25s finished");
		
		driverTool.captureScreenshot(contentIndex+++"_BubbleView_Oringinal"); //GetContent1
		
		new Select(driverTool.getDriver().findElement(By.xpath("//span/select"))).selectByVisibleText("Movies");
		System.out.println("[Action] Select \"Movies\"");		
		System.out.println("[Wait] Wait for 15s");
		Thread.sleep(15000);
		System.out.println("[Wait] Wait for 15s finished");
		
		driverTool.captureScreenshot(contentIndex+++"_BubbleView_PageGroup"); //GetContent2
		
		System.out.println("[Action] Select \"Movie\"");
		new Select(driverTool.getDriver().findElement(By.xpath("//div[7]/div[3]/span/select"))).selectByVisibleText("Movie");
		System.out.println("[Wait] Wait for 10s");
		Thread.sleep(10000);
		System.out.println("[Wait] Wait for 10s finished");
		
		driverTool.captureScreenshot(contentIndex+++"_BubbleView_PageCategory"); //GetContent3

		driverTool.getDriver().findElement(By.cssSelector("input.mstrmojo-LinkListHoriz-item.nAll")).click();
		System.out.println("[Action] Switch to Grid View");

		driverTool.waitForElementPresent(By.xpath("//input[@value='Page Size']"), 3);
		System.out.println("[Info] Grid View Ready");
		
		driverTool.getDriver().findElement(By.xpath("//input[@value='Page Size']")).click();
		Thread.sleep(5000);
		System.out.println("[Action] Switch to \"Page Size\" Grid");
		
		driverTool.captureScreenshot(contentIndex+++"_GridView_PageSize"); //GetContent4

		try {
			driverTool.getDriver().findElement(By.xpath("//input[@value='% of Page Fans Affinity']")).click();
			System.out.println("[Action] Switch to '% of Page Fans Affinity' Grid");
			driverTool.captureScreenshot(contentIndex+++"_GridView_Affinity"); //GetContent
		} catch (NoSuchElementException e) {
			System.out.println("[Warning] Cannot find \"//input[@value='% of Page Fans Affinity']\"");
		}
		try {
			driverTool.getDriver().findElement(By.xpath("//input[@value='Overlap % of Audience']")).click();
			System.out.println("[Action] Switch to 'Overlap % of Audience' Grid");
			driverTool.captureScreenshot(contentIndex+++"_GridView_Overlap"); //GetContent
		} catch (NoSuchElementException e) {
			System.out.println("[Warning] Cannot find \"//input[@value='Overlap % of Audience']\"");
		}


		try {
			driverTool.waitForElementPresent(By.cssSelector("input.mstrmojo-ObjectItem-input"), 3);
			System.out.println("[Info] Search Box Present");
			
			driverTool.getDriver().findElement(
					By.cssSelector("input.mstrmojo-ObjectItem-input"))
					.click();
			System.out.println("[Action] Click Search Box");
			
			driverTool.getDriver().findElement(By.cssSelector("input.mstrmojo-ObjectItem-input")).clear();
			driverTool.getDriver().findElement(By.cssSelector("input.mstrmojo-ObjectItem-input")).sendKeys("Ince");
			System.out.println("[Action] Type \"Ince\"");
			driverTool.keyUp(Keys.ALT);
			System.out.println("[Action] KeyUp, Triggered Search Tree Calculation");
			driverTool.waitForElementPresent(By
					.xpath("//div[@class='mstrmojo-ListBase2-itemsContainer']/div[1]/div/span"), 3);
	System.out.println("[Info] Search Tree Appear");
			driverTool
					.mouseDown(By
							.xpath("//div[@class='mstrmojo-ListBase2-itemsContainer']/div[1]/div/span"));
			System.out
					.println("[Action] MouseDown, select \"Inception\", index [1]");
			System.out.println("[Wait] Wait for 5s");
			Thread.sleep(5000);
			System.out.println("[Wait] Wait for 5s finished");
			
			driverTool.captureScreenshot(contentIndex+++"_GridView_SearchBox"); //GetContent
			
		} catch (ElementNotVisibleException e) {
			System.out.println("[Warning] Search Box can't be interacted with, ElementNotVisibleException");
			contentIndex++;
		}

		driverTool.getDriver().findElement(By.xpath("(//input[@value=''])[3]")).click();
		System.out.println("[Action] Switch to Heatmap View");
		System.out.println("[Wait] Wait for 15s");
		Thread.sleep(15000);
		System.out.println("[Wait] Wait for 15s finished");
		driverTool.getDriver().findElement(By.cssSelector(ElementDictionary.COLLAPSE_ALL_AI)).click(); //Have to move mouse to let Heatmap load
		
		driverTool.captureScreenshot(contentIndex+++"_HeatmapView"); //GetContent
		
		System.out.println("[Info] End for AI_PageLikes Panel!-----------------------------------");
	}

	@Override
	public void getContent(String fileName) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
