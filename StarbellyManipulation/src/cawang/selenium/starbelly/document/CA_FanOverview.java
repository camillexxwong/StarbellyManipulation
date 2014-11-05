package cawang.selenium.starbelly.document;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.*;
import org.openqa.selenium.By;

import cawang.selenium.starbelly.tool.DriverTool;
import cawang.selenium.starbelly.tool.ElementDictionary;
import cawang.selenium.starbelly.tool.ManipulationTool;



public class CA_FanOverview implements IPanel {
	private DriverTool driverTool;
	private ManipulationTool manipulationTool;

	public CA_FanOverview(DriverTool driverTool) {
		this.driverTool = driverTool;
		this.manipulationTool=new ManipulationTool(driverTool);
	}

	@Test
	@Override
	public void test() throws InterruptedException {
		int contentIndex=1;
		System.out.println("[Info] Begin CA_FanOverview Panel!------------------------------------");
		driverTool.waitForElementPresent(By.cssSelector(ElementDictionary.EXPAND_ALL_CA), 20);
		Thread.sleep(5000); //Wait Longer
		System.out.println("[Info] CA_Overview Ready");
		driverTool.captureScreenshot(contentIndex+++"_Oringial"); //Get Content

		driverTool.getDriver().findElement(By.cssSelector(ElementDictionary.EXPAND_ALL_CA)).click();
		System.out.println("[Action] Expand All");
		manipulationTool.selectFilter("Gender", "mstr169", new int[]{2,3});
		manipulationTool.selectFilter("Relationship Status", "mstr179", new int[]{2,3,5});
		manipulationTool.selectFilter("Education Level", "mstr184", new int[]{1,2,3});
		/*manipulationTool.selectFilter("Access Method", "mstr224", new int[]{1,2});
		manipulationTool.selectFilter("Primary Interface", "mstr229", new int[]{3,4,5,6,7});
		manipulationTool.selectFilter("Interface Usage", "mstr234", new int[]{2,3,4,5,6,7});*/
		
		driverTool.getDriver().findElement(By.cssSelector(ElementDictionary.APLLY_FILTER_CA)).click();
		System.out.println("[Action] Apply Filter");

		try {
			driverTool.waitForElementVisible(By
					.cssSelector("div.mojo-overlay-wait"), 3);
		} catch (Exception e) {
			System.out.println("[Warning] \"div.mojo-overlay-wait\" Not Found!");
		}
		System.out.println("[Info] Begin to Apply Filter");
		try {
		driverTool.waitForElementNotVisible(By
				.cssSelector("div.mojo-overlay-wait"), 20);
		} catch (Exception e) {
			System.out.println("[Warning] \"div.mojo-overlay-wait\" Not Found!");
		}
		System.out.println("[Info] End of Apply Filter");
		
		driverTool.captureScreenshot(contentIndex+++"_Filter"); //Get Content
		
		driverTool.getDriver().findElement(By.cssSelector(ElementDictionary.COLLAPSE_ALL_CA)).click();
		System.out.println("[Action] Collapse All");
		
		/* Apply the second part filter, have to expand and collapse each filter seperatly. 
		 * If Scroll Bar of Filter Panel occurs, Apply Filter button won't take affect.
		 */
		manipulationTool.click(By.cssSelector("#mstr228 > div.mstrmojo-Button-text")); //Expand \"Access Method\"
		manipulationTool.selectFilter("Access Method", "mstr224", new int[]{1,2});
		manipulationTool.click(By.cssSelector("#mstr227 > div.mstrmojo-Button-text"));
		manipulationTool.click(By.cssSelector("#mstr233 > div.mstrmojo-Button-text"));
		manipulationTool.selectFilter("Primary Interface", "mstr229", new int[]{3,4,5,6,7});
		manipulationTool.click(By.cssSelector("#mstr232 > div.mstrmojo-Button-text"));
		manipulationTool.click(By.cssSelector("#mstr238 > div.mstrmojo-Button-text"));
		manipulationTool.selectFilter("Interface Usage", "mstr234", new int[]{2,3,4,5,6,7});
		manipulationTool.click(By.cssSelector("#mstr237 > div.mstrmojo-Button-text"));
		driverTool.getDriver().findElement(By.cssSelector(ElementDictionary.APLLY_FILTER_CA)).click();
		System.out.println("[Action] Apply Filter");

		try {
			driverTool.waitForElementVisible(By
					.cssSelector("div.mojo-overlay-wait"), 3);
		} catch (Exception e) {
			System.out.println("[Warning] \"div.mojo-overlay-wait\" Not Found!");
		}
		System.out.println("[Info] Begin to Apply Filter");
		try {
		driverTool.waitForElementNotVisible(By
				.cssSelector("div.mojo-overlay-wait"), 20);
		} catch (Exception e) {
			System.out.println("[Warning] \"div.mojo-overlay-wait\" Not Found!");
		}
		System.out.println("[Info] End of Apply Filter");
		
		driverTool.captureScreenshot(contentIndex+++"_Filter_2"); //Get Content

		System.out.println("[Info] End for CA_FanOverview Panel!-----------------------------------");
	}

	@Override
	public void getContent(String FileName) throws IOException {
		// TODO Auto-generated method stub

	}

}
