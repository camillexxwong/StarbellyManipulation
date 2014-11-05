import java.io.File;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class AIDashboard {
	
	public WebDriver m_driver;
	public String FileLocation;
	
	AIDashboard(WebDriver dri) throws InterruptedException
	{
		m_driver = dri;
		List<WebElement> element = m_driver.findElements(By.className("mstrLink"));
		boolean loop = true;
		for (WebElement e : element){		
			if (loop)
			{
				String title = e.getAttribute("title");
				
				if (title.equalsIgnoreCase("Audience Insights"))
				{
					System.out.println("Executing AI Dashboard");
					e.click();  Thread.sleep(15000);loop = false;
				}
			}
		}		
	}
	
	public void AIFilter(String FilterName,String FilterChoice) throws InterruptedException
	{
		List<WebElement> element = m_driver.findElements(By.className("mstrmojo-portlet-title"));	
		boolean loop = true; 
		for(WebElement e : element){
			if (loop)
			{
				String strFilter = e.getText();
				if (strFilter.contains(FilterName))
				{					
					e.click(); // expand filter choice
					WebElement filter = m_driver.findElement(By.id(FilterChoice)); 
					filter.click();
					loop = false;
				}
			}
		}
		System.out.println("Setting the " + FilterName + " Succesfully!");
	}	
	
	public void ApplyFilter() throws InterruptedException
	{
		System.out.println("Applying Filter ...");
		List<WebElement> FilterParent = m_driver.findElements(By.id("mstr94"));
		for(WebElement e : FilterParent){
			e.click();
		}
		Thread.sleep(15000);
	}
	
	public void ClearAllFilter(String strFilter)
	{
		WebElement ClearFilter = m_driver.findElement(By.xpath(".//*[@id='mstr88']/div"));
		ClearFilter.click();
		
		List<WebElement> element = m_driver.findElements(By.className("mstrmojo-portlet-title"));
		//System.out.println("size: " + Integer.toString(size));	
		boolean loop = true; //int i =1;
		for(WebElement e : element){
			if (loop)
			{
				String strFiltername = e.getText();
				if (strFiltername.contains(strFilter))
				{
					e.click(); // collaspate filter choice
				}
			}
		}
		
	}
	
	public void AIPanel(String PanelName) throws InterruptedException, IOException
	{
		System.out.println("\n\nExecute " + PanelName+ " Panel "  + ".//*[@value='" + PanelName + "']");
	
		List<WebElement> Panels = m_driver.findElements(By.xpath(".//*[@value='" + PanelName + "']"));
		for (WebElement e : Panels){
			if(e.getAttribute("value").contentEquals(PanelName))
			{
				System.out.println("Click " + PanelName + " Panel");
				e.click(); 
				System.out.println("finish execute this dashboard");
				Thread.sleep(10000);
			}
		}
		
		if ( PanelName.equalsIgnoreCase("overview") )
		{
				GetAIOverviewContent("AI_" + PanelName);		
				AIFilter("Gender", "mstr2182");
				ApplyFilter();
				ClearAllFilter("Gender");
				GetAIOverviewContent("AI_" + PanelName + "_Filter");
		}
		
		if ( PanelName.equalsIgnoreCase("Locations") )
		{
				GetAILocationContent("AI_" + PanelName);
				
				AIFilter("Gender", "mstr2183");
				AIFilter("Education Level", "mstr2333");
				ApplyFilter();
				ClearAllFilter("Gender");
				ClearAllFilter("Education Level");
				GetAILocationContent("AI_" + PanelName + "_Filter");
		}
		
		if ( PanelName.equalsIgnoreCase("Engagement") )
		{
				GetAIEngagementContent("AI_"+PanelName);			
				AIFilter("Gender","mstr2182");
				AIFilter("Relationship Status","mstr2283");
				ApplyFilter();
				ClearAllFilter("Gender");
				ClearAllFilter("Relationship Status");
				ApplyFilter();
				GetAIEngagementContent("AI_"+PanelName + "_Filter");
		}
		
		if (PanelName.equalsIgnoreCase("Page Likes"))
		{
				//source panel
				WebElement changeMode = m_driver.findElement(By.xpath(".//table[@class='mstrmojo-ListBox-table']/tbody/tr/td[1]"));
				changeMode.click();
				Thread.sleep(5000);
				GetAIPageLikesContent("AI_"+PanelName);
				
				//source panel + medium Filter
				AIFilter("Gender","mstr2182");
				AIFilter("Relationship Status","mstr2283");
				ApplyFilter();
				GetAIPageLikesContent("AI_"+PanelName+"_Filter");
				
				//source panel + PageGroup
				ClearAllFilter("Gender");
				ClearAllFilter("Relationship Status");
				ApplyFilter();
				ChangePageGroup("3");
				Thread.sleep(1000);
				GetAIPageLikesContent("AI_"+PanelName + "_PageGroup");
				
				//check page category size is right or not? Temporarily not.
				//source panel + PageGroup + PageCategory
				ChagePageCategory("3");
				Thread.sleep(1000);
				GetAIPageLikesContent("AI_"+PanelName + "_PageGroup_PageCategory");	
				
				//source panel + PageGroup + PageCategory + Filter
				AIFilter("Education Level","mstr2333");
				ApplyFilter();
				GetAIPageLikesContent("AI_"+PanelName + "_PageGroup_PageCategory_Filter");
				
				//source panel +PageGroup + Filter
				ChagePageCategory("1");
				Thread.sleep(3000);
				GetAIPageLikesContent("AI_"+PanelName + "_PageGroup_Filter");
				ClearAllFilter("Education Level");
				ApplyFilter();
				Thread.sleep(3000);
		}
		
		if (PanelName.equalsIgnoreCase("Psychographics"))
		{
				System.out.println("go into psychographices.");
				WebElement changeMode = m_driver.findElement(By.xpath(".//table[@class='mstrmojo-ListBox-table']/tbody/tr/td[1]"));
				changeMode.click();
				Thread.sleep(5000);
				GetAIPsychographics("AI_"+PanelName);
				
				AIFilter("Gender","mstr2182");
				AIFilter("Relationship Status","mstr2283");
				ApplyFilter();
				GetAIPsychographics("AI_"+PanelName + "_Filter");
		}
		
	}
	
	public void ChangePageGroup (String strOption) throws InterruptedException
	{
		WebElement Selector = m_driver.findElement(By.xpath(".//*[@id='mstr343_select']/option[" + strOption + "]"));
		Selector.click();	
	}
	
	public void ChagePageCategory(String strOption)
	{
		WebElement Selector = m_driver.findElement(By.xpath(".//*[@id='mstr344_select']/option[" + strOption  + "]"));
		Selector.click();
	}
	
	public void GetAIOverviewContent(String strFilename) throws IOException
	{
		String OverviewData = null;
		WebElement Table = m_driver.findElement(By.className("r-cssDefault_W5830"));
		OverviewData = "\n\nAudience Avg: " + Table.getText();
		
		Table = m_driver.findElement(By.className("r-cssDefault_W8181"));
		OverviewData = OverviewData + "  Facebook Avg: " + Table.getText();
		
		Table = m_driver.findElement(By.className("r-cssDefault_W5775"));
		OverviewData = OverviewData + "  FRIENDS : " + Table.getText();
		
		Table = m_driver.findElement(By.className("r-cssDefault_W5774"));
		OverviewData = OverviewData +  "  PAGE LIKES : " + Table.getText();
		
		Table = m_driver.findElement(By.className("r-cssDefault_W7475"));
		OverviewData = OverviewData + "  L6/7 USERS : " + Table.getText();
		
		File file = new File("D:\\AutomationTest\\" + strFilename + ".txt");
		file.createNewFile();
		
		PrintWriter outFile = null;
		outFile = new PrintWriter(file);
		outFile.write(OverviewData);
		outFile.flush();
		outFile.close();
		//System.out.println(OverviewData);
	}
	
	public void GetAILocationContent(String strFilename) throws IOException
	{
		//we are just handle with the table data. 
		String LocationData = null;
		WebElement Table = m_driver.findElement(By.className("r-cssDefault_W7241"));
		//System.out.println(Table.getText());
		LocationData = "\n\n" + Table.getText();
		
		Table = m_driver.findElement(By.className("r-cssDefault_W7242"));
		LocationData = "\n\n" + LocationData + Table.getText();
		
		Table = m_driver.findElement(By.className("r-cssDefault_W7243"));
		LocationData = "\n\n" + LocationData + Table.getText();
		
		File file = new File("D:\\AutomationTest\\" + strFilename + ".txt");
		file.createNewFile();
		
		PrintWriter outFile = null;
		outFile = new PrintWriter(file);
		outFile.write(LocationData);
		outFile.flush();
		outFile.close();
		
	}
	
	public void GetAIEngagementContent(String strFilename) throws IOException
	{
		String EngagementData = null;
		WebElement Table = m_driver.findElement(By.className("r-cssDefault_W6794"));
		//System.out.println(Table.getText());
		EngagementData = "\n\n" + Table.getText();
		
		Table = m_driver.findElement(By.className("r-cssDefault_W6263"));
		//System.out.println(Table.getText());
		EngagementData = " " + EngagementData + Table.getText();
		
		Table = m_driver.findElement(By.className("r-cssDefault_W6252"));
		//System.out.println(Table.getText());
		EngagementData = " " + EngagementData + Table.getText();
		
		Table = m_driver.findElement(By.className("r-cssDefault_W6264"));
		//System.out.println(Table.getText());
		EngagementData = " " + EngagementData + Table.getText();
		
		Table = m_driver.findElement(By.className("r-cssDefault_W6829"));
		//System.out.println(Table.getText());
		EngagementData = " " + EngagementData + Table.getText();
		
		Table = m_driver.findElement(By.className("r-cssDefault_W6262"));
		//System.out.println(Table.getText());
		EngagementData = " " + EngagementData + Table.getText();
		
		Table = m_driver.findElement(By.className("r-cssDefault_W6266"));
		//System.out.println(Table.getText());
		EngagementData = " " + EngagementData + Table.getText();
		
		Table = m_driver.findElement(By.className("r-cssDefault_W6267"));
		//System.out.println(Table.getText());
		EngagementData = " " + EngagementData + Table.getText();
		
		File file = new File("D:\\AutomationTest\\" + strFilename + ".txt");
		file.createNewFile();
		
		PrintWriter outFile = null;
		outFile = new PrintWriter(file);
		outFile.write(EngagementData);
		outFile.flush();
		outFile.close();
	}
	
	public void GetAIPageLikesContent(String strFilename) throws IOException
	{
		String PagelikesData = null;
		WebElement Table = m_driver.findElement(By.className("r-cssDefault_W8460"));
		//System.out.println(Table.getText());
		PagelikesData = "\n\n" + Table.getText();
		
		File file = new File("D:\\AutomationTest\\" + strFilename + ".txt");
		file.createNewFile();
		
		PrintWriter outFile = null;
		outFile = new PrintWriter(file);
		outFile.write(PagelikesData);
		outFile.flush();
		outFile.close();
	}
	
	public void GetAIPsychographics(String strFilename) throws IOException
	{
		String GetAIPsychographicsData = null;
		WebElement Table = m_driver.findElement(By.className("r-cssDefault_W8462"));
		//System.out.println(Table.getText());
		GetAIPsychographicsData = "\n\n" + Table.getText();
		
		File file = new File("D:\\AutomationTest\\" + strFilename + ".txt");
		file.createNewFile();
		
		PrintWriter outFile = null;
		outFile = new PrintWriter(file);
		outFile.write(GetAIPsychographicsData);
		outFile.flush();
		outFile.close();
	}
	
	
}
