package com.melbourne.cycle;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;

public class GloVars extends Application{
	
	private static String numberList;
	private static boolean pageNavi;
	
	@Override
	public void onCreate() {
		super.onCreate();
		numberList = "";
		pageNavi = true;
	}
 // =========================================================================
 // TODO Functions
 // =========================================================================
	
	//-->
	public static String getUserName(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getString("userName", null);
	}
	public static void setUserName(String name,Context context) {
		PreferenceManager.getDefaultSharedPreferences(context).edit().putString("userName", name).commit();
	}
	//--<
	
	//-->
	public static String getUserRaised(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getString("userRaised", null);
	}
	public static void setUserRaised(String raised,Context context) {
		PreferenceManager.getDefaultSharedPreferences(context).edit().putString("userRaised", raised).commit();
	}
	//--<
	
	//-->
	public static String getUserGoal(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getString("userGoal",null);
	}
	public static void setUserGoal(String goal,Context context) {
		PreferenceManager.getDefaultSharedPreferences(context).edit().putString("userGoal", goal).commit();
	}
	//--<
	
	//-->
	public static String getWebLink(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getString("webLink", null);
	}
	public static void setWebLink(String link,Context context) {
		PreferenceManager.getDefaultSharedPreferences(context).edit().putString("webLink", link).commit();
	}
	//--<
	
	//-->
	public static String getSendType(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getString("sendType", null);
	}
	public static void setSendType(String type,Context context) {
		PreferenceManager.getDefaultSharedPreferences(context).edit().putString("sendType", type).commit();
	}
	//--<
	
	//-->
	public static String getLetter(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getString("letter", null);
	}
	public static void setLetter(String letter,Context context) {
		PreferenceManager.getDefaultSharedPreferences(context).edit().putString("letter", letter).commit();
	}
	//--<
	
	//-->
	public static String getEventTotals(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getString("eventTotals", null);
	}
	public static void setEventTotals(String EventTotals,Context context) {
		PreferenceManager.getDefaultSharedPreferences(context).edit().putString("eventTotals", EventTotals).commit();
	}
	//--<
	
	//-->
	public static String getTeamName(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getString("teamName", null);
	}
	public static void setTeamName(String TeamName,Context context) {
		PreferenceManager.getDefaultSharedPreferences(context).edit().putString("teamName", TeamName).commit();
	}
	//--<
	
	//-->
	public static String getTeamGoals(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getString("teamGoal", null);
	}
	public static void setTeamGoals(String TeamGoal,Context context) {
		PreferenceManager.getDefaultSharedPreferences(context).edit().putString("teamGoal", TeamGoal).commit();
	}
	//--<
	
	//-->
	public static String getTeamRaised(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getString("teamRaised", null);
	}
	public static void setTeamRaised(String TeamRaised,Context context) {
		PreferenceManager.getDefaultSharedPreferences(context).edit().putString("teamRaised", TeamRaised).commit();
	}
	//--<
	
	//-->
	public static String getNumList() {
	    return numberList;
	}
	public static void setNumList(String numlist) {
		GloVars.numberList += numlist;
	}
	public static void setNumNull(){
		GloVars.numberList = "";
	}
	//--<
	
	//-->
	public static boolean getPageNavi(){
		return pageNavi;
	}
	public static void setPageNavi(boolean navi){
		GloVars.pageNavi = navi;
	}
	//--<
}
