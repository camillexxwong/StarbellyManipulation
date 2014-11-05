package cawang.selenium.starbelly.tool;

//import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;



/**
 * Provide some common functions, including login, logout, open document, open
 * panel, etc..
 * 
 * @author cawang
 * 
 */
public class DocumentTool {
	private DriverTool driverTool;

	/**
	 * @param driverTool
	 */
	public DocumentTool(DriverTool driverTool) {
		this.driverTool = driverTool;
	}

	/**
	 * Login to IServer->Wisdom Enterprise Project->Shared Reports
	 * @param iserverHost, the IServer Name/IP
	 * @throws Exception
	 */
	@Test
	public void login(String iserverHost) throws Exception {
		driverTool.getDriver().get(
				driverTool.getBaseUrl()+"starbelly/servlet/mstrWebAdmin");
		driverTool.getDriver().findElement(
				By.cssSelector("a.mstrLink > span.mstrHighlighted")).click(); // [MSTR Web]
		driverTool
				.getDriver()
				.findElement(
						By
								.xpath("(//a[contains(@href, 'mstrWeb?evt=3010&src=mstrWeb.3010&Port=0&Project=Wisdom+Enterprise&Server="
										+ iserverHost + "&loginReq=true')])[2]"))
				.click(); // [Wisdom Project @ iserver]
		if (isLoggedOut2()) {
			driverTool.getDriver().findElement(By.id("Uid")).clear();
			driverTool.getDriver().findElement(By.id("Uid")).sendKeys(
					"administrator");
			driverTool.getDriver().findElement(By.id("Pwd")).clear();
			driverTool.getDriver().findElement(By.id("Pwd")).sendKeys(
					"alertadmin");
			driverTool.getDriver().findElement(By.id("3054")).click(); // [Login Button]
			System.out.println("[Action] Logged In");
		} // Login Only When Status is Logged Out
		for (int second = 0;; second++) {
			if (second >= 60)
				System.out.println("[Timeout]");
			try {
				if (driverTool
						.isElementPresent(By
								.cssSelector("div.mstrLargeIconViewItemName > a.mstrLink"))) // [Shared Reports]
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		} // Wait for Element Present

		driverTool.getDriver().findElement(
				By.cssSelector("div.mstrLargeIconViewItemName > a.mstrLink")) // [Shared Reports]
				.click();
		System.out.println("[Action] Into Shared Reports");
	}

	private boolean isLoggedOut() {
		System.out.println("[Info]Checking Whether is Logged Out, Method1");
		if (!driverTool.isElementPresent(By.linkText("Logout"))) {
			System.out.println("[End Try");
			System.out.println("[Info] Current Status: Logged Out!");
			return true;
		} else {
			System.out.println("[Info] Current Status: Logged In!");
			return false;
		}
	}

	private boolean isLoggedOut2() {
		System.out.println("[Info]Checking Whether is Logged Out, Method2");
		if (driverTool.isElementPresent(By.id("Uid"))) {
			System.out.println("[Info] Current Status: Logged Out!");
			return true;
		} else {
			System.out.println("[Info] Current Status: Logged In!");
			return false;
		}
	}

	/**
	 * Logout IServer from WebServer
	 */
	@Test
	public void logout() {
		driverTool.getDriver().get(
				driverTool.getBaseUrl()+"starbelly/servlet/mstrWebAdmin");
		driverTool.getDriver().findElement(
				By.cssSelector("a.mstrLink > span.mstrHighlighted")).click(); // [MSTR Web]
		if (isLoggedOut()) {
			System.out.println("[Info] Already Logged Out!");
		} else {
			driverTool.getDriver().findElement(By.linkText("Logout")).click(); // [Logout Button]
			System.out.println("[Action] Logged Out");
		}
	}

	/**
	 * @param documentName, only for easy-reading
	 * @param documentID, the real Identity for Selenium to click 
	 * @throws InterruptedException
	 */
	@Test
	public void openDocument(String documentName, String documentID) throws InterruptedException {
		String documentXPath="(//a[contains(@href, 'mstrWeb?evt=2048001&src=mstrWeb.2048001&visMode=0&documentID="+documentID+"&currentViewMedia=1')])[2]";
		for (int second = 0;; second++) {
			if (second >= 5){
				System.out.println("[Timeout!!] in Open Document "+documentName);
				throw new NoSuchElementException(documentName);
			}
			try {
				if (driverTool.isElementPresent(By.xpath(documentXPath))) {
					System.out.println("[Info] Document Found!");
					/* Open Document after element found */
					driverTool.getDriver().findElement(
							By.xpath(documentXPath)).click();
					System.out.println("[Action] Opened Document: "
							+ documentName);
					break;
				}
			} catch (Exception e) {
				System.out.println("[Error]Exception in Open Document: "
						+ documentXPath);
				e.printStackTrace();
			}
			Thread.sleep(1000); // Wait for Element Present
		}
		driverTool.waitForElementPresent(By.cssSelector("div.mstrmojo-Button-text"),15); //Wait for Document Load
		System.out.println("[Info] "+documentName+" Document Load!");
	}

	/**
	 * @param panelName, the real Identity for Selenium to click 
	 * @param inputIndex, designed for CA document, can ignore
	 * @throws InterruptedException
	 */
	public void openPanel(String panelName, int inputIndex) throws InterruptedException {
		String xpath;
		if (inputIndex==-1){
			xpath="//input[@value='" + panelName + "']";
		}else{
			xpath="(//input[@value='" + panelName + "'])["+inputIndex+"]";
		}
		for (int second = 0;; second++) {
			if (second >= 2){
				System.out.println("[Timeout] in Open Panel "+panelName);
				throw new NoSuchElementException(panelName);
			}
			try {
				//System.out.println(xpath);
				if (driverTool.isElementPresent(By.xpath(xpath))) {
					driverTool.getDriver().findElement(By.xpath(xpath)).click(); //Open Panel after element found
					System.out.println("[Action] Opened Panel: " + panelName);
					break;
				}
			} catch (Exception e) {
				System.out.println("[Error]Exception in Open Panel: "
						+ panelName);
				e.printStackTrace();
			}
			// isElementPresent method has timeout wait itself (timeout variable of driverTool)
			Thread.sleep(1000);
		}

	}
	/**
	 * @param panelName
	 * @throws InterruptedException
	 */
	public void openPanel(String panelName) throws InterruptedException {
		openPanel(panelName, -1);

	}

	/**
	 * Locate to Shared Reports Directory, when current status is Document
	 * Execution
	 * @throws InterruptedException 
	 */
	public void backToSharedReports() throws InterruptedException {
		if(driverTool.getDriver().getTitle().contains("Shared Reports")){
			return;
		}
		driverTool.waitForElementPresent(By.cssSelector("div.mstrmojo-Button-text"),15);
		driverTool.getDriver().findElement(
				By.cssSelector("div.mstrmojo-Button-text")).click(); // [Home Button]
		driverTool.getDriver().findElement(
				By.cssSelector("div.mstrLargeIconViewItemName > a.mstrLink"))
				.click();
		System.out.println("[Action] Back to \"Shared Reports\"");
	}
	

	
	/**
	 * Login first before calling this function, this function begin after INTO Shared Reports 
	 * documentTool.login() function is suggested
	 * Caller: StarbellyReliability.generateStaticDocumentBaseline()
	 * @param folderName
	 * @throws InterruptedException
	 */
	@SuppressWarnings("unchecked")
	public void captureEachDocumentUnderCurrentFolder(String folderName) throws InterruptedException{
		this.openFolder(folderName);
		List<WebElement> elements=driverTool.getDriver().findElements(By.xpath("//a[contains(text(),'_')]"));
		List<String> documentNames=new ArrayList<String>(); //To collect all document names
		for(WebElement e:elements){
			String documentName=e.getText(); //Must get it before executing following steps, or StaleElementReferenceException will occurs
			System.out.println("e.getText(): "+e.getText());
			documentNames.add(documentName);
		}
		/*Use static List<String> documentNames to operate document, to prevent StaleElementReferenceException*/
		for (String documentName:documentNames){
			driverTool.getDriver().findElement(By.xpath("//a[contains(text(),'"+documentName+"')]")).click();
			Thread.sleep(25000);
			driverTool.captureScreenshot(documentName,false); //false: don't use callerName
			this.backToSharedReports(); //Back to "Current Folder"
			this.openFolder(folderName); //Back to "Current Folder"
		}
		System.out.println("captureEachDocumentUnderCurrentFolder Finished");
	}

	public void openFolder(String folderName) throws InterruptedException {
		driverTool.getDriver().findElement(
				By.xpath("//a[contains(text(),'"+folderName+"')]")).click();
		Thread.sleep(5000);
		System.out.println("[Action] Open Folder: "+ folderName);
	}
}
