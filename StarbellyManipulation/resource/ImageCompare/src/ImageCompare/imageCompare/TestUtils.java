package imageCompare;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.text.SimpleDateFormat;

public class TestUtils {

	public static PrintStream o = System.out;
	private static Document sharedDocument = null;
	private static DocumentBuilder sharedDocumentBuilder = null;

	public static String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		t.printStackTrace(pw);
		pw.flush();
		sw.flush();
		return sw.toString();
	}

	public static String applyDateFormat(Date date) {
		// This format is the same as the one the COM uses so that our logs
		// match.
		SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy h:mm:ss a");
		return formatter.format(date);
	}

	/**
	 * this method provides a shared document instance for creating elements
	 */
	public static Document getSharedDocument() {
		if (sharedDocument == null) {
			DocumentBuilder docBuilder = null;
			docBuilder = getSharedDocumentBuilder();
			sharedDocument = docBuilder.newDocument();
		}
		return sharedDocument;
	}

	/**
	 * this method provides a shared document builder for creating or parsing
	 * elements
	 */
	public static DocumentBuilder getSharedDocumentBuilder() {
		if (sharedDocumentBuilder == null) {
			try {
				DocumentBuilderFactory bldFactory = null;
				DocumentBuilder docBuilder = null;
				bldFactory = DocumentBuilderFactory.newInstance();
				sharedDocumentBuilder = bldFactory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				System.out
						.println("Can not create a new shared document builder.");
				System.exit(1);
			}
		}
		return sharedDocumentBuilder;
	}

	/*
	 * public static void handleLogging(Throwable e, String message){
	 * synchronized(o){ if(e !=null) {
	 * System.out.println(message+":"+e.getMessage()); }else{
	 * //if(EngineTest.debug) System.out.println(message); } } }
	 */

	/**
	 * This method parses an integer from an string, if error, return the
	 * default
	 */
	public static int getIntFromString(String strvalue, int defaultvalue) {
		int intvalue;
		try {
			intvalue = Integer.parseInt(strvalue);
		} catch (NumberFormatException e) {
			return defaultvalue;
		}
		return intvalue;
	}

	/**
	 * This method compares two string data as number, to see if they are equal
	 * 0.000000591 vs 0.0000005908 0.000000380 vs 0.0000003797
	 */
	public static boolean isEqual(String data1, String data2) {
		// string comparison
		if (data1.equals(data2))
			return true;

		// if not equal, int comparison
		try {
			int stdCellDataAsInt = Integer.parseInt(data1);
			int mdCellDataAsInt = Integer.parseInt(data2);
			// if both parsed to int
			if (stdCellDataAsInt == mdCellDataAsInt)
				return true;
			else
				return false;
		} catch (Exception e) {
		}
		// if it is not an integer, try to parse as double
		try {
			double stdCellData = Double.parseDouble(data1);
			double mdCellData = Double.parseDouble(data2);
			if (stdCellData == 0) {// if 0
				if (Math.abs(mdCellData) > 0.0000001) {
					return false;
				}
			} else if (Math.abs(stdCellData - mdCellData) < 0.0000001) {
				return true;
			} else {
				if (Math.abs((stdCellData - mdCellData) / stdCellData) > 0.0001) {
					return false;
				}
			}
		} catch (NumberFormatException e) {// otherwise, say, Date, BigDecimal
			// System.out.println("Number format exception when parsing as
			// double.");
			return false;
		} catch (Exception e) {
			// System.out.println("Exception happened when parsing as double.");
			return false;
		}
		return true;
	}

	/**
	 * This method fix the select columns order for you
	 */

	public static String fixSelectOrder(final String strSQLs) throws Exception {

		final String newSQLs = strSQLs;
		String fixedSQLs = newSQLs;
		String strSelectColumns = "";
		int start = 0;
		int end = 0;

		Pattern srhPattern = Pattern.compile("select");
		Matcher srhMatcher = srhPattern.matcher(newSQLs);
		while (srhMatcher.find()) {
			start = srhMatcher.end();
			Pattern endPattern = Pattern.compile("from");
			Matcher endMatcher = endPattern.matcher(newSQLs);

			if (endMatcher.find(start)) {
				end = endMatcher.start();
				strSelectColumns = newSQLs.substring(start, end);
				// handleLogging(null, "select clauses
				// found:\n"+strSelectColumns);
				// fix the order of select columns, by breaking it into array
				// and then store in TreeSet
				// and then retrieve all columns out

				String sltCols[] = strSelectColumns.split("\n");
				Collection cols = new TreeSet();
				for (int i = 0; i < sltCols.length; i++) {
					if (sltCols[i].equals("") || sltCols[i].equals("\t")
							|| sltCols[i].equals("\r"))
						continue;
					if (sltCols[i].endsWith(","))
						sltCols[i] = sltCols[i].substring(0, sltCols[i]
								.length() - 1);
					cols.add(sltCols[i].trim());
				}

				String fxdSelectColumns = "";

				/*
				 * for(Iterator itrCols=cols.iterator();itrCols.hasNext();)
				 * fxdSelectColumns+="\t"+(String) itrCols.next()+",\n";
				 * 
				 * //get rid of the last ","
				 * fxdSelectColumns=fxdSelectColumns.substring(0,fxdSelectColumns.length()-2)+"\n";
				 */
				Iterator itrCols = cols.iterator();
				fxdSelectColumns = "\t" + (String) itrCols.next();
				while (itrCols.hasNext()) {
					fxdSelectColumns += ",\n\t" + (String) itrCols.next();
				}
				fxdSelectColumns += "\n";

				// replace WJXBFS1 as WJXBFSX
				for (int i = 0; i < 10; i++) {
					fxdSelectColumns = fxdSelectColumns.replaceAll(
							"WJXBFS" + i, "WJXBFSX");
				}

				// replace the original with fixed select clauses;notice that
				// here we use fixedSQLs
				// fixedSQLs=fixedSQLs.replaceFirst(strSelectColumns.trim(),fxdSelectColumns.trim());//why
				// not working???
				fixedSQLs = fixedSQLs.substring(0, start) + fxdSelectColumns
						+ fixedSQLs.substring(end);

				if (fixedSQLs.length() != newSQLs.length())
					throw new Exception(
							"Error during select order fixing - length not equal!");

				// handleLogging(null,"fixed SQLs:\n"+fixedSQLs);

			} else {
				throw new Exception(
						"Error during select order fixing - select without from!");
			}

		}

		return fixedSQLs;
	}

	/**
	 * This method gets child node value by child tag name
	 */
	public static String getChildByName(Node objNode, String nodeName)
			throws Exception {
		if (!objNode.hasChildNodes())
			throw new Exception("This Node Has No Child Nodes!");

		NodeList childList = objNode.getChildNodes();
		Node childNode = null;
		for (int curChildNo = 0; curChildNo < childList.getLength(); curChildNo++) {
			childNode = childList.item(curChildNo);
			// System.out.println(childNode.getNodeName());
			if (childNode.getNodeName().equals(nodeName)) {
				// seems like we can not get SQLs for indented xml; try later
				// using other options
				// return childNode.getFirstChild().getNodeValue();
				NodeList grdChildList = childNode.getChildNodes();
				String strNodeValue = "";
				for (int curGrdChildNo = 0; curGrdChildNo < grdChildList
						.getLength(); curGrdChildNo++) {
					strNodeValue = strNodeValue
							+ grdChildList.item(curGrdChildNo).getNodeValue();
				}
				return strNodeValue.trim();
			}

		}

		throw new Exception("Child(" + nodeName + ") Not Found!");
	}

	/**
	 * This method provides another version of getChildByName. It returns
	 * default instead of throwing an exception when the node does not exit.
	 */
	public static String getChildByName(Node objNode, String nodeName,
			String defaultvalue) {
		try {
			return getChildByName(objNode, nodeName);
		} catch (Exception e) {
			return defaultvalue;
		}
	}

	/**
	 * This method takes a XML node and returns its value as String
	 */
	public static String getTextFromNode(Node xmlNode) {
		// By default, we assume the indent is 1
		return getNormTextFromNode(xmlNode, 0);
	}

	public static String getNormTextFromNode(Node xmlNode, int indent) {
		xmlNode.normalize();
		String indentString = getTabs(indent);
		short nodeType = xmlNode.getNodeType();
		switch (nodeType) {
		case Node.TEXT_NODE:
			return xmlNode.getNodeValue();
		case Node.CDATA_SECTION_NODE:
			return "<![CDATA[\n" + xmlNode.getNodeValue() + "\n]]>\n";
		case Node.ELEMENT_NODE:
			String tagName = xmlNode.getNodeName();
			String elementText = "";

			// retrieve attributes
			String strAttrList = "";
			Node attrNode = null;
			NamedNodeMap attrList = xmlNode.getAttributes();
			for (int i = 0; i < attrList.getLength(); i++) {
				attrNode = attrList.item(i);
				strAttrList += " " + attrNode.getNodeName() + "=\""
						+ attrNode.getNodeValue() + "\"";
			}

			if (xmlNode.hasChildNodes()) {// has child node
				NodeList nodeList = xmlNode.getChildNodes();
				int firstNodeType = nodeList.item(0).getNodeType();
				if (firstNodeType == Node.ELEMENT_NODE
						|| firstNodeType == Node.CDATA_SECTION_NODE)
					elementText += "\n";
				for (int i = 0; i < nodeList.getLength(); i++) {
					elementText += getNormTextFromNode(nodeList.item(i),
							indent + 1);
				}
				if (firstNodeType == Node.ELEMENT_NODE
						|| firstNodeType == Node.CDATA_SECTION_NODE)
					elementText += indentString;
				return indentString + "<" + tagName + strAttrList + ">"
						+ elementText + "</" + tagName + ">\n";
			} else {// if no child nodes, like <SQL/>
				return indentString + "<" + tagName + strAttrList + "/>\n";
			}
		case Node.DOCUMENT_NODE:
			elementText = "";
			if (xmlNode.hasChildNodes()) {
				NodeList nodeList = xmlNode.getChildNodes();
				for (int i = 0; i < nodeList.getLength(); i++) {
					elementText += getNormTextFromNode(nodeList.item(i),
							indent + 1);
				}
			}
			return elementText;
		default:
			return null;
		}
	}

	private static String getTabs(int indent) {
		String tabs = "";
		for (int i = 0; i < indent; i++) {
			tabs += "\t";
		}
		return tabs;
	}

	public static String headWithTitle(String title) {
		String DOCTYPE = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 "
				+ "Transitional//EN\">";
		return (DOCTYPE + "\n" + "<HTML>\n" + "<HEAD><TITLE>" + title + "</TITLE></HEAD>\n");
	}

	public static String emphasizedBlue(String content) {
		return ("<font color='#0000ff'><strong>" + content + "</strong></font>");
	}

	public static String emphasizedRed(String content) {
		return ("<font color='#ff0000'><strong>" + content + "</strong></font>");
	}

	public static String emphasizedGreen(String content) {
		return ("<font color='#009900'><strong>" + content + "</strong></font>");
	}

	public static String makeXMLCompatible(String oString) {
		StringBuffer nStringBuffer = new StringBuffer(oString.length());
		for (int i = 0; i < oString.length(); i++) {
			switch (oString.charAt(i)) {
			case '<':
				nStringBuffer.append("&lt;");
				break;
			case '>':
				nStringBuffer.append("&gt;");
				break;
			case '&':
				nStringBuffer.append("&amp;");
				break;
			case '"':
				nStringBuffer.append("&quot;");
				break;
			case '\'':
				nStringBuffer.append("&apos;");
				break;
			default:
				nStringBuffer.append(oString.charAt(i));

			}
		}
		return nStringBuffer.toString();
	}

	public static String revertMakeXMLCompatible(String oString) {
		return oString.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
				.replaceAll("&amp;", "&").replaceAll("&quot;", "\"")
				.replaceAll("&apos;", "'");
	}

	public static String getRelPathFromAbsPath(String absPath, String baseFile) {
		String relPath = "";
		File f1 = new File(absPath);
		File f2 = new File(baseFile);

		String s1 = "";
		String s2 = "";
		try {
			s1 = f1.getCanonicalPath();
			s2 = f2.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] sa1 = s1.replaceAll("\\\\", "/").split("/");
		String[] sa2 = s2.replaceAll("\\\\", "/").split("/");

		int i;
		for (i = 0; i < sa1.length && i < sa2.length; i++) {
			if (!sa1[i].equalsIgnoreCase(sa2[i]))
				break;
		}
		for (int j = 0; j < sa2.length - i - 1; j++) {
			relPath += "../";
		}
		for (int j = i; j < sa1.length; j++) {
			if (j != sa1.length - 1)
				relPath += sa1[j] + "/";
			else
				relPath += sa1[j];
		}
		return relPath;
	}

}
