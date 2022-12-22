package com.OJTProject.bookstore.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class USConstants {
	
	public final static String US = "US";
	public final static Map<String, String> mapOfUSStates = new HashMap<>() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("AL-Alabama", "Alabama");
			put("AK-Alaska", "Alaska");
			put("AZ-Arizona", "Arizona");
			put("AR-Arkansas", "Arkansas");
			put("CA-California", "California");
			put("CO-Colorado", "Colorado");
			put("CT-Connecticut", "Connecticut");
			put("DE-Delaware", "Delaware");
			put("FA-Florida", "Florida");
			put("GA-Georgia", "Georgia");
			put("HI-Hawaii", "Hawaii");
			put("ID-Idaho", "Idaho");
			put("IL-Illinois", "Illinois");
			put("IN-Indiana", "Indiana");
			put("IA-Iowa", "Iowa");
			put("KS-Kansas", "Kansas");
			put("KY-Kentucky", "Kentucky");
			put("LA-Louisiana", "Louisiana");
			put("ME-Maine", "Maine");
			put("MD-Maryland", "Maryland");
			put("MA-Massachusetts", "Massachusetts");
			put("MI-Michigan", "Michigan");
			put("MN-Minnesota", "Minnesota");
			put("MS-Mississippi", "Mississippi");
			put("MO-Missouri", "Missouri");
			put("MT-Montana", "Montana");
			put("NE-Nebraska", "Nebraska");
			put("NV-Nevada", "Nevada");
			put("NH-New Hampshire", "New Hampshire");
			put("NJ-New Jersey", "New Jersey");
			put("NM-New Mexico", "New Mexico");
			put("NY-New York", "New York");
			put("NC-North Carolina", "North Carolina");
			put("ND-North Dakota", "North Dakota");
			put("OH-Ohio", "Ohio");
			put("OK-Oklahoma", "Oklahoma");
			put("OR-Oregon", "Oregon");
			put("PA-Pennsylvania", "Pennsylvania");
			put("RI-Rhode Island", "Rhode Island");
			put("SC-South Carolina", "South Carolina");
			put("SD-South Dakota", "South Dakota");
			put("TN-Tennessee", "Tennessee");
			put("TX-Texas", "Texas");
			put("UT-Utah", "Utah");
			put("VT-Vermont", "Vermont");
			put("VA-Virginia", "Virginia");
			put("WA-Washington", "Washington");
			put("WV-West Virginia", "West Virginia");
			put("WI-Wisconsin", "Wisconsin");
			put("WY-Wyoming", "Wyoming");
			
		}
		
	};
	
	public final static List<String> listOfUSStatesCode = new ArrayList<>(mapOfUSStates.keySet());
	
	public final static List<String> listOfUSStatesName = new ArrayList<>(mapOfUSStates.values());

}
