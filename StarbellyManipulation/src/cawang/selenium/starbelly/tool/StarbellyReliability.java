package cawang.selenium.starbelly.tool;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cawang.selenium.starbelly.document.*;




/**
 * Please connect all the IServers to be tested in one flow in WebServer first.
 * 
 * @author cawang
 *
 */
public class StarbellyReliability {
	/* Variables for WebDriver Initialization*/
	static String BASE_URL="http://localhost:8080/";
	static Long TIMEOUT=10L;
	
	/* Variables for DriverTool Initialization*/
	static int windowWidth=1280;
	static int windowHeight=1024;
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
    static String currentTime = sdf.format(new Date()); 
    static String screenshotFolderPath="E:\\screenshot\\"+currentTime;
    
	static String screenshotFolderPathSingle=screenshotFolderPath+"_650_Single";
	static String screenshotFolderPath1Plus1=screenshotFolderPath+"_650_1p1";
	static String screenshotFolderPath1Plus4=screenshotFolderPath+"_650_1p4";
	static String screenshotBaselinePath_Single="E:\\screenshot\\baseline_sg";
	static String screenshotBaselinePath_1Plus4="E:\\screenshot\\baseline_1p4";
	static String ImageCompareBatch="e:\\Workspace\\workspace-selenium\\batch\\callImageCompare.bat";

	/*Declare and Initialize Driver*/	
	static DriverTool driverTool=new DriverTool(screenshotFolderPath,windowWidth,windowHeight);
	static DocumentTool documentTool=new DocumentTool(driverTool);
	
	/*The following parameters are parameters specific for IServer and Documents*/
	static String ISERVER_HOST_SINGLE="10.199.57.6";
	static String ISERVER_HOST_1PLUS1="10.199.57.7";
	static String ISERVER_HOST_1PLUS4="10.199.56.137";
	

	static String AI_DOCUMENT ="Audience Insights - Reliability";
	static String AI_DOCUMENT_ID ="6D202938409D603BF79D1494F79720DC";
	static String CA_DOCUMENT ="Connection Analysis - Reliability";
	static String CA_DOCUMENT_ID="04C941B34D4E89D8934FA5B58F1EF9BD";
	static String EA_DOCUMENT ="Engagement Analysis - Reliability";
	static String EA_DOCUMENT_ID="F5F458B04F0091B3409EBF9F8C4F9150";
	
	static String AI_OVERVIEW="Overview";
	static String AI_LOCATIONS="Locations";
	static String AI_ENGAGEMENT="Engagement";
	static String AI_PAGELIKES="Page Likes";
	static String AI_PSYCHOGRAOHICS="Psychographics";
	
	static String CA_FANOVERVIEW="Fan Overview";
	static String CA_LOCATIONS="Locations";
	static String CA_FANAFFINITY="Fan Affinity";

	
	static String EA_ENGAGEMENT="Engagement";
	static String EA_ENGAGEROVERVIEW="Engager Overview";
	static String EA_LOCATIONS="Locations";
	static String EA_ENGAGERAFFINITY="Engager Affinity";
	
	
	static AI_Overview oAI_Overview;
	static AI_Locations oAI_Locations;
	static AI_Engagement oAI_Engagement;
	static AI_PageLikes oAI_PageLikes;
	static AI_Psychographics oAI_Psychographics;
	static CA_FanOverview oCA_FanOverview;
	static CA_Locations oCA_Locations;
	static CA_FanAffinity oCA_FanAffinity;

	static EA_Engagement oEA_Engagement;
	static EA_EngagerOverview oEA_EngagerOverview;
	static EA_Locations oEA_Locations;
	static EA_EngagerAffinity oEA_EngagerAffinity;



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		initPanels(driverTool);
		driverTool.initDriver(BASE_URL, TIMEOUT);
		try {
			execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		driverTool.tearDownDriver();
	}

	/**
	 * Execute main test flow
	 */
	private static void execute() throws Exception {
		/*Test Single*/
		documentTool.login(ISERVER_HOST_SINGLE); //Tested
 
		//generateStaticDocumentBaseline("Manipulation-IMDBMPP"); //generate Baseline using Static Document 
		
		driverTool.setScreenshotFolderPath(screenshotFolderPathSingle);
		executeAllPanel();
		documentTool.logout(); //Tested!
		ResultCompare.compareImageResults(ImageCompareBatch, screenshotBaselinePath_Single, screenshotFolderPathSingle);
		
		/*Test 1+1*/
		//documentTool.login(ISERVER_HOST_1PLUS1);
		//driverTool.setScreenshotFolderPath(screenshotFolderPath1Plus1);
		//executeAllPanel();
		//documentTool.logout();
		//ResultCompare.compareImageResults(ImageCompareBatch, screenshotBaselinePath, screenshotFolderPath1Plus1);
		
		/*Test 1+4*/
		documentTool.login(ISERVER_HOST_1PLUS4);
		driverTool.setScreenshotFolderPath(screenshotFolderPath1Plus4);
		executeAllPanel();
		documentTool.logout();
		ResultCompare.compareImageResults(ImageCompareBatch, screenshotBaselinePath_1Plus4, screenshotFolderPath1Plus4);
		

		
	}
	
	/**
	 * Execute panel test flow
	 */
	private static void executeAllPanel() throws Exception{

		/*AI Document*/
		documentTool.openDocument(AI_DOCUMENT, AI_DOCUMENT_ID); //Tested
		
		documentTool.openPanel(AI_OVERVIEW); //Tested
		oAI_Overview.test(); //Tested
		documentTool.openPanel(AI_ENGAGEMENT); //Tested
		oAI_Engagement.test(); //Tested, use Thread.sleep() to control filter applying time
		documentTool.openPanel(AI_LOCATIONS); //Tested
		oAI_Locations.test(); //Tested
		documentTool.openPanel(AI_PAGELIKES); //Tested
		oAI_PageLikes.test(); //Tested, Search Box NOT OK.
		documentTool.openPanel(AI_PSYCHOGRAOHICS); //Tested
		oAI_Psychographics.test(); //Tested
		
				
		/*CA Document*/
		documentTool.backToSharedReports(); //Tested
		documentTool.openDocument(CA_DOCUMENT, CA_DOCUMENT_ID); //Tested
		
		documentTool.openPanel(CA_FANOVERVIEW,2); //Tested
		oCA_FanOverview.test(); //NOT PASSED, Apply Filter NOT OK. ->Passed
		documentTool.openPanel(CA_LOCATIONS,2); //Tested
		oCA_Locations.test(); //Tested, City Search Box NOT OK.
		documentTool.openPanel(CA_FANAFFINITY,2); //Tested
		oCA_FanAffinity.test(); //Tested, Search Box NOT OK.

		

		/*EA Document*/
		documentTool.backToSharedReports(); //Tested
		documentTool.openDocument(EA_DOCUMENT, EA_DOCUMENT_ID); //Tested
		
		documentTool.openPanel(EA_ENGAGEMENT); //Tested
		oEA_Engagement.test(); //Tested
		documentTool.openPanel(EA_ENGAGEROVERVIEW); //Tested
		oEA_EngagerOverview.test(); //Tested
		documentTool.openPanel(EA_LOCATIONS); //Tested
		oEA_Locations.test(); //Tested
		documentTool.openPanel(EA_ENGAGERAFFINITY); //Tested
		oEA_EngagerAffinity.test(); //Tested, Search Box NOT OK.
	}
	
	/**
	 * Login first before calling this function, this function begin after INTO Shared Reports 
	 * documentTool.login() function is suggested
	 * @param folderName
	 * @throws InterruptedException
	 */
	public static void generateStaticDocumentBaseline (String folderName) throws InterruptedException{
		
		driverTool.setScreenshotFolderPath(screenshotFolderPathSingle+"_Static");
		documentTool.captureEachDocumentUnderCurrentFolder(folderName);
	}
	
	/**
	 * Initialize Panel Instances
	 */
	private static void initPanels(DriverTool driverTool){
		
		oAI_Overview = new AI_Overview(driverTool);
		oAI_Locations = new AI_Locations(driverTool);
		oAI_Engagement = new AI_Engagement(driverTool);
		oAI_PageLikes = new AI_PageLikes(driverTool);
		oAI_Psychographics = new AI_Psychographics(driverTool);
		
		oCA_FanOverview = new CA_FanOverview(driverTool);
		oCA_Locations = new CA_Locations(driverTool);
		oCA_FanAffinity = new CA_FanAffinity(driverTool);

		oEA_Engagement = new EA_Engagement(driverTool);
		oEA_EngagerOverview = new EA_EngagerOverview(driverTool);
		oEA_Locations = new EA_Locations(driverTool);
		oEA_EngagerAffinity = new EA_EngagerAffinity(driverTool);
	}


}
