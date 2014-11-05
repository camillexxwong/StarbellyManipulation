package imageCompare;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.xml.parsers.*;
import javax.imageio.ImageIO;
import org.w3c.dom.*;
import java.awt.image.DataBufferByte;

import com.microstrategy.MSTRTester.ComparisonResultHolder;
import com.microstrategy.MSTRTester.analyzers.GraphAnalyzer;
import com.microstrategy.MSTRTester.utils.FileUtils;

public class IMResultSummaryWriter {
	private int sequenceID = 1; // in IM, sequence ID start from 1

	private String newXMLString;
	private String outputFilePath;
	private boolean allPass;
	private final double DEFAULT_TOLERANCE = 0;

	public IMResultSummaryWriter(String outputFilePath, String server_Name, String port, String login, String project) {
		this.outputFilePath = outputFilePath;
		sequenceID = 1;
		newXMLString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<com.microstrategy.MSTRTester.MSTRTesterModel>\n";
		this.appendIMHeader(server_Name, port, login, project);
		allPass = true;

	}

	public boolean appendIMResultExec(String docName, String docID, String docPath, String stdPNG, String newPNG, double tolerance) throws Exception {
		boolean isEqual = false;
		int TesterResult;
		String serFile;

		try {
			isEqual = compareImage(stdPNG, newPNG, tolerance);
		} catch (Exception e) {
			e.printStackTrace();
			appendIMResultFail(docName, docID, docPath, "", e.getMessage());
			throw e;
		}

		if (isEqual) {
			serFile = "";
			TesterResult = 1;
		} else {
			serFile = newPNG.replace(".png", "_diff.ser");
			TesterResult = 2;
			allPass = false;
		}

		this.appendIMResult(docName, docName, docID, docPath, docPath, TestUtils.getRelPathFromAbsPath(stdPNG, outputFilePath), TestUtils
				.getRelPathFromAbsPath(newPNG, outputFilePath), "", "", getStatusFromVal(TesterResult), String.valueOf(TesterResult), "Not Available", "0",
				TestUtils.getRelPathFromAbsPath(serFile, outputFilePath));

		return isEqual;
	}

	public boolean appendIMResultFail(String docName, String docID, String docPath, String stdErrMsg, String newErrMsg) {
		this.appendIMResult(docName, docName, docID, docPath, docPath, "", "", stdErrMsg, newErrMsg, "Not Compared - Error", "3", "Error", "7", "");
		allPass = false;
		return true;
	}

	public void finishIMResultSummary() {
		newXMLString += "</com.microstrategy.MSTRTester.MSTRTesterModel>\n";
		if (!allPass) {
			try {
				FileWriting fw = new FileWriting(outputFilePath);
				fw.initialize();
				fw.appendContent(newXMLString);
				fw.close();
				fw.finalized();
				System.out.println(outputFilePath + " is successfully generated.");
				if (ImageCompare.ta5 != null)
					ImageCompare.ta5.append(outputFilePath + " is successfully generated.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("No failure is found for this folder, ResultSummary.xml will not be generated.");
			if (ImageCompare.ta5 != null)
				ImageCompare.ta5.append("No failure is found for this folder, ResultSummary.xml will not be generated.\n");
		}
	}

	private boolean appendIMResult(String stdDocName, String newDocName, String docID, String stdDocPath, String newDocPath, String stdPNG, String newPNG,
			String stdErrMsg, String newErrMsg, String graphComparisonStatus, String graphComparisonStatus_VAL, String Execution_Status,
			String Execution_Status_VAL, String AnalysisLocation) {
		Document xmlDoc = TestUtils.getSharedDocument();
		Element resultSummaryXML = null;
		Element newElement = null;
		Element anotherElement = null;
		Node newNode = null;

		resultSummaryXML = xmlDoc.createElement("com.microstrategy.MSTRTester.ExecutableSet");
		resultSummaryXML.setAttribute("execID", String.valueOf(sequenceID));
		resultSummaryXML.setAttribute("ID", docID);

		// Element for std "com.microstrategy.MSTRTester.rw.RWExecutableImpl"
		newElement = xmlDoc.createElement("com.microstrategy.MSTRTester.rw.RWExecutableImpl");
		newElement.setAttribute("execID", String.valueOf(sequenceID));
		newElement.setAttribute("ConnectionIndex", "0");

		anotherElement = xmlDoc.createElement("name");
		newNode = xmlDoc.createTextNode(TestUtils.makeXMLCompatible(stdDocName));
		anotherElement.appendChild(newNode);
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("ID");
		newNode = xmlDoc.createTextNode(docID);
		anotherElement.appendChild(newNode);
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Path");
		newNode = xmlDoc.createTextNode(TestUtils.makeXMLCompatible(stdDocPath));
		anotherElement.appendChild(newNode);
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("desc");
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Type");
		newNode = xmlDoc.createTextNode("55");
		anotherElement.appendChild(newNode);
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("SubType");
		newNode = xmlDoc.createTextNode("14081");
		anotherElement.appendChild(newNode);
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Creation_Time");
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Modification_Time");
		newElement.appendChild(anotherElement);

		resultSummaryXML.appendChild(newElement);

		// Element for new "com.microstrategy.MSTRTester.rw.RWExecutableImpl"
		newElement = xmlDoc.createElement("com.microstrategy.MSTRTester.rw.RWExecutableImpl");
		newElement.setAttribute("execID", String.valueOf(sequenceID));
		newElement.setAttribute("ConnectionIndex", "1");

		anotherElement = xmlDoc.createElement("name");
		newNode = xmlDoc.createTextNode(TestUtils.makeXMLCompatible(newDocName));
		anotherElement.appendChild(newNode);
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("ID");
		newNode = xmlDoc.createTextNode(docID);
		anotherElement.appendChild(newNode);
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Path");
		newNode = xmlDoc.createTextNode(TestUtils.makeXMLCompatible(newDocPath));
		anotherElement.appendChild(newNode);
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("desc");
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Type");
		newNode = xmlDoc.createTextNode("55");
		anotherElement.appendChild(newNode);
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("SubType");
		newNode = xmlDoc.createTextNode("14081");
		anotherElement.appendChild(newNode);
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Creation_Time");
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Modification_Time");
		newElement.appendChild(anotherElement);

		resultSummaryXML.appendChild(newElement);

		// Element for std "com.microstrategy.MSTRTester.StoredExecutionResult"
		newElement = xmlDoc.createElement("com.microstrategy.MSTRTester.StoredExecutionResult");
		newElement.setAttribute("ConnectionIndex", "0");

		anotherElement = xmlDoc.createElement("Execution_Status");
		newNode = xmlDoc.createTextNode(TestUtils.makeXMLCompatible(Execution_Status));
		anotherElement.appendChild(newNode);
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Execution_Status_VAL");
		newNode = xmlDoc.createTextNode(TestUtils.makeXMLCompatible(Execution_Status_VAL));
		anotherElement.appendChild(newNode);
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Detailed_Execution_Status");
		newNode = xmlDoc.createTextNode(TestUtils.makeXMLCompatible(stdErrMsg));
		anotherElement.appendChild(newNode);
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Prev_Detailed_Execution_Status");
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Rows_Generated");
		newNode = xmlDoc.createTextNode("N/A");
		anotherElement.appendChild(newNode);
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Cols_Generated");
		newNode = xmlDoc.createTextNode("N/A");
		anotherElement.appendChild(newNode);
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("SQL_File");
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("DATA_File");
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Grid_DATA_File");
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Graph_File");
		newNode = xmlDoc.createTextNode(TestUtils.makeXMLCompatible(stdPNG));
		anotherElement.appendChild(newNode);
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("SQL_Normal_File");
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("SQL_HTML_File");
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("SourceTableType");
		newNode = xmlDoc.createTextNode("0");
		anotherElement.appendChild(newNode);
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Excel_File");
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Excel_Image");
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Pdf_File");
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Pdf_Image");
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Note_File");
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Is_Note_Blank");
		newNode = xmlDoc.createTextNode("true");
		anotherElement.appendChild(newNode);
		newElement.appendChild(anotherElement);

		resultSummaryXML.appendChild(newElement);

		// Element for new "com.microstrategy.MSTRTester.StoredExecutionResult"
		newElement = xmlDoc.createElement("com.microstrategy.MSTRTester.StoredExecutionResult");
		newElement.setAttribute("ConnectionIndex", "1");

		anotherElement = xmlDoc.createElement("Execution_Status");
		newNode = xmlDoc.createTextNode(TestUtils.makeXMLCompatible(Execution_Status));
		anotherElement.appendChild(newNode);
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Execution_Status_VAL");
		newNode = xmlDoc.createTextNode(TestUtils.makeXMLCompatible(Execution_Status_VAL));
		anotherElement.appendChild(newNode);
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Detailed_Execution_Status");
		newNode = xmlDoc.createTextNode(TestUtils.makeXMLCompatible(newErrMsg));
		anotherElement.appendChild(newNode);
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Prev_Detailed_Execution_Status");
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Rows_Generated");
		newNode = xmlDoc.createTextNode("N/A");
		anotherElement.appendChild(newNode);
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Cols_Generated");
		newNode = xmlDoc.createTextNode("N/A");
		anotherElement.appendChild(newNode);
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("SQL_File");
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("DATA_File");
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Grid_DATA_File");
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Graph_File");
		newNode = xmlDoc.createTextNode(TestUtils.makeXMLCompatible(newPNG));
		anotherElement.appendChild(newNode);
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("SQL_Normal_File");
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("SQL_HTML_File");
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("SourceTableType");
		newNode = xmlDoc.createTextNode("0");
		anotherElement.appendChild(newNode);
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Excel_File");
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Excel_Image");
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Pdf_File");
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Pdf_Image");
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Note_File");
		newElement.appendChild(anotherElement);

		anotherElement = xmlDoc.createElement("Is_Note_Blank");
		newNode = xmlDoc.createTextNode("true");
		anotherElement.appendChild(newNode);
		newElement.appendChild(anotherElement);

		resultSummaryXML.appendChild(newElement);

		// Element subsequent info
		newElement = xmlDoc.createElement("sqlComparisonStatus");
		newNode = xmlDoc.createTextNode("Not Available");
		newElement.appendChild(newNode);
		resultSummaryXML.appendChild(newElement);

		newElement = xmlDoc.createElement("sqlComparisonStatus_VAL");
		newNode = xmlDoc.createTextNode("0");
		newElement.appendChild(newNode);
		resultSummaryXML.appendChild(newElement);

		newElement = xmlDoc.createElement("dataComparisonStatus");
		newNode = xmlDoc.createTextNode("Not Available");
		newElement.appendChild(newNode);
		resultSummaryXML.appendChild(newElement);

		newElement = xmlDoc.createElement("dataComparisonStatus_VAL");
		newNode = xmlDoc.createTextNode("0");
		newElement.appendChild(newNode);
		resultSummaryXML.appendChild(newElement);

		newElement = xmlDoc.createElement("graphComparisonStatus");
		newNode = xmlDoc.createTextNode(TestUtils.makeXMLCompatible(graphComparisonStatus));
		newElement.appendChild(newNode);
		resultSummaryXML.appendChild(newElement);

		newElement = xmlDoc.createElement("graphComparisonStatus_VAL");
		newNode = xmlDoc.createTextNode(TestUtils.makeXMLCompatible(graphComparisonStatus_VAL));
		newElement.appendChild(newNode);
		resultSummaryXML.appendChild(newElement);

		newElement = xmlDoc.createElement("excelComparisonStatus");
		newNode = xmlDoc.createTextNode("Not Available");
		newElement.appendChild(newNode);
		resultSummaryXML.appendChild(newElement);

		newElement = xmlDoc.createElement("excelComparisonStatus_VAL");
		newNode = xmlDoc.createTextNode("0");
		newElement.appendChild(newNode);
		resultSummaryXML.appendChild(newElement);

		newElement = xmlDoc.createElement("pdfComparisonStatus");
		newNode = xmlDoc.createTextNode("Not Available");
		newElement.appendChild(newNode);
		resultSummaryXML.appendChild(newElement);

		newElement = xmlDoc.createElement("pdfComparisonStatus_VAL");
		newNode = xmlDoc.createTextNode("0");
		newElement.appendChild(newNode);
		resultSummaryXML.appendChild(newElement);

		newElement = xmlDoc.createElement("Execution_Status");
		newNode = xmlDoc.createTextNode(TestUtils.makeXMLCompatible(Execution_Status));
		newElement.appendChild(newNode);
		resultSummaryXML.appendChild(newElement);

		newElement = xmlDoc.createElement("Execution_Status_VAL");
		newNode = xmlDoc.createTextNode(TestUtils.makeXMLCompatible(Execution_Status_VAL));
		newElement.appendChild(newNode);
		resultSummaryXML.appendChild(newElement);

		newElement = xmlDoc.createElement("AnalysisLocation");
		newNode = xmlDoc.createTextNode(TestUtils.makeXMLCompatible(AnalysisLocation));
		newElement.appendChild(newNode);
		resultSummaryXML.appendChild(newElement);

		newXMLString += TestUtils.getNormTextFromNode(resultSummaryXML, 1);
		// System.out.println(TestUtils.getNormTextFromNode(resultSummaryXML,
		// 1));
		sequenceID++;

		return true;
	}

	private boolean appendIMHeader(String server_Name, String port, String login, String project) {
		Document xmlDoc = TestUtils.getSharedDocument();
		Element resultSummaryXML = null;
		Element newElement = null;
		Element anotherElement = null;
		Element connectionList = null;
		Node newNode = null;

		resultSummaryXML = xmlDoc.createElement("TesterConfig");

		newElement = createElementWithTextNode(xmlDoc, "Execution_Mode", "Project versus Project Integrity Test");
		resultSummaryXML.appendChild(newElement);
		newElement = createElementWithTextNode(xmlDoc, "Execution_Mode_Value", "1");
		resultSummaryXML.appendChild(newElement);
		newElement = createElementWithTextNode(xmlDoc, "LocalVersion", "N/A");
		resultSummaryXML.appendChild(newElement);

		newElement = xmlDoc.createElement("Analyzers");
		anotherElement = createElementWithTextNode(xmlDoc, "isDataEnabled", "false");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "isSQLEnabled", "false");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "isGraphEnabled", "true");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "isExcelEnabled", "false");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "isPdfEnabled", "false");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "isRsdExcelEnabled", "false");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "isRsdPdfEnabled", "false");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "isRsdExecEnabled", "false");
		newElement.appendChild(anotherElement);
		resultSummaryXML.appendChild(newElement);

		newElement = xmlDoc.createElement("PerformanceOptions");
		anotherElement = createElementWithTextNode(xmlDoc, "reportCycles", "0");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "documentCycles", "0");
		newElement.appendChild(anotherElement);
		resultSummaryXML.appendChild(newElement);

		newElement = xmlDoc.createElement("AnalyzersSettings");
		anotherElement = createElementWithTextNode(xmlDoc, "dynamicSQLStart", "");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "dynamicSQLEnd", "");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "isCSVEnabled", "false");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "isXLSEnabled", "false");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "isColorCodeSQL", "false");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "EquivalentStrings", "");
		newElement.appendChild(anotherElement);
		resultSummaryXML.appendChild(newElement);

		newElement = xmlDoc.createElement("ExecutionSettings");
		anotherElement = createElementWithTextNode(xmlDoc, "MaxTimeout", "10");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "numSimultaneousExecutions", "1");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "useCache", "true");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "chaseSearches", "false");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "objMatchType", "0");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "isExportFilterDetails", "null");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "isExportHeaderAsText", "null");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "isExportMetricAsText", "null");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "isLiveCharts", "null");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "isOfficeRefresh", "null");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "isRemoveColumn", "null");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "excelVersion", "null");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "isLinkPopup", "false");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "BaseURL", "");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "TargetURL", "");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "PdfExportSettings", "");
		newElement.appendChild(anotherElement);
		resultSummaryXML.appendChild(newElement);

		newElement = xmlDoc.createElement("Output");
		anotherElement = createElementWithTextNode(xmlDoc, "Output_Directory", "");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "isAppendDateToOutputDir", "false");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "LoggingInformation", "");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "LogLevel", "0");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "LogFile", "");
		newElement.appendChild(anotherElement);
		resultSummaryXML.appendChild(newElement);

		newElement = createElementWithTextNode(xmlDoc, "Base_Connection_Index", "0");
		resultSummaryXML.appendChild(newElement);

		// For element connectionList, there are two "Connection"s
		connectionList = xmlDoc.createElement("ConnectionList");
		newElement = xmlDoc.createElement("Connection");
		anotherElement = createElementWithTextNode(xmlDoc, "Server_Name", server_Name);
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "Port", port);
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "Authentication_Mode", "1");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "Login", login);
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "Project", project);
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "Version", "standard");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "baselineConnection", "false");
		newElement.appendChild(anotherElement);
		connectionList.appendChild(newElement);

		newElement = xmlDoc.createElement("Connection");
		anotherElement = createElementWithTextNode(xmlDoc, "Server_Name", server_Name);
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "Port", port);
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "Authentication_Mode", "1");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "Login", login);
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "Project", project);
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "Version", "standard");
		newElement.appendChild(anotherElement);
		anotherElement = createElementWithTextNode(xmlDoc, "baselineConnection", "false");
		newElement.appendChild(anotherElement);
		connectionList.appendChild(newElement);
		resultSummaryXML.appendChild(connectionList);

		newElement = createElementWithTextNode(xmlDoc, "TestablesList", "");
		resultSummaryXML.appendChild(newElement);

		newXMLString += TestUtils.getNormTextFromNode(resultSummaryXML, 1);
		return true;
	}
	
	private boolean compareImage(String stdPNG, String newPNG) throws Exception {
		return compareImage(stdPNG, newPNG, DEFAULT_TOLERANCE);
	}
	
	private boolean compareImage(String stdPNG, String newPNG, double tolerance) throws Exception {
		boolean _IsAllAnalyzerEqual = false;

		try {
			// Baseline file
			File oldGraphFile = new File(stdPNG);
			byte[] oldGraph = FileUtils.fileToByteArray(oldGraphFile);

			// Target file
			File newGraphFile = new File(newPNG);
			byte[] newGraph = FileUtils.fileToByteArray(newGraphFile);

			String strDifferenceFileLocation = null;
			// Difference wrapper
			strDifferenceFileLocation = processFileName(newPNG.replace(".png", "_diff.ser"));

			// Compare
			ComparisonResultHolder oComparisonResultHolder = new ComparisonResultHolder();
			
			BufferedImage baseBufferedImage = ImageIO.read(new ByteArrayInputStream(oldGraph));
			BufferedImage targetBufferedImage = ImageIO.read(new ByteArrayInputStream(newGraph));
			
			byte[] baseBytes = ((DataBufferByte)baseBufferedImage.getData().getDataBuffer()).getData();
			byte[] targetBytes = ((DataBufferByte)targetBufferedImage.getData().getDataBuffer()).getData(); 
			
			if (GraphAnalyzer.isEquivalent(baseBytes, targetBytes, tolerance)) {
				_IsAllAnalyzerEqual = true;
			} else if (GraphAnalyzer.isEquivalent(oldGraph, newGraph, oComparisonResultHolder.getGraphDifferences(), GraphAnalyzer.DEFAULT_RADIUS)) {
				_IsAllAnalyzerEqual = true;
			} else {
				FileUtils.ObjectToFile(oComparisonResultHolder, strDifferenceFileLocation);
				_IsAllAnalyzerEqual = false;
			}
		} catch (Exception e) {
			throw e;
		}
		return _IsAllAnalyzerEqual;
	}

	private String processFileName(String strFileName) {
		String _regEx = "[\\W&&[^_]]"; // non-word character except '_'
		// return strFileName.replaceAll(_regEx, "_");
		return strFileName;
	}

	private String getStatusFromVal(int val) {
		switch (val) {
		case 0:
			return "Not Available";
		case 1:
			return "Matched";
		case 2:
			return "Not Matched";
		default:
			return "Not Matched";
		}
	}

	private Element createElementWithTextNode(Document sharedDocument, String elementName, String nodeValue) {
		Element anotherElement = sharedDocument.createElement(elementName);
		if (nodeValue != null && nodeValue.length() != 0) {
			Node newNode = sharedDocument.createTextNode(nodeValue);
			anotherElement.appendChild(newNode);
		}
		return anotherElement;
	}

}
