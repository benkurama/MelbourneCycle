package com.melbourne.cycle;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.melbourne.cycle.utils.ActiveCustom;
import com.melbourne.cycle.utils.Properties;
import com.melbourne.cycle.utils.SMSUtils;
import com.melbourne.cycle.utils.Utils;

public class SendSMSAct extends ActiveCustom{
 // =========================================================================
 // TODO Variables
 // =========================================================================	
	private TextView Title;
	private EditText PhoneNumbers,Message;
	private Button Send;
////-----------------------////
	private BroadcastReceiver sendBroadcastReceiver = null;
	private BroadcastReceiver deliveryBroadcastReciever = null;
////-----------------------////
	private String SENT = "SMS_SENT";
    private String DELIVERED = "SMS_DELIVERED";

 // =========================================================================
 // TODO Activity Life Cycle
 // =========================================================================		
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_sms_act);
        registerBaseActivityReceiver();
        
        SetupControls();
        
        PhoneNumbers.setText(GloVars.getNumList());
        
    }
  // =========================================================================  
    @Override
	protected void onDestroy() {
		super.onDestroy();
		unRegisterBaseActivityReceiver();
		
		unregisterReceiver(sendBroadcastReceiver);
		unregisterReceiver(deliveryBroadcastReciever);
	}
 // =========================================================================
 // TODO onClick View
 // =========================================================================
    public void onBack(View v){
    	this.finish();
    	
		PhoneNumbers.setText("");
		Message.setText("");
		GloVars.setNumNull();
		
//		unregisterReceiver(sendBroadcastReceiver);
//		unregisterReceiver(deliveryBroadcastReciever);
    }
 // =========================================================================   
    public void onClick(View v){
    	
    	String phoneNo = PhoneNumbers.getText().toString();
    	String message = Message.getText().toString();
    	
    	if((phoneNo.length() > 0) && (message.length() > 0)){
    		
    		sendSMS(phoneNo, message);
    		
    		Message.setText("");
    		PhoneNumbers.setText("");
    		GloVars.setNumNull();
    		this.finish();
    	}else {
    		
    		Utils.MessageToast("Fill Message and Phone Number to Send", this);
    	}
    }
 // =========================================================================
 // TODO Implementation
 // =========================================================================
    
 // =========================================================================
 // TODO Functions
 // =========================================================================
    @SuppressLint("NewApi")
	public void SetupControls(){
    	
    	this.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    	
    	Title = (TextView) findViewById(R.id.tvTitle);
    	Title.setText("Send SMS");
    	
    	PhoneNumbers = (EditText) findViewById(R.id.etNumbers);
    	Message = (EditText) findViewById(R.id.etMessage);
    	
    	if (!TextUtils.isEmpty(GloVars.getUserName(this))){
    	
	    	Message.setText("" 
				+ "Sponsor me at \n\n"
				+ Properties.MELBOURNE_DONATION_LINK + GloVars.getUserName(this).toString()
				+ "\n\n"
				//+ "or Visit my Homepage at \n\n"
				+ Properties.MELBOURNE_PARTICIPANTS_LINKS + GloVars.getUserName(this).toString()
			);
    	}
    	
    	Send = (Button) findViewById(R.id.btnSend);
    	Send.setText("Send to Recipient");
         
    }
 // =========================================================================   
 public void sendSMS(String phone,String message){
    	try{
    		
            PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
            PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);  
            
            //---when the SMS has been sent---
            sendBroadcastReceiver = SMSUtils.sendBroadcastReceiver(this);
            registerReceiver(sendBroadcastReceiver, new IntentFilter(SENT));
            
            //---when the SMS has been delivered---
            deliveryBroadcastReciever = SMSUtils.deliverBroadcastReceiver(this);
            registerReceiver(deliveryBroadcastReciever, new IntentFilter(DELIVERED)); 
     
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phone, null, message, sentPI, deliveredPI);
            
    		//unregisterReceiver(sendBroadcastReceiver);
    		//unregisterReceiver(deliveryBroadcastReciever);
        
    	}catch(Exception e){
    		//Log.e(TAG,"Error in Sending Message Function",e);
    	}
    }
 // =========================================================================
 // TODO Final Destination
}
