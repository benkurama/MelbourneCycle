package com.melbourne.cycle;

import com.melbourne.cycle.utils.NetUtils;
import com.melbourne.cycle.utils.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class MapImage extends Activity{
 // =========================================================================
 // TODO Variables
 // =========================================================================	
	private WebView mapWV;
	private TextView imageTv;
	
 // =========================================================================
 // TODO Activity Life Cycle
 // =========================================================================		
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle sonicInstance) {
    	
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(sonicInstance);
        setContentView(R.layout.map_image);
        
        SetupControls();
        loadWebview("short_course.jpg","Short Course");
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
			//this.overridePendingTransition(R.anim.main_enter,R.anim.main_enter);
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
    public void onMapType(View v){
    	
    	AlertDialog.Builder alert = new AlertDialog.Builder(this);
    	alert.setTitle("Select Map Type");
    	
    	alert.setPositiveButton("Google Map Type", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				
				if(NetUtils.isNetworkOn(getBaseContext())){
					startActivity(new Intent(getApplicationContext(),MapsEventsAct.class));
					finish();
				}else{
					Utils.MessageToast("No Internet Connection", getBaseContext());
				}
			}
		});
    	
    	alert.setNegativeButton("Cancel", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				//---
			}
		});
    	
    	alert.show();
    }
    // =========================================================================
    public void onCourse(View v){
    	
    	AlertDialog.Builder alert = new AlertDialog.Builder(this);
    	alert.setTitle("Select a course");
    	
    	alert.setPositiveButton("Short course", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			
				loadWebview("short_course.jpg","Short Course");
			}
		});
    	
    	alert.setNegativeButton("Long course", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				
				loadWebview("long_course.jpg","Long Course");
			}
		});
    	
    	alert.setNeutralButton("Combo course", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				
				loadWebview("combo_course.jpg","Combo Course");
			}
		});
    	
    	alert.show();
    }
 // =========================================================================
 // TODO Implementation
 // =========================================================================

 // =========================================================================
 // TODO Functions
 // =========================================================================
    @SuppressLint("NewApi")
	public void SetupControls(){
    	 
    	mapWV = (WebView)findViewById(R.id.wvMap);
    	mapWV.setInitialScale(60);
    	
    	WebSettings settings = mapWV.getSettings();
    	settings.setBuiltInZoomControls(true);
    	settings.setSupportZoom(true); 
//    	settings.setUseWideViewPort(true);
//    	settings.setLoadWithOverviewMode(true);

    	imageTv = (TextView)findViewById(R.id.tvImageMap);
    }
 // =========================================================================
    public void loadWebview(String imagePath,String Title){
    	
    	imageTv.setText(Title);
    	
//    	<style type='text/css'>
//    	   img {max-width: 100%;height:initial;} div,p,span,a {max-width: 100%;}
//    	   </style>
    	
    	String data = "<html><head>" +
    			"<style type='text/css'>" +
    			"body{margin:auto auto;text-align:center;} " +
    			"img{width:100%25;} " +
    			"</style>" +
    			"</head><body><img src='" + imagePath +"'/>" +
    			"</body></html>";
    	mapWV.loadDataWithBaseURL("file:///android_asset/",data , "text/html", "utf-8",null);
    }
 // =========================================================================
 // TODO Final Destination
}
