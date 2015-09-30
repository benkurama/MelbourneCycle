package com.melbourne.cycle;

import com.melbourne.cycle.utils.ActiveCustom;
import com.melbourne.cycle.utils.Properties;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

public class CreditsAct extends ActiveCustom{
 // =========================================================================
 // TODO Variables
 // =========================================================================	
	public TextView Content,Title;
 // =========================================================================
 // TODO Activity Life Cycle
 // =========================================================================		
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credits_act);
        registerBaseActivityReceiver();
        
        SetupControls();
        
        Title.setText("Credits");
        
        Content.setText("Converged IT Pty Ltd \n" +
    			"ABN: 46 128 249 329 2 \n" +
    			"\n" +
    			"Mailing Address:\n" +
    			"PO Box 126, Mascot 1460 , NSW, Australia\n" +
    			"\n" +
    			"Physical Address:\n" +
    			"S4 (Suite 4) ,1-3 Elizabeth Avenue \n" +
    			"Mascot NSW 2020, Australia\n" +
    			"\n" +
    			"Tel: 1300 733 982:\n" +
    			"+61 (2) 8090-3822\n" +
    			"\n" +
    			"Fax: +61 (2) 9191-6499\n" +
    			"\n" +
    			"Website: http://www.converged.net.au\n" +
    			"\n" +
    			"\n");
//    	TranslateAnimation slide = new TranslateAnimation(0, 0, 500,0 );   
//    	slide.setDuration(4000);   
//    	slide.setFillAfter(true);   
//    	Content.startAnimation(slide);
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
    public void onConverged(View v){
    	
    	Uri url = Uri.parse(Properties.CONVERGED_IT_HOME_PAGE);
		startActivity(new Intent(Intent.ACTION_VIEW,url));
    }
 // =========================================================================
 // TODO Implementation
 // =========================================================================

 // =========================================================================
 // TODO Functions
 // =========================================================================
    public void SetupControls(){
    	
    	Content = (TextView) findViewById(R.id.tvCredits);
    	Title = (TextView) findViewById(R.id.tvTitle);
    }
 // =========================================================================
 // TODO Final Destination
}
