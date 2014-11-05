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

public class EADashboard {
	
	public WebDriver m_driver;
	
	EADashboard(WebDriver dri) throws InterruptedException
	{
		m_driver = dri;
		List<WebElement> element = m_driver.findElements(By.className("mstrLink"));
		boolean loop = true;
		for (WebElement e : element){		
			if (loop)
			{
				String title = e.getAttribute("title");
				
				if (title.equalsIgnoreCase("Engagement Analysis-Facebook"))
				{
					System.out.println("Executing EA Dashboard");
					e.click();  Thread.sleep(10000);loop = false;
				}
			}
		}		
	}
	
	public void EAPanel(String PanelName) throws InterruptedException, IOException
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
		
		if(PanelName.equalsIgnoreCase("Engagement"))
		{
			GetEngagementContent("EA_"+PanelName);
			
			EAFilter("Engagement Type","mstr2061");
			ApplyFilter();
			GetEngagementContent("EA_"+PanelName+"_EATypeFilter");
			ClearALLFilter();
			CollapseFilter("Engagement Type(EA Only)");
			ApplyFilter();
			
			EAFilter("Fan Type","mstr2111");
			ApplyFilter();
			GetEngagementContent("EA_"+PanelName+"_EAFanTypeFilter");
			ClearALLFilter();
			CollapseFilter("Fan Type(EA Only)");
			ApplyFilter();
			
			EAFilter("Gender","mstr1521");
			EAFilter("Relationship Status","mstr1611");
			ApplyFilter();
			GetEngagementContent("EA_" + PanelName + "_Filter");
			
			EAFilter("Engagement Type","mstr2061");
			ApplyFilter();
			GetEngagementContent("EA_" + PanelName + "_Filter_EA");
			EAFilter("Fan Type", "mstr2111");
			ApplyFilter();
			GetEngagementContent("EA_" + PanelName + "+Filter_EA_FanType");
			ClearALLFilter();
			CollapseFilter("Gender");
			CollapseFilter("Relationship Status");
			CollapseFilter("Engagement Type");
			CollapseFilter("Fan Type");
			ApplyFilter();
			
		}
		
		if(PanelName.equalsIgnoreCase("Audience Overview"))
		{
			GetOverviewContent("EA_" + PanelName);
			
			EAFilter("Relationship Status","mstr1613");
			ApplyFilter();
			GetOverviewContent("EA_" + PanelName + "_Filter");
			
			EAFilter("Engagement Type","mstr2061");
			ApplyFilter();
			GetOverviewContent("EA_" + PanelName + "_Filter");
			
			EAFilter("Fan Type", "mstr2111");
			ApplyFilter();
			GetOverviewContent("EA_" + PanelName + "+Filter_EA_FanType");
			ClearALLFilter();
			CollapseFilter("Relationship Status");
			CollapseFilter("Engagement Type");
			CollapseFilter("Fan Type");
			ApplyFilter();
			
			EAFilter("Engagement Type","mstr2061");
			EAFilter("Fan Type", "mstr2111");
			ApplyFilter();
			GetOverviewContent("EA_" + PanelName + "EA_FanType");
			ClearALLFilter();
			CollapseFilter("Engagement Type");
			CollapseFilter("Fan Type");
			ApplyFilter();
		}
		
		if(PanelName.equalsIgnoreCase("Pages"))
		{
			//change to Grid mode
			WebElement changeMode = m_driver.findElement(By.xpath(".//table[@class='mstrmojo-ListBox-table']/tbody/tr/td[1]"));
			changeMode.click();
			Thread.sleep(5000);
				
			GetPagesContent("EA_" + PanelName + "_Engagers" );
				
				
			EAFilter("Fan Type", "mstr2111");
			ApplyFilter();
			GetPagesContent("EA_" + PanelName + "_Pages_Engagers_Filter");
			ClearALLFilter();
			CollapseFilter("Fan Type");
			//List<WebElement> selectors = m_driver.findElements(By.xpath(".//select"));
			//for (WebElement e:selectors){
			//	System.out.println(e.getAttribute("id"));
			//}
			ChangePageGroup("8");
			Thread.sleep(2000);
			GetPagesContent("EA_" + PanelName + "_Pages_Engagers_Group");
				
			EAFilter("Relationship Status","mstr1613");
			ApplyFilter();
			GetPagesContent("EA_" + PanelName + "_Pages_Engagers_Group_Filter");
			ClearALLFilter();
			CollapseFilter("Relationship Status");
			
			
			ChangePageCategory("3");
			Thread.sleep(2000);
			GetPagesContent("EA_" + PanelName + "_Pages_Engagers_Group_Category");
				
			EAFilter("Engagement Type","mstr2061");
			ApplyFilter();
			GetPagesContent("EA_" + PanelName + "_Pages_Engagers_Group_Category_Filter");
			ClearALLFilter();
			CollapseFilter("Engagement Type");
			/*System.out.println("step1");
			WebElement strInputPage = m_driver.findElement(By.className("mstrmojo-ObjectItem-input"));
			strInputPage.click();
			System.out.println("step2");
			Thread.sleep(1000);
			strInputPage.sendKeys("Harry Potter");
			Thread.sleep(2000);
			GetPagesContent("EA_" + PanelName + "Pages_Engagers_Group_Category_Page");
			*/
			ChangePageGroup("1");
			Thread.sleep(3000);
			GetPagesContent("EA_" + PanelName + "_Pages_Engagers_Category");
				
			EAFilter("Access Method","mstr2261");
			ApplyFilter();
			GetPagesContent("EA_" + PanelName + "_Pages_Engagers_Category_Filter");
			ClearALLFilter();
			CollapseFilter("Access Method");
			
			//change to page like group to continue this. 
			ChangeView("2");
			Thread.sleep(3000);
			EAFilter("Fan Type", "mstr2111");
			ApplyFilter();
			//GetPagesContent("EA_" + PanelName + "_Pages_Engagers_Filter");
			ClearALLFilter();
			CollapseFilter("Fan Type");
			//List<WebElement> selectors = m_driver.findElements(By.xpath(".//select"));
			//for (WebElement e:selectors){
			//	System.out.println(e.getAttribute("id"));
			//}
			ChangePageGroup("8");
			Thread.sleep(2000);
			//GetPagesContent("EA_" + PanelName + "_Pages_Engagers_Group");
				
			EAFilter("Relationship Status","mstr1613");
			ApplyFilter();
			//GetPagesContent("EA_" + PanelName + "_Pages_Engagers_Group_Filter");
			ClearALLFilter();
			CollapseFilter("Relationship Status");
			
			
			ChangePageCategory("3");
			Thread.sleep(2000);
			//GetPagesContent("EA_" + PanelName + "_Pages_Engagers_Group_Category");
				
			EAFilter("Engagement Type","mstr2061");
			ApplyFilter();
			//GetPagesContent("EA_" + PanelName + "_Pages_Engagers_Group_Category_Filter");
			ClearALLFilter();
			CollapseFilter("Engagement Type");
			
			ChangePageGroup("1");
			Thread.sleep(3000);
			//GetPagesContent("EA_" + PanelName + "_Pages_Engagers_Category");
				
			EAFilter("Access Method","mstr2261");
			ApplyFilter();
			//GetPagesContent("EA_" + PanelName + "_Pages_Engagers_Category_Filter");
			ClearALLFilter();
			CollapseFilter("Access Method");
		}
	}
	
	public void ChangeView(String strOption)
	{
		WebElement Selector = m_driver.findElement(By.xpath(".//*[@id='mstr267_select']/option[" + strOption + "]"));
		Selector.click();	
	}
	
	public void ChangePageGroup(String strOption)
	{
		WebElement Selector = m_driver.findElement(By.xpath(".//*[@id='mstr265_select']/option[" + strOption + "]"));
		Selector.click();	
	}
	
	public void ChangePageCategory(String strOption)
	{
		WebElement Selector = m_driver.findElement(By.xpath(".//*[@id='mstr266_select']/option[" + strOption + "]"));
		Selector.click();	
	}
	
	public void ApplyFilter() throws InterruptedException
	{
		System.out.println("Click Apply Button");
		WebElement ApplyButton = m_driver.findElement(By.xpath(".//*[@id='mstr103']"));
		ApplyButton.click();
		Thread.sleep(5000);
	}
	
	public void EAFilter(String strFiltername, String strClick)
	{
		List<WebElement> element = m_driver.findElements(By.className("mstrmojo-portlet-title"));	
		boolean loop = true; 
		for(WebElement e : element){
			if (loop)
			{
				String strFilter = e.getText();
				if (strFilter.contains(strFiltername))
				{	
					if (!strFilter.contains("Engagement Type")) e.click(); // expand filter choice
					WebElement filter = m_driver.findElement(By.id(strClick)); 
					filter.click();
					loop = false;
				}
			}
		}
		System.out.println("Setting the " + strFiltername + " Succesfully!");
	}
	
	public void ClearALLFilter()
	{
		WebElement ClearFilter = m_driver.findElement(By.xpath(".//*[@id='mstr97']/div"));
		ClearFilter.click();
	}
	
	public void CollapseFilter(String strFiltername)
	{
		List<WebElement> element = m_driver.findElements(By.className("mstrmojo-portlet-title"));
		//System.out.println("size: " + Integer.toString(size));	
		boolean loop = true; //int i =1;
		for(WebElement e : element){
			if (loop)
			{
				String strFilter = e.getText();
				if (strFilter.contains(strFiltername) && !strFilter.contains("Engagement Type"))
				{
					e.click(); // collaspate filter choice
				}
			}
		}
	}
	
	public void GetEngagementContent(String strFilePath) throws IOException
	{
		String strContent = null;
		
		WebElement Table = m_driver.findElement(By.className("r-cssDefault_W9321"));
		strContent = Table.getText() + "\n";
		
		//List<WebElement> Rects = m_driver.findElements(By.className("mstrmojo-DocXtabGraph"));//(".//div[@id='']/area"));
		//List<WebElement> Rects = m_driver.findElements(By.xpath(".//map[@id='*lK138*kW9312*x1*t1369106784884_map3']"));//("mstrmojo-DocXtabGraph"));
		//WebElement Rect = m_driver.findElement(By.xpath(".//*[@id='*lK138*kW6290*x1*t1369106784884_map0']"));
		//for(WebElement e: Rects){
		//System.out.println("go into here \n" + Rect.getAttribute("ttl"));
			
		//strContent  = strContent + "\n" + e.getAttribute("ttl");
		//}
		
		Table = m_driver.findElement(By.className("r-cssDefault_W9315"));
		strContent = "\n" + strContent + Table.getText();
		
		Table = m_driver.findElement(By.className("r-cssDefault_W9316"));
		strContent = "\n" + strContent + Table.getText();
		//System.out.println(strContent);
		
		File file = new File("D:\\AutomationTest\\" + strFilePath + ".txt");
		file.createNewFile();
		
		PrintWriter outFile = null;
		outFile = new PrintWriter(file);
		outFile.write(strContent);
		outFile.flush();
		outFile.close();	
	}
	
	public void GetOverviewContent(String strFilePath) throws IOException
	{
		String strContent = null;
		
		WebElement Table = m_driver.findElement(By.className("r-cssDefault_W5775"));
		strContent = Table.getText() + "\n";
		
		Table = m_driver.findElement(By.className("r-cssDefault_W5774"));
		strContent = strContent + Table.getText() + "\n";
		
		Table = m_driver.findElement(By.className("r-cssDefault_W5830"));
		strContent = strContent + Table.getText() + "\n";
		
		Table = m_driver.findElement(By.className("r-cssDefault_W6085"));
		strContent = strContent + Table.getText() + "\n";
		
		System.out.println(strContent);
		
		File file = new File("D:\\AutomationTest\\" + strFilePath + ".txt");
		file.createNewFile();
		
		PrintWriter outFile = null;
		outFile = new PrintWriter(file);
		outFile.write(strContent);
		outFile.flush();
		outFile.close();	
	}
	
	public void GetPagesContent(String strFilePath) throws IOException
	{
		String strContent = null;
		
		WebElement Table = m_driver.findElement(By.className("r-cssDefault_W7578"));
		strContent = Table.getText() + "\n";
		
		File file = new File("D:\\AutomationTest\\" + strFilePath + ".txt");
		file.createNewFile();
		
		PrintWriter outFile = null;
		outFile = new PrintWriter(file);
		outFile.write(strContent);
		outFile.flush();
		outFile.close();	
	}
	
	
}

