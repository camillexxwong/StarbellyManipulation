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

public class Logout {
	private DriverTool driverTool;

	public Logout(DriverTool driverTool) {
		this.driverTool = driverTool;
	}

	/**
	 * Main Execution Flow
	 * 
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		driverTool.getDriver().get("http://localhost:8080/starbelly/servlet/mstrWebAdmin");
		driverTool.getDriver().findElement(By.cssSelector("a.mstrLink > span.mstrHighlighted"))
				.click();
		try {
			driverTool.getDriver().findElement(By.linkText("Logout")).click();
		} catch (Exception e) {
			System.out.println("[Info] Already Logged Out!");
		}
	}
}
