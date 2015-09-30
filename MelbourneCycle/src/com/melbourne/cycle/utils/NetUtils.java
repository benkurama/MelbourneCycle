package com.melbourne.cycle.utils;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.melbourne.cycle.GloVars;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

public class NetUtils {
 // =========================================================================
 // TODO GetHtmlTag from Web Service| Page: SettingsAct
 // =========================================================================	
	public static String getHtmlWebService(String username,Context context){
		
		String str = "";
		
		try{
			
			
			String URL = Properties.PARTICIPANT_FUNDRAISED + username;
			
			URL url = new URL(URL);
			
	        URLConnection con = url.openConnection();
	        Pattern p = Pattern.compile("text/html;\\s+charset=([^\\s]+)\\s*");
	        Matcher m = p.matcher(con.getContentType());
	        
	        /* If Content-Type doesn't match this pre-conception, choose default and hope for the best.  */
	        String charset = m.matches() ? m.group(1) : "ISO-8859-1";
	        Reader r;
			
			r = new InputStreamReader(con.getInputStream(), charset);
			StringBuilder buf = new StringBuilder();
			
	        while (true) {
	        	
	        	int ch = r.read();
	        	if (ch < 0)
	            break;
	        	buf.append((char) ch);
	        	
	        	str = buf.toString();
	        }
			
		}catch(Throwable t){
			//Utils.MessageToast("Error Parsing", context);
		}
		
		return str;
	}
 
 // =========================================================================
 // TODO GetNeededValue from Web Service | Page: SettingsAct
 // =========================================================================
	public static boolean getFundraiseValue(String value,String username,Context context){
		
		String[] ArrayOne = value.split("<q1Result>");
		String[] ArrayTwo = ArrayOne[1].split("</q1Result>");
		
		String[] DataValue = ArrayTwo[0].split("\\|");
		
		if(DataValue.length !=1){
		
			// Set Value to Global Variables 
			// for username
			GloVars.setUserName(username, context);
			// for Event Totals
			GloVars.setEventTotals(Utils.convertValue(DataValue[0]), context);
			// for User Totals
			if(!DataValue[1].equals("")){
				GloVars.setUserGoal(Utils.convertValue(DataValue[1]), context);
			} else {
				GloVars.setUserGoal(Utils.convertValue("0.00"), context);
			}
			
			// for User Raised
			if(!DataValue[2].equals("")){
				GloVars.setUserRaised(Utils.convertValue(DataValue[2]), context);
			}else{
				GloVars.setUserRaised(Utils.convertValue("0.00"), context);
			}
			
			if (!DataValue[3].equals("")){
				// for Team Name
				GloVars.setTeamName(DataValue[3], context);
			}else{
				GloVars.setTeamName("No Team", context);
			}
			
			if (!DataValue[4].equals("")){
				// for Team Totals
				GloVars.setTeamGoals(Utils.convertValue(DataValue[4]), context);
			}else{
				GloVars.setTeamGoals(Utils.convertValue("0.00"), context);
			}
			
			if (!DataValue[5].equals("")){
				// for Team Raised
				GloVars.setTeamRaised(Utils.convertValue(DataValue[5]), context);
			}else{
				GloVars.setTeamRaised(Utils.convertValue("0.00"), context);
			}
		
			return true;
		}else{
			Utils.MessageBox("Username not found!", context);
			return false;
		}
	}
	  // =========================================================================
	 	public static boolean isNetworkOn(Context context) {
	 		boolean haveConnectedWifi = false, haveConnectedMobile = false;
	 		
	 	    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	 	    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
	 	    
	 	    for (NetworkInfo ni : netInfo) {
	 	        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
	 	            if (ni.isConnected())
	 	                haveConnectedWifi = true;
	 	        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
	 	            if (ni.isConnected())
	 	                haveConnectedMobile = true;
	 	    }
	 	    return haveConnectedWifi || haveConnectedMobile;
	 	}
 // =========================================================================
 // TODO Final Destination	
}