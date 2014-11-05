package cawang.selenium.starbelly.document;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.junit.Test;
import org.openqa.selenium.By;

import cawang.selenium.starbelly.tool.DriverTool;
import cawang.selenium.starbelly.tool.ElementDictionary;
import cawang.selenium.starbelly.tool.ManipulationTool;



public class AI_Locations implements IPanel  {
	private DriverTool driverTool;
	private ManipulationTool manipulationTool;
	private String imgXPath = "div.gmnoprint > div > img";
	private String panelName="AI_Locations";

	public AI_Locations(DriverTool driverTool) {
		this.driverTool = driverTool;
		this.manipulationTool=new ManipulationTool(driverTool);
	}

	@Test
	@Override
	public void test() throws InterruptedException{
		int contentIndex=1;
		System.out.println("[Info] End for AI_Locations Panel!--------------------------");
		driverTool.waitForElementPresent(By.cssSelector(imgXPath), 20);
		System.out.println("[Info] AI_Locations Ready!");
		
		driverTool.captureScreenshot(contentIndex+++"_Oringinal"); //GetContent

		driverTool.getDriver().findElement(By.cssSelector(ElementDictionary.CLEAR_ALL_AI)).click();
		System.out.println("[Action] Cleas All Filter");
		
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
		

		driverTool.getDriver().findElement(By.cssSelector(ElementDictionary.FILTER_EXPAND_USAGE_AI)).click();
		System.out.println("[Action] Expand Usage (AI User Activity)");
		manipulationTool.selectFilter("Usage (AI User Activity)", "mstr283", new int[]{0});
		driverTool.getDriver().findElement(By.cssSelector(ElementDictionary.FILTER_COLLAPSE_USAGE_AI)).click();
		System.out.println("[Action] Collapse Usage (AI User Activity)");
		
		/* Select Country Filter*/
		manipulationTool.selectSearchTree("Country", "mstr240", "u", 6, "United States");
/*		driverTool.getDriver().findElement(By.cssSelector("#mstr240 > div.mstrmojo-Button-text")).click();
		System.out.println("[Action] Expand Country");
		driverTool.waitForElementPresent(By.cssSelector("div.mstrmojo-ObjectItem-emptyText.truncated"), 2);
		System.out.println("[Info] Search Box Present");
		driverTool.getDriver().findElement(By.cssSelector("div.mstrmojo-ObjectItem-emptyText.truncated")).click();
		System.out.println("[Action] Click Country Search Box");
		driverTool.waitForElementPresent(By.cssSelector("input.mstrmojo-ObjectItem-input"), 2);
		System.out.println("[Info] Search Box Input Present");
		 clear+sndKeys=Type 
		driverTool.getDriver().findElement(By.cssSelector("input.mstrmojo-ObjectItem-input")).clear();
		driverTool.getDriver().findElement(By.cssSelector("input.mstrmojo-ObjectItem-input")).sendKeys("u");
		System.out.println("[Action] Type \"u\"");
		Thread.sleep(1000);
		//driverTool.keyUp(By.cssSelector("input.mstrmojo-ObjectItem-input"),Keys.SHIFT);
		System.out.println("[Action] KeyUp, Triggered Search Tree Calculation");
		Thread.sleep(1000);
		driverTool.waitForElementPresent(By.xpath("//div[@class='mstrmojo-ListBase2-itemsContainer']"), 3);
		System.out.println("[Info] Search Tree appear");
		driverTool.waitForElementPresent(By.xpath("//div[@class='mstrmojo-ListBase2-itemsContainer']/div[6]/div/span"),3);
		System.out.println("[Info] Search Tree Target Item appear");
		driverTool.mouseDown(By.xpath("//div[@class='mstrmojo-ListBase2-itemsContainer']/div[6]/div/span"));
		System.out.println("[Action] MouseDown, Select \"United States\", index [6]");*/
		
		manipulationTool.click(By.cssSelector(ElementDictionary.APLLY_FILTER_AI));
		System.out.println("[Action] Apply Filter");
		try {
			driverTool.waitForElementNotPresent(By.cssSelector(imgXPath), 10);
		} catch (NoSuchElementException e) {
			System.out.println("[Warning] IMG not present failed");
		}
		System.out.println("[Info] Begin to Apply Filter!");
		driverTool.waitForElementPresent(By.cssSelector(imgXPath), 20);
		System.out.println("[Info] End of Apply Filter!");
		Thread.sleep(10000);
		
		driverTool.captureScreenshot(contentIndex+++"_CountryFilter"); //GetContent
		
		driverTool.getDriver().findElement(
				By.cssSelector("#mstr239 > div.mstrmojo-Button-text")).click();
		System.out.println("[Action] Collapse Country");

	}


	@Override
	public void getContent(String fileName) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
