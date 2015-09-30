package com.melbourne.cycle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class SplashAct extends Activity{
 // =========================================================================
 // TODO Variables
 // =========================================================================	
	private Handler hand;
 // =========================================================================
 // TODO Activity Life Cycle
 // =========================================================================		
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_act);
        
        hand = new Handler();
        hand.postDelayed(Running, 1000);
    }
 // =========================================================================
 // TODO onClick View
 // =========================================================================

 // =========================================================================
 // TODO Implementation
 // =========================================================================
    private Runnable Running = new Runnable() {
		public void run() {
			
			MainPage();
		}
	};
 // =========================================================================
 // TODO Functions
 // =========================================================================
	private void MainPage(){
		
		startActivity(new Intent(SplashAct.this,MainPageAct.class));
		this.finish();
	}
 // =========================================================================
 // TODO Final Destination
}
