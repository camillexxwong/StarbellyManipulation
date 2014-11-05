package delete;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import xxx.tool.reliability.starbelly.DriverTool;

/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public class Login {
	private DriverTool driverTool;

	public Login(DriverTool driverTool) {
		this.driverTool = driverTool;
	}

/*	public Login() {
	}*/

	/**
	 * Main Execution Flow
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		driverTool.getDriver().get("http://localhost:8080/starbelly/servlet/mstrWebAdmin");
		driverTool.getDriver().findElement(By.cssSelector("a.mstrLink > span.mstrHighlighted"))
				.click();
		driverTool.getDriver()
				.findElement(
						By
								.xpath("(//a[contains(@href, 'mstrWeb?evt=3010&src=mstrWeb.3010&Port=0&Project=Wisdom+Enterprise&Server=10.199.57.6&loginReq=true')])[2]"))
				.click();
		driverTool.getDriver().findElement(By.id("Uid")).clear();
		driverTool.getDriver().findElement(By.id("Uid")).sendKeys("administrator");
		driverTool.getDriver().findElement(By.id("Pwd")).clear();
		driverTool.getDriver().findElement(By.id("Pwd")).sendKeys("alertadmin");
		driverTool.getDriver().findElement(By.id("3054")).click(); //[Login Button]
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (driverTool.isElementPresent(By
						.cssSelector("div.mstrLargeIconViewItemName > a.mstrLink")))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		} //Wait for Element Present

		driverTool.getDriver().findElement(
				By.cssSelector("div.mstrLargeIconViewItemName > a.mstrLink")) //[Shared Reports]
				.click();
	}


}
