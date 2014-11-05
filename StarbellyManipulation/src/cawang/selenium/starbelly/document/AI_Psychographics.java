package cawang.selenium.starbelly.document;

import java.io.IOException;

import org.junit.*;
import org.openqa.selenium.*;

import cawang.selenium.starbelly.tool.DriverTool;
import cawang.selenium.starbelly.tool.ElementDictionary;
import cawang.selenium.starbelly.tool.ManipulationTool;

public class AI_Psychographics implements IPanel  {

	private DriverTool driverTool;
	private String panelName="AI_Psychograohics";
	/**
	 * check if AI_Psychographics is opened after AI_PageLikes Opened, if so,
	 * switch view button's index will change.
	 */
	private boolean isPagelikesOpenFirst;
	private Integer switchViewBase;
	private ManipulationTool manipulationTool;

	public AI_Psychographics(DriverTool driverTool) {
		this.driverTool = driverTool;
		manipulationTool=new ManipulationTool(driverTool);
	}

	@Test
	@Override
	public void test() throws InterruptedException {
		int contentIndex=1;
		for (int second = 0;; second++) {
			if (second >= 3) {
				System.out.println("[Timeout!!]");
				throw new NoSuchElementException("//input[@value=''])[2]");
			}
			try {
				// System.out.println("Try"+second);
				if (driverTool.isElementPresent(By
						.xpath("(//input[@value=''])[5]"))) {
					isPagelikesOpenFirst = true;
					switchViewBase = 4;
					System.out
							.println("[Info] AI_Psychographics Load Finished");
					break;
				} else if (driverTool.isElementPresent(By
						.xpath("(//input[@value=''])[2]"))) {
					isPagelikesOpenFirst = false;
					switchViewBase = 1;
					System.out
							.println("[Info] AI_Psychographics Load Finished");
					break;
				}
			} catch (Exception e) {
				System.out.println("[Error] Error in AI_Psychographics");
				e.printStackTrace();
			}
			Thread.sleep(1000);
			System.out.println("[Next loop!!]");
		} // Wait for AI_Psychographics Load finished, and initialize Switch View Button ID
		
		
		driverTool.getDriver().findElement(
				By.xpath("(//input[@value=''])[" + (switchViewBase + 1) + "]"))
				.click();
		System.out.println("[Action] Click Bubble View");
		System.out.println("[Wait] Wait for 15s");
		Thread.sleep(15000);
		System.out.println("[Wait] Wait for 15s finished");
		
		driverTool.captureScreenshot(contentIndex+++"_BubbleView_Originial"); //GetContent1
		
		manipulationTool.switchView("Heatmap View", switchViewBase + 2, "");
		System.out.println("[Wait] Wait for 15s");
		Thread.sleep(15000);
		driverTool.getDriver().findElement(By.cssSelector(ElementDictionary.COLLAPSE_ALL_AI)).click(); //Have to move mouse to let Heatmap load
		System.out.println("[Wait] Wait for 15s finished");
		
		driverTool.captureScreenshot(contentIndex+++"_HeatmapView_Originial"); //GetContent2
		
		manipulationTool.switchView("Grid View", switchViewBase + 0, "");

		driverTool.waitForElementPresent(By
				.xpath("//input[@value='Psychographic Size']"), 3); // Wait for Grid View finished
		System.out.println("[Info] Grid View Execution Finished");
		
		Thread.sleep(3000); // Wait longer to make sure
		driverTool.captureScreenshot(contentIndex+++"_GridView_Originial"); //GetContent3
		
		driverTool.getDriver().findElement(By.xpath("//input[@value='Psychographic Size']")).click();
		Thread.sleep(5000);
		System.out.println("[Action] Switch to 'Psychographic Size' Grid");
		
		driverTool.captureScreenshot(contentIndex+++"_GridView_PsychographicSize"); //GetContent4
		
		try {
			driverTool.getDriver().findElement(	By.xpath("//input[@value='% of Psychographic             Affinity']"))
					.click();
			System.out.println("[Action] Switch to '% of Psychographic Affinity' Grid");
			driverTool.captureScreenshot(contentIndex+++"_GridView_Affinity"); //GetContent
		} catch (NoSuchElementException e) {
			System.out.println("[Warning] Cannot find \"//input[@value='% of Psychographic Affinity']\"");
		}

		System.out.println("[Info] End for AI_Psychographics Panel!-----------------------------------");
	}

	@Override
	public void getContent(String fileName) throws IOException {
		// TODO Auto-generated method stub
		
	}
}
