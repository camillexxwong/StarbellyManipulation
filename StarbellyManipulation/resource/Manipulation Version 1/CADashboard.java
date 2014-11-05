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
public class CADashboard {
	
	public WebDriver m_driver;
	
	CADashboard(WebDriver dri) throws InterruptedException
	{
		m_driver = dri;
		List<WebElement> element = m_driver.findElements(By.className("mstrLink"));
		boolean loop = true;
		for (WebElement e : element){		
			if (loop)
			{
				String title = e.getAttribute("title");
				
				if (title.equalsIgnoreCase("Connection Analysis_Facebook"))
				{
					System.out.println("Executing CA Dashboard");
					e.click();  Thread.sleep(10000);loop = false;
				}
			}
		}		
	}
	
	public void CAPanel(String PanelName) throws InterruptedException, IOException
	{
		List<WebElement> Panels = m_driver.findElements(By.xpath(".//*[@value='" + PanelName + "']"));
		boolean loop = true;
		for (WebElement e : Panels){
		if(loop)
			{
				if(e.getAttribute("value").contentEquals(PanelName))
				{
					//System.out.println("Click " + PanelName + " Panel");
					e.click(); 
					//System.out.println("finish execute this dashboard");
					Thread.sleep(5000); loop = false;
				}
			}
		}
		
		if ( PanelName.equalsIgnoreCase("Overview") )
		{
				//ClearAllFilter("Gender");//collapse the gender choice.
				//WebElement element = m_driver.findElement(By.className("mstrmojo-portlet-title"));	
				//element.click();
				GetCAOverviewContent("CA_" + PanelName);		
				
				CAFilter("Relationship Status", "mstr1633");
				ApplyFilter();
				GetCAOverviewContent("CA_" + PanelName + "_Filter");
				ClearAllFilter("Relationship Status");
				ApplyFilter();
		}
		
		if (PanelName.equalsIgnoreCase("Map"))
		{
			GetCAMapContent("CA_" + PanelName);
			
			CAFilter("Access Method","mstr2082");
			ApplyFilter();
			GetCAMapContent("CA_" + PanelName + "_Filter");
			ClearAllFilter("Access Method");
			ApplyFilter();
		}
		
		if(PanelName.equalsIgnoreCase("Page Likes"))
		{
			WebElement changeMode = m_driver.findElement(By.xpath(".//table[@class='mstrmojo-ListBox-table']/tbody/tr/td[1]"));
			changeMode.click();
			Thread.sleep(5000);
			GetCAPageLikesContent("CA_" + PanelName);
		}
		
	}
	
	public void CAFilter(String strFilter, String strValue)
	{
		//System.out.println("setting filter\n");
		List<WebElement> element = m_driver.findElements(By.className("mstrmojo-portlet-title"));	
		boolean loop = true; 
		for(WebElement e : element){
			if (loop)
			{
				String strFiltername = e.getText();
				if (strFiltername.contains(strFilter))
				{	
					  System.out.println("expand the filter choice\n");
					  e.click();// expand filter choice				
					  WebElement filter = m_driver.findElement(By.id(strValue)); 
					  filter.click();
					  loop = false;
				}
			}
		}
		System.out.println("Setting the " + strFilter + " Succesfully!");
	}
	
	
	public void ClearAllFilter(String strFilter)
	{
		WebElement ClearFilter = m_driver.findElement(By.xpath(".//*[@id='mstr102']/div"));
		ClearFilter.click();
		
		List<WebElement> element = m_driver.findElements(By.className("mstrmojo-portlet-title"));	
		boolean loop = true; 
		for(WebElement e : element){
			if (loop)
			{
				String strFiltername = e.getText();
				//System.out.println(strFiltername + "\n");
				
				if (strFiltername.contains(strFilter))
				{
					System.out.println("get it!!");
					e.click(); // collaspate filter choice
					loop = false;
				}
			}
		}
		
	}
	
	public void ApplyFilter() throws InterruptedException
	{
		System.out.println("Click Apply Button...");
		List<WebElement> FilterParent = m_driver.findElements(By.id("mstr108"));
		for(WebElement e : FilterParent){
			e.click();
		}
		Thread.sleep(5000);
	}
	
	public void GetCAOverviewContent(String strFilePath)throws IOException
	{
		String CAContent = null;
		
		WebElement Table = m_driver.findElement(By.className("r-cssDefault_W369"));
		CAContent = "\n" + Table.getText();
		
		Table = m_driver.findElement(By.className("r-cssDefault_W6256"));
		CAContent = CAContent + "\n" + Table.getText();
		
		Table = m_driver.findElement(By.className("r-cssDefault_W5830"));
		CAContent = CAContent + "\n" + Table.getText();
		
		Table = m_driver.findElement(By.className("r-cssDefault_W5775"));
		CAContent = CAContent + "\n" + Table.getText();
		
		Table = m_driver.findElement(By.className("r-cssDefault_W5774"));
		CAContent = CAContent + "\n" + Table.getText();
		
		File file = new File("D:\\AutomationTest\\" + strFilePath + ".txt");
		file.createNewFile();
		
		PrintWriter outFile = null;
		outFile = new PrintWriter(file);
		outFile.write(CAContent);
		outFile.flush();
		outFile.close();	
	}
	
	public void GetCAMapContent(String strFilePath)throws IOException
	{
		String CAContent = null;
		
		WebElement Table = m_driver.findElement(By.className("r-cssDefault_W369"));
		CAContent = "\n" + Table.getText();
		
		Table = m_driver.findElement(By.className("r-cssDefault_W6256"));
		CAContent = CAContent + "\n" + Table.getText();
		
		File file = new File("D:\\AutomationTest\\" + strFilePath + ".txt");
		file.createNewFile();
		
		PrintWriter outFile = null;
		outFile = new PrintWriter(file);
		outFile.write(CAContent);
		outFile.flush();
		outFile.close();	
	}
	
	public void GetCAPageLikesContent(String strFilePath) throws IOException
	{
String CAContent = null;
		
		WebElement Table = m_driver.findElement(By.className("r-cssDefault_W6659"));
		CAContent = "\n" + Table.getText();
		
		Table = m_driver.findElement(By.className("r-cssDefault_W6256"));
		CAContent = CAContent + "\n" + Table.getText();
		
		File file = new File("D:\\AutomationTest\\" + strFilePath + ".txt");
		file.createNewFile();
		
		PrintWriter outFile = null;
		outFile = new PrintWriter(file);
		outFile.write(CAContent);
		outFile.flush();
		outFile.close();	
		
	}
	
}
