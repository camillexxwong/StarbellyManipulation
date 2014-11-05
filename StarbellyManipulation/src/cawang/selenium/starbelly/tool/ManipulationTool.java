package cawang.selenium.starbelly.tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import cawang.selenium.starbelly.document.IPanel;




public class ManipulationTool {
	private DriverTool driverTool;

	public ManipulationTool(DriverTool driverTool) {
		this.driverTool = driverTool;
	}
	
	/**
	 * @param filterName, only for easy-reading
	 * @param filterID
	 * @param selectIndex
	 */
	public void selectFilter(String filterName, String filterID, int[] selectIndex){
		for(Integer i:selectIndex){
			String filterItemID=filterID+i;
			driverTool.getDriver().findElement(By.id(filterItemID)).click();
		}
		System.out.println("[Action] Selected"+filterName+Arrays.toString(selectIndex));
	}
	
	
	/**
	 * @param filterName, only for easy-reading
	 * @param expandID, filterID to expand filter panel, EG. "mstr172"
	 * @param typeChar, the input character, EG. "U"/"Uni" for "United States"
	 * @param selectIndex, the index of target item
	 * @param itemName, the name of selected item, only for easy-reading
	 * @throws InterruptedException
	 */
	public void selectSearchTree(String filterName, String expandID, String typeChar, int selectIndex, String itemName) throws InterruptedException{
		if(!filterName.equals("Page")&&!expandID.equals("")){
		driverTool.getDriver().findElement(By.cssSelector("#"+expandID+" > div.mstrmojo-Button-text")).click();
		System.out.println("[Action] Expand"+ filterName);
		driverTool.waitForElementPresent(By.cssSelector("div.mstrmojo-ObjectItem-emptyText.truncated"), 2);
		System.out.println("[Info] Search Box Present");
		driverTool.getDriver().findElement(By.cssSelector("div.mstrmojo-ObjectItem-emptyText.truncated")).click();
		System.out.println("[Action] Click"+filterName+" Search Box");
		}
		driverTool.waitForElementPresent(By.cssSelector("input.mstrmojo-ObjectItem-input"), 2);
		driverTool.getDriver().findElement(By.cssSelector("input.mstrmojo-ObjectItem-input")).click();
		System.out.println("[Info] Search Box Input Present");
		/* clear+sndKeys=Type */
		driverTool.getDriver().findElement(By.cssSelector("input.mstrmojo-ObjectItem-input")).clear();
		driverTool.getDriver().findElement(By.cssSelector("input.mstrmojo-ObjectItem-input")).sendKeys(typeChar);
		System.out.println("[Action] Type \""+typeChar+"\"");
		Thread.sleep(1000);
		//driverTool.keyUp(By.cssSelector("input.mstrmojo-ObjectItem-input"),Keys.SHIFT);
		System.out.println("[Action] KeyUp, Triggered Search Tree Calculation");
		Thread.sleep(1000);
		driverTool.waitForElementPresent(By.xpath("//div[@class='mstrmojo-ListBase2-itemsContainer']"), 3);
		System.out.println("[Info] Search Tree appear");
		driverTool.waitForElementPresent(By.xpath("//div[@class='mstrmojo-ListBase2-itemsContainer']/div["+selectIndex+"]/div/span"),3);
		System.out.println("[Info] Search Tree Target Item appear");
		driverTool.mouseDown(By.xpath("//div[@class='mstrmojo-ListBase2-itemsContainer']/div["+selectIndex+"]/div/span"));
		System.out.println("[Action] MouseDown, Select \"United States\", index ["+selectIndex+"]");
	}

	/**
	 * @param viewName, for easy-reading only, EG. GridView, BubbleView, HeatmapVIew
	 * @param viewIndex
	 * @param extraName, sub-grid panel name only in Grid View, EG. 'Engagers Overlap'/'Page Engagers'/'Affinity'/'Overlap', etc. 
	 */
	public void switchView(String viewName, int viewIndex, String extraName){
		String indexString;
		if (viewIndex!=-1)indexString="["+viewIndex+"]";
		else indexString="";
		driverTool.getDriver().findElement(By.xpath("(//input[@value='"+extraName+"'])"+indexString)).click();
		System.out.println("//input[@value='"+extraName+"'])"+indexString);
		System.out.println("[Action] Switch View to: "+viewName+extraName);
		
	}
	public void click(By by) {
		driverTool.getDriver().findElement(by).click();
	}
	
	public void applyFilter(){
		String panelName=this.getCallerName(3);
		String[] panelNames=panelName.split("_");
		String DocumentShortName=panelNames[0];
		this.click(By.cssSelector(ElementDictionary.APLLY_FILTER_AI));
		
	}
	public String getCallerName(int callerIndex){
		//Class<IPanel> caller = Reflection.getCallerClass(callerIndex);
		//String callerFullName=caller.getName();
		String callerFullName=getCallerName(callerIndex);
		String[] callerNames=callerFullName.split("\\.");
		String callerClassName=callerNames[callerNames.length-1];
		//System.out.println(callerClassName);
		return callerClassName;
	}
	
	/**
	 * To save every manipulation step into static document, caller: this.captureScreenshot()
	 * @see DriverTool.saveCurrentDocumentAs()
	 * @param newFileName
	 * @throws InterruptedException
	 * 
	 */
	public void saveCurrentDocumentAs(String newFileName) throws InterruptedException{
		driverTool.getDriver().findElement(By.cssSelector("#mstr38 > div.mstrmojo-Button-text")).click();
		driverTool.getDriver().findElement(By.id("saveAsReportName")).clear();
		driverTool.getDriver().findElement(By.id("saveAsReportName")).sendKeys(newFileName);
		driverTool.getDriver().findElement(By.linkText("Manipulation-IMDBMPP")).click();
		driverTool.getDriver().findElement(By.id("26005")).click();
		driverTool.getDriver().findElement(By.id("RunReportFromSave")).click();
	    Thread.sleep(35000);

	}

}
