package cawang.selenium.starbelly.document;

import java.io.IOException;

import org.junit.*;
import org.openqa.selenium.*;

import cawang.selenium.starbelly.tool.DriverTool;
import cawang.selenium.starbelly.tool.ElementDictionary;
import cawang.selenium.starbelly.tool.ManipulationTool;




public class EA_Engagement implements IPanel {
	private DriverTool driverTool;
	private ManipulationTool manipulationTool;

	public EA_Engagement(DriverTool driverTool) {
		this.driverTool = driverTool;
		this.manipulationTool=new ManipulationTool(driverTool);
	}

	@Test
	public void test() throws InterruptedException {
		int contentIndex=1;
		
		System.out.println("[Info] Begin EA_Engagement Panel!------------------------------------");
		driverTool.waitForElementPresent(By.cssSelector(ElementDictionary.EXPAND_ALL_EA), 5);
		System.out.println("[Wait] Wait for 10s");
		Thread.sleep(10000);
		System.out.println("[Wait] Wait for 10s finished");
		
		driverTool.captureScreenshot(contentIndex+++"_Oringinal"); //Get Content
		
		driverTool.getDriver().findElement(By.cssSelector(ElementDictionary.EXPAND_ALL_EA)).click();
		System.out.println("[Action] Expand All");
		manipulationTool.selectFilter("Gender", "mstr146", new int[]{2,3});
		manipulationTool.selectFilter("Relationship Status", "mstr156", new int[]{3,4,5});
		manipulationTool.selectFilter("Education Level", "mstr161", new int[]{1,2,3});
		manipulationTool.selectFilter("Access Method", "mstr221", new int[]{1,2});
		manipulationTool.selectFilter("Interface Usage", "mstr211", new int[]{2,3,4,5,6,7});
		manipulationTool.selectFilter("Primary Interface", "mstr216", new int[]{3,4,5,6,7});
		manipulationTool.selectFilter("Engagement Type", "mstr201", new int[]{1,2,3,4,5,6,7});
		manipulationTool.selectFilter("Fan Type", "mstr206", new int[]{1,2});

		driverTool.getDriver().findElement(By.cssSelector(ElementDictionary.APLLY_FILTER_EA)).click();
		System.out.println("[Action] Apply Filter");
		System.out.println("[Wait] Wait for 20s");
		Thread.sleep(20000);
		System.out.println("[Wait] Wait for 20s finished");
		
		driverTool.captureScreenshot(contentIndex+++"_Filter"); //Get Content

		driverTool.getDriver().findElement(By.cssSelector(ElementDictionary.COLLAPSE_ALL_EA)).click();
		System.out.println("[Action] Collapse All");
		
		System.out.println("[Info] End of EA_Engagement Panel!------------------------------------");
	}

	@Override
	public void getContent(String fileName) {
	}

}
