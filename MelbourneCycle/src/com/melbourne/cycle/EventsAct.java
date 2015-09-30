package com.melbourne.cycle;

import java.text.DecimalFormat;

import com.melbourne.cycle.utils.ActiveCustom;
import com.melbourne.cycle.utils.Utils;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class EventsAct extends ActiveCustom {
 // =========================================================================
 // TODO Variables
 // =========================================================================
	private TextView Title,EventTotals,TeamName,TeamGoal,TeamRaised,ParticipantGoal,ParticipantRaised;
 // =========================================================================
 // TODO Activity Life Cycle
 // =========================================================================		
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_act);
        
        SetupControls();
        
        PostFunds();
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
			this.overridePendingTransition(R.anim.main_enter,R.anim.main_enter);
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
 // TODO Implementation
 // =========================================================================

 // =========================================================================
 // TODO Functions
 // =========================================================================
    public void SetupControls(){
    	 
    	Title = (TextView)findViewById(R.id.tvTitle);
    	Title.setText("Event Details");
    	
    	EventTotals = (TextView)findViewById(R.id.tvEventTotals);
    	TeamName = (TextView)findViewById(R.id.tvTeamName);
    	TeamGoal = (TextView)findViewById(R.id.tvTeamGoal);
    	TeamRaised = (TextView)findViewById(R.id.tvTeamRaised);
    	ParticipantGoal = (TextView)findViewById(R.id.tvParticipantGoal);
    	ParticipantRaised = (TextView)findViewById(R.id.tvParticipantRaised);
    }
 // =========================================================================
    public void PostFunds(){
    	//-->>
    	Utils.LogCat(GloVars.getEventTotals(this));
    	
    	Utils.LogCat(GloVars.getTeamGoals(this));
    	Utils.LogCat(GloVars.getTeamRaised(this));
    	//--<<
    	
    	DecimalFormat dec = new DecimalFormat("#,###,###,###,##0.00");
    	
    	if(!TextUtils.isEmpty(GloVars.getEventTotals(this))){
    		int TotalEvents = Utils.toInt(GloVars.getEventTotals(this));
			EventTotals.setText("$"+dec.format(TotalEvents));
		}
    	
    	if(!TextUtils.isEmpty(GloVars.getTeamName(this))){
    		TeamName.setText(GloVars.getTeamName(this));
		}
    	
    	if(!TextUtils.isEmpty(GloVars.getTeamGoals(this))){
    		int totalGoal = Utils.toInt(GloVars.getTeamGoals(this));
    		TeamGoal.setText("$" + dec.format(totalGoal));
		}
    	
    	if(!TextUtils.isEmpty(GloVars.getTeamRaised(this))){
    		int totalRaised = Utils.toInt(GloVars.getTeamRaised(this));
    		TeamRaised.setText("$" + dec.format(totalRaised));
		}
    	
    	if(!TextUtils.isEmpty(GloVars.getUserGoal(this))){
    		int ParticiGoal = Utils.toInt(GloVars.getUserGoal(this));
    		ParticipantGoal.setText("$" + dec.format(ParticiGoal));
		}
    	
    	if(!TextUtils.isEmpty(GloVars.getUserRaised(this))){
    		int ParticiRaised = Utils.toInt(GloVars.getUserRaised(this));
    		ParticipantRaised.setText("$" + dec.format(ParticiRaised));
		}
    }
 // =========================================================================
 // TODO Final Destination
}
