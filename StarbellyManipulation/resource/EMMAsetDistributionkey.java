package com.microstrategy.yatiactions.web.objects.dataimport;

/**
* <p>Title: EmmaSetDistributionKey</p>
* <p>Description: Set distribution key for EMMA cube.</p>
* <p>Copyright: Copyright (c) 2014</p>
* <p>Company: MicroStrategy</p>
* @author Yi Sui
* @version 1.0
*/


import java.util.Map;
import com.microstrategy.yati.Action;
import com.microstrategy.yati.ActionContextInterface;
import com.microstrategy.yati.StateContainer;
import com.microstrategy.yati.EnumYatiTestResults;
import com.microstrategy.yatilibrary.Utils;

public class EmmaSetDistributionKey extends Action { 

	private StateContainer initialization;

	 public int initialize(StateContainer parameters,
	                       ActionContextInterface context) throws Exception {
	   initialization = parameters;
	   return EnumYatiTestResults.Ok;
	 }

	/**
	*  All yati actions should implement com.microstrategy.yati.Action.execute()
	*/
	  public int execute(StateContainer state, ActionContextInterface actionContext) throws  Exception {

	    // input/output variables declaration
	    WebEmmaReportInstance oWebEmmaReportInstance = null;
	    Map<String,String> mapAttrNameToId = null;
	    EmmaDataImporter oEmmaDataImporter = null;
	    String strAttributeName = null;
	    Integer intDistributionNum = 0;
	    DataImportJsonParser oReportInstanceAsJSON = null;
	 	
	 	String strErrorMsg = "";
	 	
	 	// Read parameters
	 	Object oDummy = null;
	 	oWebEmmaReportInstance = (WebEmmaReportInstance)Utils.getParameter("oWebEmmaReportInstance", initialization, state, oDummy);
	 	strAttributeName  = Utils.getParameter("strAttributeName", initialization, state, null);
	 	intDistributionNum  = Utils.getParameter("intDistributionNum", initialization, state, 0);
	 	oReportInstanceAsJSON = (DataImportJsonParser) Utils.getParameter("oReportInstanceAsJSON", initialization, state, oDummy);

	 	
	 	// Validate Input parameters
	 	if (oWebEmmaReportInstance == null) strErrorMsg += "oWebEmmaReportInstance ";
	 	if (strAttributeName == null) strErrorMsg += "strAttributeName ";
	 	if (oReportInstanceAsJSON == null) strErrorMsg += "oReportInstanceAsJSON ";
	 	if (intDistributionNum == 0) strErrorMsg += "intDistributionNum ";

	 	
	 	if (! strErrorMsg.isEmpty()) {
	 		Utils.logMessage("Error", "Required parameter " + strErrorMsg + " is missing", actionContext);
	 		return EnumYatiTestResults.Abort;
	 	}

	 /**
	 *  Testing code
	 */
	 	try{
	 		oEmmaDataImporter = new EmmaDataImporter();
	 		mapAttrNameToId = oReportInstanceAsJSON.getAttributeNameToIdMap();
	 		Utils.logMessage("Info", "Set distribution for attribute " + strAttributeName + " with " + intDistributionNum + " partitions.", actionContext);
	 		oWebEmmaReportInstance = oEmmaDataImporter.setDistributionKey(oWebEmmaReportInstance, mapAttrNameToId, strAttributeName, intDistributionNum);
	   } catch (Exception e) {
	 	   Utils.logMessage("Error", "Unexpected error found", actionContext);
	 	   return EnumYatiTestResults.Fail;
	   }
	 	

	 /**
	 *  Store results into container
	 */
	    // place the output into the state container
	    state.put("oWebEmmaReportInstance", oWebEmmaReportInstance);

	    // log a message
	    Utils.logMessage("Info", "Distribution key set", actionContext);

	    // return Ok
	    return EnumYatiTestResults.Ok;
	  }

	 }