package cawang.selenium.starbelly.document;

import java.io.IOException;

import org.junit.*;
import org.openqa.selenium.*;

import cawang.selenium.starbelly.tool.DriverTool;
import cawang.selenium.starbelly.tool.ElementDictionary;
import cawang.selenium.starbelly.tool.ManipulationTool;

public class EA_EngagerOverview {

	private DriverTool driverTool;
	private ManipulationTool manipulationTool;

	public EA_EngagerOverview(DriverTool driverTool) {
		this.driverTool = driverTool;
		this.manipulationTool=new ManipulationTool(driverTool);
	}

	@Test
	public void test() throws Exception {
		int contentIndex=1;
		
		System.out.println("[Info] Begin EA_EngagerOverview Panel!------------------------------------");
		System.out.println("[Wait] Wait for 20s");
		Thread.sleep(20000);
		System.out.println("[Info] EA_EngagerOverview Ready");
		
		driverTool.captureScreenshot(contentIndex+++"_Oringinal"); //Get Content
		
		driverTool.getDriver().findElement(By.cssSelector(ElementDictionary.CLEAR_ALL_EA)).click();
		System.out.println("[Action] CLear All Filter");

		System.out.println("[Wait] Wait for 15s");
		Thread.sleep(15000);
		System.out.println("[Wait] Wait for 15s finished");
		
		driverTool.captureScreenshot(contentIndex+++"_NoFilter"); //Get Content
		
		System.out.println("[Info] End of EA_EngagerOverview Panel!------------------------------------");

	}

}
