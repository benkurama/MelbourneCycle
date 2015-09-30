package com.melbourne.cycle;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.melbourne.cycle.utils.ActiveCustom;
import com.melbourne.cycle.utils.Utils;

public class FundraiseAct extends ActiveCustom{
 // =========================================================================
 // TODO Variables
 // =========================================================================	
	private TextView Title;
	private Button Donate;
	private ListView FundList;
	
	private static ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
	
	private Integer x;
	
 // =========================================================================
 // TODO Activity Life Cycle
 // =========================================================================		
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fundraise_act);
        registerBaseActivityReceiver();
        
        SetupControls();
        
        list.clear();
        
        list = Utils.LoadFundRaise();
        
        SimpleAdapter adapter = new SimpleAdapter (this,list, R.layout.fundraise_list,new String[] {"price","descri"},new int[] {R.id.ammount,R.id.donnation});
        
        FundList.setAdapter(adapter);
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
 // TODO onClick View
 // =========================================================================
    public void onBack(View v){
    	
    	this.finish();
    }
 // =========================================================================   
    public void onClick(View v){
    	startActivity(new Intent(this,DonateOptions.class));
    	GloVars.setPageNavi(true);
    }
 // =========================================================================
 // TODO Implementation
 // =========================================================================

 // =========================================================================
 // TODO Functions
 // =========================================================================
    @SuppressLint("NewApi")
	public void SetupControls(){
    	
    	Title = (TextView) findViewById(R.id.tvTitle);
    	Title.setText("Fundraise");
    	
    	Donate = (Button) findViewById(R.id.btnSend);
    	Donate.setText("Donate");
    	
    	FundList = (ListView) findViewById(R.id.lstFunds);
    }
 // =========================================================================
 // TODO Final Destination
}
