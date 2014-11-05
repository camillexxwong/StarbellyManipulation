package cawang.selenium.starbelly.document;

import java.io.IOException;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;

import cawang.selenium.starbelly.tool.DriverTool;
import cawang.selenium.starbelly.tool.ElementDictionary;
import cawang.selenium.starbelly.tool.ManipulationTool;




public class CA_Locations implements IPanel {
	private DriverTool driverTool;
	private ManipulationTool manipulationTool;
	private String imgXPath = "div.gmnoprint > div > img";

	public CA_Locations(DriverTool driverTool) {
		this.driverTool = driverTool;
		this.manipulationTool=new ManipulationTool(driverTool);
	}

	@Test
	@Override
	public void test() throws InterruptedException {
		int contentIndex=1;
		System.out.println("[Info] Begin CA_Locations Panel!------------------------------------");
		driverTool.waitForElementPresent(By.cssSelector("div.gmnoprint > div > img"), 20);
		System.out.println("[Info] CA_Locations Ready");
		
		driverTool.captureScreenshot(contentIndex+++"_Oringial"); //Get Content
		
		manipulationTool.selectSearchTree("Country", "mstr195", "Uni", 3, "United States");

		//manipulationTool.selectSearchTree("City", "mstr209", "Cincin", 3, "Cincinati");
		
		/*JavascriptExecutor j= (JavascriptExecutor)driverTool.getDriver();
		j.executeScript("mstrmojo.dom.captureDomEvent('mstr244','keyup', self, arguments[0]);");*/
		
		/*driverTool.getDriver().findElement(By.cssSelector("#mstr209 > div.mstrmojo-Button-text")).click();
		System.out.println("[Action] Expand City");
		driverTool.waitForElementPresent(By.cssSelector("div.mstrmojo-ObjectItem-emptyText.truncated"), 3);
		System.out.println("[Info] Search Box Ready");
		//driverTool.getDriver().findElement(By.cssSelector("div.mstrmojo-ObjectItem-emptyText.truncated")).click();
		System.out.println("[Action] Click Search Box");
		//driverTool.waitForElementPresent(By.cssSelector("input.mstrmojo-ObjectItem-input"),3);
		System.out.println("[Info] Search Box Input Ready");
		driverTool.getDriver().findElement(By.xpath("//input[@type='text'])[6]")).clear();
		driverTool.getDriver().findElement(By.xpath("//input[@type='text'])[6]")).sendKeys("Cincin");
		System.out.println("[Action] Type \"Cincin\"");
		driverTool.keyUp(By.xpath("//input[@type='text'])[6]"), Keys.ALT);
		System.out.println("[Action] KeyUp, Triggered Search Tree Calculation");
		driverTool.waitForElementPresent(By.xpath("//div[@class='mstrmojo-ListBase2-itemsContainer']/div[3]/div/span"), 3);
		System.out.println("[Info] Search Tree Appear");
		driverTool.mouseDown(By.xpath("//div[@class='mstrmojo-ListBase2-itemsContainer']/div[3]/div/span"));
		System.out.println("[Action] MouseDown, Select \"Cincinati\", index [3]");*/
		
		//manipulationTool.selectFilter("Gender", "mstr169", new int[]{2,3});
		
		//manipulationTool.applyFilter();
		driverTool.getDriver().findElement(By.cssSelector(ElementDictionary.APLLY_FILTER_CA)).click();
		System.out.println("[Action] Apply Filter");
		
		try {
			driverTool.waitForElementNotPresent(By.cssSelector(imgXPath), 10);
		} catch (NoSuchElementException e) {
			System.out.println("[Warning] IMG not present failed");
		}
		System.out.println("[Info] Begin to Apply Filter!");
		driverTool.waitForElementPresent(By.cssSelector(imgXPath), 20);
		System.out.println("[Info] End of Apply Filter!");

		driverTool.captureScreenshot(contentIndex+++"_Filter"); //Get Content
		
		driverTool.getDriver().findElement(By.cssSelector("#mstr194 > div.mstrmojo-Button-text")).click();
		System.out.println("[Action] Collapse Country");
	/*	driverTool.getDriver().findElement(By.cssSelector("#mstr208 > div.mstrmojo-Button-text")).click();
		System.out.println("[Action] Collapse City");*/
		
		driverTool.getDriver().findElement(By.cssSelector("#mstr105 > div.mstrmojo-Button-text")).click();
		System.out.println("[Action] Clear All Filter");
		
		try {
			driverTool.waitForElementNotPresent(By.cssSelector(imgXPath), 10);
		} catch (NoSuchElementException e) {
			System.out.println("[Warning] IMG not present failed");
		}
		System.out.println("[Info] Begin to Apply Filter!");
		driverTool.waitForElementPresent(By.cssSelector(imgXPath), 20);
		System.out.println("[Info] End of Apply Filter!");
		
		driverTool.captureScreenshot(contentIndex+++"_NoFilter"); //Get Content

		System.out.println("[Info] End for CA_Locations Panel!-----------------------------------");

	}

	@Override
	public void getContent(String fileName) throws IOException {
		// TODO Auto-generated method stub

	}



}
