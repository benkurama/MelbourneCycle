package com.melbourne.cycle.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;


public class Utils {
 // =========================================================================
 // TODO Message Box | Page: Universal
 // =========================================================================
	public static void MessageBox(String msg, Context context){
		
		AlertDialog.Builder alert = new AlertDialog.Builder(context);                 
 		alert.setTitle(msg);  

 		alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
 	    public void onClick(DialogInterface dialog, int which) {
 	    	//
 	        //return;   
 	    }});
 		
 		alert.show();
	}
	
 // =========================================================================
 // TODO Message Box | Page: Universal
 // =========================================================================
	public static void MessageBox2(String msg, Context context){
		
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
 		alert.setTitle("Error : Internet Connection");  
 		alert.setMessage(msg);

 		alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
 	    public void onClick(DialogInterface dialog, int which) {
 	    	//
 	        //return;   
 	    }});
 		
 		alert.show();
	}
 // =========================================================================
 // TODO Round value into float | Page: Universal
 // =========================================================================
	public static String convertValue(String str){
		
		Float value1 = Float.parseFloat(str);
		Integer convert = Math.round(value1);
		String con = convert.toString();
		
		return con;
	}
 // =========================================================================
 // TODO Convert String into Integer | Page: Universal
 // =========================================================================		
	public static Integer toInt(String str){
		Integer x;
		x = Integer.parseInt(str);
		return x;
	}
 // =========================================================================
 // TODO Message Toast | Page: Universal
 // =========================================================================
	public static void MessageToast(String msg,Context context){
		
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}
 // =========================================================================
 // TODO Load Fundraise Details Legends | Page: Universal
 // =========================================================================
	public static ArrayList<HashMap<String, String>> LoadFundRaise(){
		
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		int x;
		
		String price[] = {"30","65","120","250","500"};
		String desc[] = {"Pays for a telephone consultation with one of our MS CONNECT health care professionals for up to date information about MS and MS management"
		       ,"Pays for a hand rail to be installed on a person's home to assist walking up and down stairs as MS can affect balance"
		       ,"Pays for a person with MS to participate in a Managing your Fatigue teleconference program to learn how to be better manage their symptoms"
		       ,"Pays for an individually tailored exercise program to improve the balance, strength and mobility of a person with MS"
		       ,"Pays for a professional Health & Lifestyle assessment for an person with MS and the development of an individual action plan to improve health, wellbeing & quality life"
		       };
		
		for(x=0; x < price.length; x++){
			
		     HashMap<String,String> temp = new HashMap<String,String>();
		     
		     temp.put("price","$"+price[x]);
		     temp.put("descri", desc[x]);
		     list.add(temp);
		}
		
		return list;
	}
 // =========================================================================
 // TODO Progress Dialog | Page: Universal
 // =========================================================================
	public static ProgressDialog showProgressDialog(Activity activity,CharSequence title, CharSequence message,	boolean isInderdeterminate, boolean cancellable,OnCancelListener onCancelListener) {
		
		ProgressDialog dialog = ProgressDialog.show(activity, title, message,isInderdeterminate, cancellable, onCancelListener);
		dialog.setOwnerActivity(activity);
		
		return dialog;
	}
 // =========================================================================
 // TODO Detect Number | Page: Universal
 // =========================================================================
	public static boolean detectNumber(String str){
		
		boolean number = false;
		
		for(char c: str.toCharArray()){
			
			if(Character.isDigit(c)){
				number = true;
			}else{
				number = false;
			}
		}
		return number;
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
	public static void LogCat(String value){
		
		Log.d(Properties.TAG,value);
	}
	
 // =========================================================================
	public static boolean Ping(String u) { 
	    try {
	    	
	        URL url = new URL(u);
	        HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
	        urlc.setConnectTimeout(10000); // Time is in Milliseconds to wait for ping response
	        urlc.connect();
	        
	        if (urlc.getResponseCode() == 200) {
	        	
	        	Utils.LogCat("* ping  with ResponseCode: "+urlc.getResponseCode());
	            return true;
	        }else{
	        	
	        	Utils.LogCat("* Connection is too slow with ResponseCode: "+urlc.getResponseCode());
	            return false;
	        }
	    }catch (MalformedURLException e1){
	    	Utils.LogCat("Error in URL to ping"+e1);
	        return false;
	    }catch (IOException e){
	    	Utils.LogCat("Error in URL to ping"+e);
	        return false;
	    }
	}
 // =========================================================================
 // TODO Final Destination	
}
