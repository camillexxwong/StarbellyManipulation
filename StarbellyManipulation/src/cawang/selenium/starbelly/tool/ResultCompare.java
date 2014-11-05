package cawang.selenium.starbelly.tool;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import imageCompare.ImageCompare;

public class ResultCompare {
	ImageCompare imgCompare = new ImageCompare();

	public void getFields() {
		Field[] fields = imgCompare.getClass().getDeclaredFields();
		Method[] methods = imgCompare.getClass().getMethods();
		for (Field f : fields) {
			System.out.println(f.getName());
		}
		for (Method m : methods) {
			System.out.println(m.getName());
		}

	}
	
	/**
	 * @param batchPath, the batch file path of callImageCompare
	 * @param baselinePath
	 * @param outputPath
	 */
	public static void compareImageResults(String batchPath, String baselinePath, String outputPath){
		try {
			Runtime.getRuntime().exec("cmd /k start"+" "+batchPath+" "+baselinePath+" "+outputPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param batchPath
	 * @param baselinePath
	 * @param outputPath
	 * @param resultPath
	 */
	public static void compareImageResults(String batchPath, String baselinePath, String outputPath, String resultPath){
		try {
			Runtime.getRuntime().exec("cmd /k start"+" "+batchPath+" "+baselinePath+" "+outputPath+" "+resultPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
