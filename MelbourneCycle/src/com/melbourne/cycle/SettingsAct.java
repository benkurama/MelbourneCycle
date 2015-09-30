package com.melbourne.cycle;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.melbourne.cycle.utils.ActiveCustom;
import com.melbourne.cycle.utils.NetUtils;
import com.melbourne.cycle.utils.Utils;

public class SettingsAct extends ActiveCustom{
 // =========================================================================
 // TODO Variables
 // =========================================================================	
	private TextView PageTitle;
	private EditText UserName;
	
	private String strTags;
	private String strValues;
 // =========================================================================
 // TODO Activity Life Cycle
 // =========================================================================		
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_act);
        registerBaseActivityReceiver();
        
        SetupControl();
        
        if(!TextUtils.isEmpty((GloVars.getUserName(this)))){
        	UserName.setText(GloVars.getUserName(this).toString());
        }
    }
 // =========================================================================
    @SuppressLint("NewApi")
	@Override
	public void onResume() {
		super.onResume();
		
		// -- > Animation transition
		if(GloVars.getPageNavi()){
			this.overridePendingTransition(R.anim.slide_right_in,R.anim.slide_right_out);	
		}else{
			this.overridePendingTransition(R.anim.slide_left_in,R.anim.slide_left_out);
		}
		GloVars.setPageNavi(false);
		// -- <
    }
 // =========================================================================  
    @Override
	protected void onDestroy() {
		super.onDestroy();
		unRegisterBaseActivityReceiver();
	}
 // =========================================================================
 // TODO onClick View
 // =========================================================================
    public void onBack(View v){
    	
    	this.finish();
    }
 // =========================================================================
    public void onSet(View v){
    	
    	if (!(UserName.length() == 0)){
    		
    		if(Utils.isNetworkOn(SettingsAct.this)){
    			//-->
    			final ProgressDialog dialog = ProgressDialog.show(SettingsAct.this, "Please Wait..","Processing...", true);
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
					  //strTags = NetUtils.getHtmlTags(UserName.getText().toString().trim());
					  strValues = NetUtils.getHtmlWebService(UserName.getText().toString().trim(),getBaseContext());
					  ////// -----
				      handler.sendEmptyMessage(0);				      
				      }};
				      checkUpdate.start();
    			//--<
    		}else{
    			Utils.MessageBox2("Slow or No Internet Connection", this);
    		}
    		
    	}else{
    		Utils.MessageBox("Text Box is Empty", this);
    	}
    }
 // =========================================================================
    public void onCancel(View v){
    	this.finish();
    }
 // =========================================================================
 // TODO Implementation
 // =========================================================================

 // =========================================================================
 // TODO Functions
 // =========================================================================
    @SuppressLint("NewApi")
	public void SetupControl(){
    	
    	PageTitle = (TextView) findViewById(R.id.tvTitle);
    	PageTitle.setText("Settings");
    	
    	UserName = (EditText) findViewById(R.id.etUserName);
    }
 // =========================================================================
	public void callNeededValue(){
		
		boolean access = NetUtils.getFundraiseValue(strValues,UserName.getText().toString().toLowerCase().trim(),this);

		if(access){

			this.finish();
			Utils.MessageToast("Login Successful", this);
		}
	}
 // =========================================================================
 // TODO Final Destination
}
