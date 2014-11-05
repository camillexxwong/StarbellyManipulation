package cawang.selenium.starbelly.tool;

//import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;


/**
 * Encapsulation of WebDriver, provide Driver initialize, drop, and other useful
 * functions.
 * 
 * 
 * @author cawang
 * 
 */
public class DriverTool {
	private WebDriver driver;
	private String baseUrl;  //??
	private Long timeout;  //??
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	private String screenshotFolderPath;
	int windowWidth;
	int windowHeight;
	
	/**
	 * @param screenshotFolderPath
	 * @param windowWidth
	 * @param windowHeight
	 */
	DriverTool(String screenshotFolderPath, int windowWidth, int windowHeight){
		this.screenshotFolderPath=screenshotFolderPath;
		this.windowWidth=windowWidth;
		this.windowHeight=windowHeight;
		
	}
	
	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			//System.out.println("Not Present");
			return false;
		}
	}

	public boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	public String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
	public void keyDown(Keys theKey) {
		new Actions(driver).keyDown(theKey).perform();
	}

	public void keyDown(By by, Keys theKey) {
		new Actions(driver).keyDown(driver.findElement(by), theKey).perform();
	}

	public void keyUp(Keys theKey) {
		new Actions(driver).keyUp(theKey).perform();
	}
	
	 /** 
	  *
	  * EG. driverTool.keyUp(By.cssSelector("input.mstrmojo-ObjectItem-input"),Keys.SHIFT); in AI_Locations
	  * @param by
	  * @param key
	  * Keys key, Only Modifier Keys (shift, control, alt) are available for parameters, but it's OK.
	  * Any characters for Key is OK, the real text is recognized by Type Method in Search Tree.
	  * */
	public void keyUp(By by, Keys theKey) {
		new Actions(driver).keyUp(driver.findElement(by), theKey).perform();
	} 

	public void mouseOver(By by) {
		new Actions(driver).moveToElement(driver.findElement(by)).perform();
	}
	 /** 
	  * EG. driverTool.mouseDown(By.cssSelector("input.mstrmojo-ObjectItem-input")); in AI_Locations
	  * @param by
	  * 
	  * */
	public void mouseDown(By by) {
		new Actions(driver).clickAndHold(driver.findElement(by)).perform();
	}
	
	/**
	 * Wait For Element Present Method
	 * 
	 * @param xpath
	 *             XPath of Element
	 * @param retryTimes
	 *            The maximum of retry times
	 * @throws InterruptedException
	 */
	public void waitForElementPresent(By by, int retryTimes)
			throws InterruptedException {
		for (int second = 0;; second++) {
			if (second >= retryTimes) {
				System.out.println("[Timeout!!]");
				throw new NoSuchElementException(by.toString());
			}
			try {
				//System.out.println("Try" + second);
				if (isElementPresent(by)) {
					//System.out.println("[Info] Element Present");
					break;
				}
			} catch (Exception e) {
				System.out.println("[Error] Error in finding " + by.toString());
				e.printStackTrace();
			}
			Thread.sleep(1000);
		}
	}
	
	/**
	 * Wait For Element Not Present Method
	 * 
	 * @param xpath
	 *             XPath of Element
	 * @param retryTimes
	 *            The maximum of retry times
	 * @throws InterruptedException
	 */
	public void waitForElementNotPresent(By by, int retryTimes)
			throws InterruptedException {
		for (int second = 0;; second++) {
			if (second >= retryTimes) {
				System.out.println("[Timeout!!]");
				throw new NoSuchElementException(by.toString());
				//break;
			}
			try {
				//System.out.println("Try" + second);
				//System.out.println(isElementPresent(by));
				if (!isElementPresent(by)) {
					//System.out.println("[Info] Element Not Present");
					break;
				}
			} catch (Exception e) {
				System.out.println("[Error] Error in finding " + by.toString());
				e.printStackTrace();
			}
			Thread.sleep(500);
		}
	}
	
	/**
	 * Wait For Element Visible Method
	 * 
	 * @param cssSelector
	 *             cssSelector of Element
	 * @param retryTimes
	 *            The maximum of retry times
	 * @throws InterruptedException
	 */
	public void waitForElementVisible(By by, int retryTimes)
			throws InterruptedException, NoSuchElementException {
		for (int second = 0;; second++) {
			if (second >= retryTimes) {
				System.out.println("[Timeout!!]");
				throw new NoSuchElementException(by.toString());
			}
				//System.out.println("Try" + second);
				if (getDriver().findElement(
						by).isDisplayed()) {
					//System.out.println("[Info] Element Visible");
					break;
				}
			Thread.sleep(1000);
		}
		
	}
	
	/**
	 * Wait For Element Not Visible Method
	 * 
	 * @param cssSelector
	 *             cssSelector of Element
	 * @param retryTimes
	 *            The maximum of retry times
	 * @throws InterruptedException
	 */
	public void waitForElementNotVisible(By by, int retryTimes)
			throws InterruptedException, NoSuchElementException {
		for (int second = 0;; second++) {
			if (second >= retryTimes) {
				System.out.println("[Timeout!!]");
				throw new NoSuchElementException(by.toString());
			}

			// System.out.println("Try" + second);
			if (!getDriver().findElement(by).isDisplayed()) {
				System.out.println("[Info] Element Not Visible");
				break;
			}

			Thread.sleep(1000);
		}
	}
	
	/**
	 * Capture Screenshot of Current WebDriver's Browser Window
	 * Will get Entire page Screenshot, even if there are scroll bars
	 * @param filename
	 * @throws InterruptedException 
	 */
	public void captureScreenshot(String filename) throws InterruptedException {
		String panelName=this.getCallerName(3);
		filename=panelName+"_"+filename;
		captureScreenshot(filename,false);
	    /*If want to save every manipulation step into static document, please un-commment the following statement*/
	    //this.saveCurrentDocumentAs(filename);
	    
	}
	
	/**
	 * Capture Screenshot of Current WebDriver's Browser Window
	 * Will get Entire page Screenshot, even if there are scroll bars
	 * @param filename
	 * @throws InterruptedException 
	 */
	public void captureScreenshot(String filename, boolean useCallerName) throws InterruptedException {
		if(useCallerName==false){
			this.resizeBrowserWindow(this.windowWidth,this.windowHeight);
			System.out.println("[Info] Begin to Capture Screen");
			String dir_name = screenshotFolderPath;  
			if (!(new File(dir_name).isDirectory())) {
				new File(dir_name).mkdir(); 
			}

			try {
				File source_file = ((TakesScreenshot)this.getDriver()).getScreenshotAs(OutputType.FILE);  // Execute Screenshot, saved in temp directory
				FileUtils.copyFile(source_file, new File(dir_name + File.separator + filename + ".png"));  //Copy image to folderPath
				System.out.println("[Action] Saved Screenshot to "+dir_name + File.separator + filename + ".png");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else captureScreenshot(filename);


	}
	
    /**
     * Resize Firefox Browser Window, to make all document content visible;
     * Mainly for screenShot() Function;
     * The constants Should be adjusted according to local machine resolution and document visual size.
     * @param driver
     */
	public void resizeBrowserWindow(int width, int height) { 
		final JavascriptExecutor js = (JavascriptExecutor) this.getDriver();
		js.executeScript("window.moveTo(0,0);");
		js.executeScript("window.resizeTo("+width+","+height+");"); 
	} 
	
	
	/**
	 * Get the Class short name of Caller Class
	 * @param callerIndex, the calling hierarchy index. 1: self; 2:direct caller; 3:caller of 2;...
	 * @return
	 */
	@SuppressWarnings("unchecked")
/*	public String getCallerName(int callerIndex){
		Class<IPanel> caller = Reflection.getCallerClass(callerIndex);
		String callerFullName=caller.getName();
		String[] callerNames=callerFullName.split("\\.");
		String callerClassName=callerNames[callerNames.length-1];
		//System.out.println(callerClassName);
		return callerClassName;
	}*/
	
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
	 * Initialize Web Driver
	 * 
	 * @param baseUrl 
	 * The Base URL
	 * @param timeout 
	 * The Timeout Duration, in seconds
	 */
	public void initDriver(String baseUrl,long timeout) {
		driver=new FirefoxDriver();
		this.baseUrl=baseUrl;
		this.timeout=timeout;
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS); //Set Timeout duration in seconds.
		System.out.println("[Info] Initialized Driver. Base URL: "+baseUrl+"; Time Out Duration: "+timeout+" seconds.");
	}
	/**
	 * Drop Web Driver
	 */
	public void tearDownDriver() {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			System.out.println("[Error] "+verificationErrorString);
		}
		System.out.println("[Info] Dropped Driver!");
	}

	public WebDriver getDriver() {
		return this.driver;
	}

	public String getBaseUrl() {
		return this.baseUrl;
	}
	
	public Long getTimeout() {
		return this.timeout;
	}
	
	public void setScreenshotFolderPath(String newPath) {
		this.screenshotFolderPath=newPath;
	}
	
	/**
	 * To save every manipulation step into static document, caller: this.captureScreenshot()
	 * @see ManipulationTool.saveCurrentDocumentAs()
	 * @param newFileName
	 * @throws InterruptedException
	 * 
	 */
	public void saveCurrentDocumentAs(String newFileName) throws InterruptedException{
		this.getDriver().findElement(By.cssSelector("#mstr38 > div.mstrmojo-Button-text")).click();
		this.getDriver().findElement(By.id("saveAsReportName")).clear();
		this.getDriver().findElement(By.id("saveAsReportName")).sendKeys(newFileName);
	    driver.findElement(By.linkText("Manipulation-IMDBMPP")).click();
	    driver.findElement(By.id("26005")).click();
	    driver.findElement(By.id("RunReportFromSave")).click();
	    Thread.sleep(35000);

	}
}
