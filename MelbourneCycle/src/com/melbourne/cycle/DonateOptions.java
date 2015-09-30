package com.melbourne.cycle;

import com.melbourne.cycle.utils.ActiveCustom;
import com.melbourne.cycle.utils.Properties;
import com.melbourne.cycle.utils.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class DonateOptions extends ActiveCustom{
 // =========================================================================
 // TODO Variables
 // =========================================================================	
	private TextView Title;
 // =========================================================================
 // TODO Activity Life Cycle
 // =========================================================================		
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donate_options);
        registerBaseActivityReceiver();
        
        SetupControls();
    }
 // =========================================================================  
    @Override
	protected void onDestroy() {
		super.onDestroy();
		unRegisterBaseActivityReceiver();
		
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
		GloVars.setPageNavi(false);
    }
 // =========================================================================
 // TODO onClick View
 // =========================================================================
    public void onBack(View v){
    	this.finish();
    	
    }
 // =========================================================================   
    public void onWebsite(View v){
    	
    	if(!TextUtils.isEmpty(GloVars.getUserName(this))){
    		
    		GloVars.setWebLink(Properties.WEBSITE_DONATION + GloVars.getUserName(this), this);
        	startActivity(new Intent(this,WebViewAct.class));
    		
    	} else {
    		Utils.MessageBox("Username not found", this);
    	}
    }
 // =========================================================================      
    public void onApps(View v){
    	startActivity(new Intent(this,DonateInAppAct.class));
    }
 // =========================================================================      
    public void onPrivacy(View v){
    	
    	GloVars.setWebLink(Properties.PRIVACY_POLICY_LINK, this);
    	startActivity(new Intent(this,WebViewAct.class));
    }
 // =========================================================================      
    public void onTerms(View v){
    	
    	GloVars.setWebLink(Properties.TERMS_AND_CONDITIONS, this);
    	startActivity(new Intent(this,WebViewAct.class));
    }
 // =========================================================================
 // TODO Implementation
 // =========================================================================

 // =========================================================================
 // TODO Functions
 // =========================================================================
    public void SetupControls(){
    	
    	Title = (TextView) findViewById(R.id.tvTitle);
    	Title.setText("Donate Options");
    }
 // =========================================================================
 // TODO Final Destination
}
