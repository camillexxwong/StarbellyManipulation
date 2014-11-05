package cawang.selenium.starbelly.document;

import java.io.IOException;

public interface IPanel {
	/**
	 * Main Execution Flow for Each Panels
	 * @throws InterruptedException 
	 * 
	 */
	void test() throws InterruptedException;
	/**
	 * Get Panel Data Content
	 * 
	 */
	void getContent(String fileName) throws IOException;
}
