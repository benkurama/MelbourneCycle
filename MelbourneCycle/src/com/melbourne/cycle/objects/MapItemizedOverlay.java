package com.melbourne.cycle.objects;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MapItemizedOverlay extends ItemizedOverlay<OverlayItem>{
 // =========================================================================
 // TODO Variables
 // =========================================================================	
	 private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	 private Context mContext;
	 private long systemTime = System.currentTimeMillis();
	 private MapController mapController;
// =========================================================================
// TODO Constructors
// =========================================================================		
	public MapItemizedOverlay(Drawable defaultMarker, Context context,MapView mapview)
	{
		 super(boundCenterBottom(defaultMarker));
		 mContext = context;
		 mapController = mapview.getController();
		 mapController.setZoom(12);
	}
//=========================================================================	
	public void addOverlay(OverlayItem overlay)
	{
		 mOverlays.add(overlay);
		 populate();
	}
 // =========================================================================
 // TODO Activity Life Cycle
 // =========================================================================	
	@Override
	protected OverlayItem createItem(int i)
	{
		 return mOverlays.get(i);
	}
//=========================================================================	
	@Override
	public int size()
	{
		 return mOverlays.size();
	}
//=========================================================================	
	@Override
	protected boolean onTap(int index)
	{
		OverlayItem item = mOverlays.get(index);
		 
		popupDialog(item.getTitle(),item.getSnippet());
		 
		// -- Old Codes
//		 AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
//		 dialog.setTitle(item.getTitle());
//		 dialog.setMessage(item.getSnippet());
//		 
//		 ImageView mv = new ImageView(mContext);
//		 mv.setImageResource(com.melbourne.cycle.R.drawable.envelopes);
//		 dialog.setView(mv);
//		 
//		 dialog.setPositiveButton("Close", new OnClickListener() {
//			public void onClick(DialogInterface dialog, int which) {
//				
//			}
//		});
//		 dialog.show();
		 // -- Reserved Codes
	//	 OverlayItem item = mOverlays.get(index);
	//	 GlobalVars.setMapInfo(item.getTitle());
	//	 mContext.startActivity(new Intent(mContext,MapInfoAct.class));
		 
		 return true;
	}
//=========================================================================	
	@Override
	public boolean onTouchEvent(MotionEvent event, MapView map) {
		
		switch (event.getAction()) {
		
	   case MotionEvent.ACTION_DOWN:
	       if ((System.currentTimeMillis() - systemTime) < ViewConfiguration.getDoubleTapTimeout()) {
	           //mapController.zoomInFixing((int) event.getX(), (int) event.getY());
	       }
	       break;
	   case MotionEvent.ACTION_UP:
	       systemTime = System.currentTimeMillis();
	       break;
	   }
	
	   return false;
	}
 // =========================================================================
 // TODO Functions
 // =========================================================================	
	public void popupDialog(String title,String description){
		
		final Dialog dialog = new Dialog(mContext,android.R.style.Theme_Dialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawableResource(com.melbourne.cycle.R.drawable.blanc);
		dialog.setContentView(com.melbourne.cycle.R.layout.maps_popup);
		// -- >>
		TextView Title = (TextView)dialog.findViewById(com.melbourne.cycle.R.id.tvMapTitle);
		TextView Description = (TextView)dialog.findViewById(com.melbourne.cycle.R.id.tvDescription);
		ImageView image = (ImageView)dialog.findViewById(com.melbourne.cycle.R.id.ivImage);
		Button Close = (Button)dialog.findViewById(com.melbourne.cycle.R.id.btnClose);
		// --
		Title.setText(title);
		Description.setText(description);
		
		if(title.equals("Rest Point 1")){
			image.setImageResource(com.melbourne.cycle.R.drawable.sandridge_map);	
		}
		if(title.equals("Rest Point 2")){
			image.setImageResource(com.melbourne.cycle.R.drawable.lyson_map);
		}
		if(title.equals("Rest Point 3")){
			image.setImageResource(com.melbourne.cycle.R.drawable.smithfield_map);
		}
		if(title.equals("Start")){
			image.setVisibility(View.GONE);
		}
		if(title.equals("Finish")){
			image.setVisibility(View.GONE);
		}
		
		Close.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			dialog.dismiss();	
			}
		});
		// -- <<
		dialog.setCancelable(true);
		dialog.show();
	}
//=========================================================================	
//TODO Final Destination
}
