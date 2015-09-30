package com.melbourne.cycle;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;
import com.melbourne.cycle.objects.LinesOverlay;
import com.melbourne.cycle.objects.LongCourseOverlay;
import com.melbourne.cycle.objects.MapItemizedOverlay;

public class MapsEventsAct extends MapActivity{
 // =========================================================================
 // TODO Variables
 // =========================================================================	
	// Legend set to the Mapview, not Literally Variables
	String forEmulator = "0pMf5Q5UZIaNhUblQ6CHTEJ1Q2KsYMYskTPaT5A";
	String forApps = "0pMf5Q5UZIaNcq4DqdCKcP7bJtbckl-m1fet3nw";
	
	
	private MapView mapView;
	private TextView TitleTv;
	
	//private MapController mapController;
	private Projection projection;
	private List<Overlay> mapOverlays;
	
	private boolean ShortCourse = true;
	private boolean LongCourse = false;
 // =========================================================================
 // TODO Activity Life Cycle
 // =========================================================================		
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_events_act);
        
        SetupControls();
        TitleTv.setText("Short Course");
		SetMap();
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
   	
   	alert.setPositiveButton("Image Map Type", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				
				startActivity(new Intent(getBaseContext(),MapImage.class));
				finish();
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
	   alert.setTitle("Select Course");
	   
	   alert.setPositiveButton("Short Course", new OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			
			 mapOverlays.removeAll(mapOverlays);
			 ShortCourse = true;
			 LongCourse = false;
			 TitleTv.setText("Short Course");
			 SetMap();
			}
		});
	   
	   alert.setNeutralButton("Combo Course", new OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			
			mapOverlays.removeAll(mapOverlays);
			ShortCourse = true;
			LongCourse = true;
			TitleTv.setText("Combo Course");
			SetMap();
			}
		});
	   
	   alert.setNegativeButton("Long Course", new OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			
			mapOverlays.removeAll(mapOverlays);
			ShortCourse = false;
			LongCourse = true;
			TitleTv.setText("Long Course");
			SetMap();
			}
		});
	   
	   alert.show();
   }
// =========================================================================
// TODO Implementation
// =========================================================================	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
// =========================================================================
// TODO Methods
// =========================================================================
	public void SetupControls(){
		
		TitleTv = (TextView)findViewById(R.id.tvTitleCourse);
		mapView = (MapView)findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        projection = mapView.getProjection();
        
	}
 // =========================================================================
	public void SetMap(){
		
		mapOverlays = mapView.getOverlays();
		
		// -- >>
        if(LongCourse){
        	mapOverlays.add(new LongCourseOverlay(projection));
        }
        if(ShortCourse){
        	mapOverlays.add(new LinesOverlay(projection));
        }
        // -- <<
		// -->
		Drawable drawable1 = this.getResources().getDrawable(R.drawable.map_start);
		MapItemizedOverlay itemizedoverlay1 = new MapItemizedOverlay(drawable1,this,mapView);
		
		Drawable drawable2 = this.getResources().getDrawable(R.drawable.map_finish);
		MapItemizedOverlay itemizedoverlay2 = new MapItemizedOverlay(drawable2,this,mapView);
		
		Drawable drawable3 = this.getResources().getDrawable(R.drawable.rest_icon);
		MapItemizedOverlay itemizedoverlay3 = new MapItemizedOverlay(drawable3,this,mapView);
		
		Drawable drawable4 = this.getResources().getDrawable(R.drawable.rest_icon);
		MapItemizedOverlay itemizedoverlay4 = new MapItemizedOverlay(drawable4,this,mapView);
		
		Drawable drawable5 = this.getResources().getDrawable(R.drawable.rest_icon);
		MapItemizedOverlay itemizedoverlay5 = new MapItemizedOverlay(drawable5,this,mapView);
		
//		Drawable drawable6 = this.getResources().getDrawable(R.drawable.rest_icon);
//		MapItemizedOverlay itemizedoverlay6 = new MapItemizedOverlay(drawable6,this,mapView);
		//--<
		//-->
		GeoPoint point01 = new GeoPoint(-37820750,144969840);
        OverlayItem overlayitem01 = new OverlayItem(point01, "Start", "Alexander Garden");
        
        GeoPoint point02 = new GeoPoint(-37784330,144960690);
        OverlayItem overlayitem02 = new OverlayItem(point02, "Finish", "Royal Parade");
        //-->>
        GeoPoint point03 = new GeoPoint(-37839610,144916970);
        OverlayItem overlayitem03 = new OverlayItem(point03, "Rest Point 1", "Sandridge SLS Club, The Boulevard Port Melbourne");
        
        GeoPoint point04 = new GeoPoint(-37808280,144899850);
        OverlayItem overlayitem04 = new OverlayItem(point04, "Rest Point 2", "Corner of Lyons and Hyde Sts, Seedon");
        
        GeoPoint point05 = new GeoPoint(-37794220,144914240);
        OverlayItem overlayitem05 = new OverlayItem(point05, "Rest Point 3", "Junction of Smithfield Rd and the Maribyrnong River");
        
//        GeoPoint point06 = new GeoPoint(-37782320,144964080);
//        OverlayItem overlayitem06 = new OverlayItem(point06,"Rest Point 4", "Rest ");
        
        //--<
        //--<
        itemizedoverlay1.addOverlay(overlayitem01);
        itemizedoverlay2.addOverlay(overlayitem02);
        itemizedoverlay3.addOverlay(overlayitem03);
        itemizedoverlay4.addOverlay(overlayitem04);
        itemizedoverlay5.addOverlay(overlayitem05);
        //itemizedoverlay6.addOverlay(overlayitem06);
        
        //mapOverlays.add(itemizedoverlay6);
        if(LongCourse){
        	mapOverlays.add(itemizedoverlay5);
        }
        mapOverlays.add(itemizedoverlay4);
        mapOverlays.add(itemizedoverlay3);
        mapOverlays.add(itemizedoverlay2);
        mapOverlays.add(itemizedoverlay1);
        
        // control view of map
        MapController mapController = mapView.getController();
        mapController.setCenter(point01);
        mapController.setZoom(13);
	}
//=========================================================================
//TODO Final Destination
}
