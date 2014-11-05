import java.io.File;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import java.lang.Thread;


public class ExecuteMain {
               
	public static  void main(String[] args) throws Exception {
		
		WebDriver wholeDriver ;
		wholeDriver=OpenBrowser("http://10.199.56.119:8080/Dev/servlet/mstrWeb","ProjectNO") ;
		OpenSharedReport(wholeDriver);
		
		//open AI Dashboard and choose which panel to run. 
		//AIDashboard AI_Dashboard = new AIDashboard(wholeDriver);
		//AI_Dashboard.AIPanel("Overview");
		//AI_Dashboard.AIPanel("Locations");
		//AI_Dashboard.AIPanel("Engagement");
		//AI_Dashboard.AIPanel("Page Likes");
		//AI_Dashboard.AIPanel("Psychographics");
		
		//open CA Dashboard and choose which panle to run.
		//CADashboard CA_Dashboard = new CADashboard(wholeDriver);
		//CA_Dashboard.CAPanel("Overview");
		//CA_Dashboard.CAPanel("Map");
		//CA_Dashboard.CAPanel("Page Likes");
		
		
		//open EA Dashboard and choose which panel to run
		EADashboard EA_Dashboard = new EADashboard(wholeDriver);
		EA_Dashboard.EAPanel("Engagement");
		EA_Dashboard.EAPanel("Audience Overview");
		EA_Dashboard.EAPanel("Pages");
	}
	
    public static void OpenSharedReport(WebDriver driver) throws Exception
    {
    	 //select the project
        WebElement Project = driver.findElement(By.className("mstrLargeIconViewItemName"));
        Project.click();
        
        Thread.sleep(1000);//sleep(1000);
       
        //
        WebElement username = driver.findElement(By.id("Uid"));
        username.sendKeys("administrator");
        
        WebElement Password = driver.findElement(By.id("Pwd"));
        Password.sendKeys("alertadmin");
        
        WebElement Login = driver.findElement(By.className("mstrLoginButtonBarLeft"));
        Login.click();
        Thread.sleep(2000);
        
        //may be this position have problem, if we have active IServer here?
        //WebElement ContinueButton = driver.findElement(By.id("3054"));
        //ContinueButton.click();
       // Thread.sleep(2000);
        
        //select shared reports
        WebElement SharedReport = driver.findElement(By.className("mstrLink"));
        SharedReport.click();  
        Thread.sleep(1500);
    	
    }
	public static WebDriver OpenBrowser(String url,String ProjectNo){
            			
			 WebDriver driver = new FirefoxDriver();
             driver.get(url);          
             return driver;
             
            
    }
          

}



