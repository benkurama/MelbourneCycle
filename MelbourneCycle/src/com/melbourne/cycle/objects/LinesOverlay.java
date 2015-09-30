package com.melbourne.cycle.objects;

import java.util.ArrayList;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;
import com.melbourne.cycle.GloVars;
import com.melbourne.cycle.R;
import com.melbourne.cycle.utils.Utils;

public class LinesOverlay extends Overlay{

	private Projection projection;
	private ArrayList<GeoPointObj> GeoPointList;
 //=========================================================================
	public LinesOverlay(Projection proj){
		
		this.projection = proj;
	}
 //=========================================================================
	public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when){
        super.draw(canvas, mapView, shadow);
        
        GeoPointList = new ArrayList<GeoPointObj>();
        
       if(shadow){
    	   return false;
       }
        // -- >>
        Paint   mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setColor(Color.rgb(242, 101, 34));
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(6);
        
        // -- >>
        setGeoPoints("-37.82075","144.96984");
        setGeoPoints("-37.82076","144.96837");
        setGeoPoints("-37.82285","144.96353");
        setGeoPoints("-37.82582","144.96027");
        setGeoPoints("-37.82657","144.96039");
        setGeoPoints("-37.83229","144.96305");
        setGeoPoints("-37.83619","144.94956");
        setGeoPoints("-37.83614","144.94876");
        setGeoPoints("-37.84304","144.94552");
        setGeoPoints("-37.84248","144.94452");
        
        setGeoPoints("-37.84414","144.94293");
        setGeoPoints("-37.84006","144.93574");
        setGeoPoints("-37.83834","144.93731");
        setGeoPoints("-37.83804","144.93666");
        setGeoPoints("-37.83765","144.93623");
        setGeoPoints("-37.83734","144.93486");
        setGeoPoints("-37.83753","144.93383");
        setGeoPoints("-37.83784","144.93338");
        setGeoPoints("-37.84022","144.93119");
        setGeoPoints("-37.83912","144.92576");
        
        setGeoPoints("-37.83865","144.92514");
        setGeoPoints("-37.83922","144.92465");
        setGeoPoints("-37.83922","144.91741");
        setGeoPoints("-37.83941","144.91565");
        setGeoPoints("-37.83531","144.91430");
        setGeoPoints("-37.83356","144.91291");
        setGeoPoints("-37.83228","144.91726");
        setGeoPoints("-37.83182","144.91733");
        setGeoPoints("-37.83167","144.91686");
        setGeoPoints("-37.83231","144.91486");
        
        setGeoPoints("-37.83362","144.91149");
        setGeoPoints("-37.83384","144.90817");
        setGeoPoints("-37.83285","144.90387");
        setGeoPoints("-37.82704","144.89385");
        setGeoPoints("-37.82555","144.88948");
        setGeoPoints("-37.82533","144.88647");
        setGeoPoints("-37.82601","144.88227");
        setGeoPoints("-37.82619","144.88104");// --- for long course
        setGeoPoints("-37.82926","144.88134");
        setGeoPoints("-37.83053","144.89276");
        
        setGeoPoints("-37.82858","144.89315");
        setGeoPoints("-37.82880","144.89561");
        setGeoPoints("-37.82762","144.89482");
        setGeoPoints("-37.81339","144.89739");
        setGeoPoints("-37.80431","144.90143");
        setGeoPoints("-37.80692","144.91020");
        setGeoPoints("-37.80509","144.90877");
        setGeoPoints("-37.80093","144.91076");
        setGeoPoints("-37.80049","144.91128");
        setGeoPoints("-37.80037","144.91186");
        
        setGeoPoints("-37.80058","144.91252");
        setGeoPoints("-37.80122","144.91276");// --- for long course
        setGeoPoints("-37.80148","144.91405");
        setGeoPoints("-37.80310","144.92911");
        setGeoPoints("-37.80139","144.93168");
        setGeoPoints("-37.80121","144.93254");
        setGeoPoints("-37.80053","144.93293");
        setGeoPoints("-37.79931","144.93503");
        setGeoPoints("-37.80134","144.95280");
        setGeoPoints("-37.79958","144.95520");
        
        setGeoPoints("-37.79973","144.95752");
        setGeoPoints("-37.78774","144.95898");
        setGeoPoints("-37.78759","144.95709");
        setGeoPoints("-37.78457","144.95705");// --- for long course
        setGeoPoints("-37.78474","144.95936");
        setGeoPoints("-37.78477","144.96007");
        setGeoPoints("-37.78433","144.96069");
        
        // -- >>
        GeoPoint[] geoPoint = new GeoPoint[GeoPointList.size()];
        for(int x = 0;x < GeoPointList.size();x++){
        	
        	geoPoint[x] = new GeoPoint(GeoPointList.get(x).LATITUDE, GeoPointList.get(x).LONGTITUDE);
        }
        // -- >>
        Point[] points = new Point[GeoPointList.size()];
        for(int x = 0;x < GeoPointList.size();x++){
        	
        	points[x] = new Point();
        }
        // -- >>
        Path path = new Path();
        // -->>
        for(int x = 0;x < GeoPointList.size();x++){
        	
        	projection.toPixels(geoPoint[x], points[x]);
        	
        }
        // --
        Utils.LogCat(" "+points.length + " " + GeoPointList.size());
        // -- >> Plot a Path
        path.moveTo(points[0].x,points[0].y);
        for (int x = 1;x < (points.length - 1);x++){
        	path.lineTo(points[x].x, points[x].y);  path.moveTo(points[x].x, points[x].y);
        }
        // -- >>
        canvas.drawPath(path, mPaint);
        
        Utils.LogCat("Draw Calls");
        
        return false;
    }
//=========================================================================
	public void setGeoPoints(String latitude, String longtitude){
		
		// -- >>
		// latitude convertion
		String latString1 = latitude;
		String[] latString2 = latString1.split("\\.");
		String latString3 = latString2[0] + latString2[1];
		latString3 = latString3 +"0";
		int latitudeInt = Integer.parseInt(latString3);
		// longtitude convertion
		String longString1 = longtitude;
		String[] longString2 = longString1.split("\\.");
		String longString3 = longString2[0] + longString2[1];
		longString3 = longString3 + "0";
		int longtitudeInt = Integer.parseInt(longString3);
		// -- <<
		GeoPointObj points = new GeoPointObj();
		
		points.LATITUDE = latitudeInt;
		points.LONGTITUDE = longtitudeInt;
		
		GeoPointList.add(points);
	}
	
	public void setGeoP(int latitude,int longtitude){
		
		GeoPointObj points = new GeoPointObj();
		
		points.LATITUDE = latitude;
		points.LONGTITUDE = longtitude;
		
		GeoPointList.add(points);
	}
//=========================================================================
//TODO Final Destination
}
