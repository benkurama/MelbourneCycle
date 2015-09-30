package com.melbourne.cycle;

import java.text.DecimalFormat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.melbourne.cycle.utils.ActiveCustom;
import com.melbourne.cycle.utils.NetUtils;
import com.melbourne.cycle.utils.Properties;
import com.melbourne.cycle.utils.Utils;

public class MainPageAct extends ActiveCustom{
 // =========================================================================
 // TODO Variables
 // =========================================================================	
	private LinearLayout Slides,LandSlides;
	private TextView Raised,Goal,EventTotal,TeamName;
	private ProgressBar Bar;
////-----------------------////
	private String strTags;
 // =========================================================================
 // TODO Activity Life Cycle
 // =========================================================================		
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_act);
        registerBaseActivityReceiver();
        
        checkFirstTimeUser();
        
        SetupControls();
        GloVars.setPageNavi(true);
    }
 // =========================================================================		
    @SuppressLint("NewApi")
	@Override
	public void onResume() {
		super.onResume();
		// Animation transition
		if(GloVars.getPageNavi()){
			this.overridePendingTransition(R.anim.slide_right_in,R.anim.slide_right_out);	
		}else{
			this.overridePendingTransition(R.anim.slide_left_in,R.anim.slide_left_out);
		}
		
		if(!TextUtils.isEmpty((GloVars.getUserName(this)))){
			
			postFunds();
        }
		///----------------------------------------
		if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) 
		{
			getSlides();
			
		}else {
			getLandSlides();
		}
	}
  // =========================================================================  
    @Override
	protected void onDestroy() {
		super.onDestroy();
		unRegisterBaseActivityReceiver();
	}
    // =========================================================================
    // TODO Menu Methods
    // =========================================================================
   	public boolean onCreateOptionsMenu(Menu menu){
   	
   	MenuInflater inflater = getMenuInflater();
   	inflater.inflate(R.menu.menu, menu);
   	return true;
   }
    //=========================================================================	
   @Override
   	public boolean onOptionsItemSelected(MenuItem item) {
   	
       switch (item.getItemId()) {
           case R.id.refresh:
        	   callRefreshData();
               break;
           case R.id.exit:
           	this.finish();
               break;
       }
       return true;
   }
 // =========================================================================
 // TODO onClick View
 // =========================================================================
    public void onMelbourne(View v){
    	
    	Uri url = Uri.parse(Properties.MELBOURNE_CYCLE_HOME_PAGE);
		startActivity(new Intent(Intent.ACTION_VIEW,url));
    	
		// Reserved Codes
    	// Map point based on address
    	//Uri location = Uri.parse("geo:0,0?q=Metro+Manila");
    	// Or map point based on latitude/longitude
    	// Uri location = Uri.parse("geo:37.422219,-122.08364?z=14"); // z param is zoom level
    	//Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
    	//startActivity(mapIntent);
		
//		Intent sendIntent = new Intent();
//		sendIntent.setAction(Intent.ACTION_SEND);
//		sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
//		sendIntent.setType("text/plain");
//		startActivity(Intent.createChooser(sendIntent, "Text Sample"));
    }
  // =========================================================================
    public void onSettings (View v){
    	
    	startActivity(new Intent(MainPageAct.this,SettingsAct.class));
    	GloVars.setPageNavi(true);
    }
  // =========================================================================
    public void onCredits(View v){
    	
    	startActivity(new Intent(this,CreditsAct.class));
    }
 // =========================================================================
    public void onFundraise(View v){
    	
    	startActivity(new Intent(this,FundraiseAct.class));
    	GloVars.setPageNavi(true);
    }
 // =========================================================================
    public void onContacts(View v){
    	
    	final Dialog dialog = new Dialog(this,android.R.style.Theme_Dialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// this setting is incorrect format but the output is correct
		dialog.getWindow().setBackgroundDrawableResource(R.drawable.blanc);
		dialog.setContentView(R.layout.custom_dialog);
		
		Button sendEmail = (Button) dialog.findViewById(R.id.btnEmail);
		Button sendSms = (Button) dialog.findViewById(R.id.btnSMS);
		TextView sendTitle = (TextView)dialog.findViewById(R.id.tvDialogMenu);
		
		sendTitle.setText("Select A Type");
		
		sendSms.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//-->
				GloVars.setSendType("SMS", MainPageAct.this);
				startActivity(new Intent(MainPageAct.this,ContactsAct.class));
				dialog.dismiss();
				GloVars.setPageNavi(true);
				//--<
			}
		});
		
		sendEmail.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//-->
				GloVars.setSendType("EMAIL", MainPageAct.this);
				startActivity(new Intent(MainPageAct.this,ContactsAct.class));
				dialog.dismiss();
				GloVars.setPageNavi(true);
				//--<
			}
		}); 
		
		dialog.setCancelable(true);
		dialog.show();
    }
 // =========================================================================   
    public void onInfo(View v){
    	
    	final Dialog dialog = new Dialog(this,android.R.style.Theme_Dialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// this setting is incorrect format but the output is correct
		dialog.getWindow().setBackgroundDrawableResource(R.drawable.blanc);
		dialog.setContentView(R.layout.custom_dialog_info);
		//-->>
		Button btnCancel = (Button)dialog.findViewById(R.id.btnCancel);
		TextView TotalEventTv = (TextView)dialog.findViewById(R.id.tvTotalEvent);
		TextView TeamName = (TextView)dialog.findViewById(R.id.tvTeamName);
		TextView TeamGoal = (TextView)dialog.findViewById(R.id.tvTeamGoal);
		TextView TeamRaised = (TextView)dialog.findViewById(R.id.tvTeamRaised);
		int EventTotal,goalTeam,raisedTeam;
		DecimalFormat dec = new DecimalFormat("#,###,###,###,##0.00");
		//--<<
		//-->>
		if(!TextUtils.isEmpty(GloVars.getEventTotals(this))){
			
			EventTotal = Utils.toInt(GloVars.getEventTotals(this));
			TotalEventTv.setText("$"+dec.format(Math.round(EventTotal)));
		}
		
		if(!TextUtils.isEmpty(GloVars.getTeamName(this))){
			TeamName.setText(GloVars.getTeamName(this));
		}
		
		if(!TextUtils.isEmpty(GloVars.getTeamGoals(this))){
			
			goalTeam = Utils.toInt(GloVars.getTeamGoals(this));
			TeamGoal.setText("$"+dec.format(Math.round(goalTeam)));
		}
		
		if(!TextUtils.isEmpty(GloVars.getTeamRaised(this))){
			
			raisedTeam = Utils.toInt(GloVars.getTeamRaised(this));
			TeamRaised.setText("$"+dec.format(Math.round(raisedTeam)));
		}
		
		//--<<
		btnCancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		dialog.setCancelable(true);
		dialog.show();
    }
 // =========================================================================
    public void onEvents(View v){
    	
    	final Dialog dialog = new Dialog(this,android.R.style.Theme_Dialog);
    	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	dialog.getWindow().setBackgroundDrawableResource(R.drawable.blanc);
    	dialog.setContentView(R.layout.custom_event_dialog);
    	
    	Button viewEvent = (Button) dialog.findViewById(R.id.btnOne);
    	Button viewMap = (Button) dialog.findViewById(R.id.btnTwo);
    	TextView Title = (TextView) dialog.findViewById(R.id.tvTitleDial);
    	Title.setText("Select Event Types");
    	
    	final Context Core = this;
    	
    	viewEvent.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// -- >>
		    	if(!TextUtils.isEmpty(GloVars.getUserName(Core))){
		    		
		    		startActivity(new Intent(Core,EventsAct.class));
		    		GloVars.setPageNavi(true);
		    		
		    	} else {
		    		Utils.MessageBox("Username not found", Core);
		    	}
		    	dialog.dismiss();
		    	// -- <<
			}
		});
    	
    	viewMap.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				if(NetUtils.isNetworkOn(getBaseContext())){
					
					startActivity(new Intent(Core,MapsEventsAct.class));
					dialog.dismiss();
					GloVars.setPageNavi(true);
				}else{
					
					startActivity(new Intent(Core,MapImage.class));
					dialog.dismiss();
					GloVars.setPageNavi(true);
				}
				
//				startActivity(new Intent(Core,MapsEventsAct.class));
//				dialog.dismiss();
//				GloVars.setPageNavi(true);
					
			}
		});
    	
    	dialog.setCancelable(true);
    	dialog.show();
    }
 // =========================================================================
 // TODO Implementation
 // =========================================================================

 // =========================================================================
 // TODO Functions
 // =========================================================================
    public void SetupControls(){
    	
    	Slides = (LinearLayout) findViewById(R.id.llSlides);
    	LandSlides = (LinearLayout) findViewById(R.id.landSlides);
    	
    	Raised = (TextView) findViewById(R.id.tvRaised);
    	Goal = (TextView) findViewById(R.id.tvTotal);
    	
    	Bar = (ProgressBar) findViewById(R.id.progBar);
    	Bar.setMax(100);
    	
    }
  // =========================================================================
    public void getSlides(){
    	
    	int randomNum = (int) Math.ceil(Math.random() * 4);
		switch (randomNum) {
		case 1:
			Slides.setBackgroundDrawable(getResources().getDrawable(R.drawable.main_body_a1));
			break;
		case 2:
			Slides.setBackgroundDrawable(getResources().getDrawable(R.drawable.main_body_a2));
			break;
		case 3:
			Slides.setBackgroundDrawable(getResources().getDrawable(R.drawable.main_body_a3));
			break;
		case 4:
			Slides.setBackgroundDrawable(getResources().getDrawable(R.drawable.main_body_a4));
			break;
		}
    }
  // =========================================================================
    public void getLandSlides(){
    	
    	int randomNum = (int) Math.ceil(Math.random() * 4);
		switch (randomNum) {
		case 1:
			LandSlides.setBackgroundDrawable(getResources().getDrawable(R.drawable.land_main_b1));
			break;
		case 2:
			LandSlides.setBackgroundDrawable(getResources().getDrawable(R.drawable.land_main_b2));
			break;
		case 3:
			LandSlides.setBackgroundDrawable(getResources().getDrawable(R.drawable.land_main_b3));
			break;
		case 4:
			LandSlides.setBackgroundDrawable(getResources().getDrawable(R.drawable.land_main_b4));
			break;
		}
    }
 // =========================================================================
    public void postFunds(){
    	
		int percent = 0;
		double progress,total, divide = 0;
		
		progress = Utils.toInt(GloVars.getUserRaised(this));
		total = Utils.toInt(GloVars.getUserGoal(this));
		
		DecimalFormat dec = new DecimalFormat("#,###,###,###,##0.00");
    	
		Raised.setText("$"+dec.format(Math.round(progress)));
		Goal.setText("$"+dec.format(Math.round(total)));
		
		divide = (double)(progress / total);
		percent = (int) (divide * 100);
		Bar.setProgress(percent);
    }
 // =========================================================================
    public void callRefreshData(){
    	
    	if(Utils.isNetworkOn(MainPageAct.this)){
			//-->
			final ProgressDialog dialog = ProgressDialog.show(MainPageAct.this, "Please Wait..","Processing...", true);
			final Handler handler = new Handler() {
			   public void handleMessage(Message msg) {
			      dialog.dismiss();
			      ///// 2nd if the load finish -----
			      callNeededValue();
				  ///// -----
			      }};
			      
			      Thread checkUpdate = new Thread() {  
			   public void run() {	
				  /// 1st main activity here... -----
				  strTags = NetUtils.getHtmlWebService(GloVars.getUserName(getBaseContext()),getBaseContext());
				  ////// -----
			      handler.sendEmptyMessage(0);				      
			      }};
			      checkUpdate.start();
			//--<
		}else{
			Utils.MessageBox("Slow or No Internet Connection", this);
		}
    }
 // =========================================================================
    public void callNeededValue(){
    	
    	boolean access = NetUtils.getFundraiseValue(strTags,GloVars.getUserName(this),this);
    	
		if(!TextUtils.isEmpty(GloVars.getUserName(this)) && access){
			
			postFunds();
			Utils.MessageBox("Update Successful", this);
			
        } else {
        	Utils.MessageBox("Update Failed", this);
        }
    }
 // =========================================================================   
    public void checkFirstTimeUser(){
    	
    	if(TextUtils.isEmpty(GloVars.getUserName(this))){
    		
    		Utils.MessageBox("To begin, kindly go to Settings and type in your Username", this);
    	}
    }
 // =========================================================================
 // TODO Final Destination
}
