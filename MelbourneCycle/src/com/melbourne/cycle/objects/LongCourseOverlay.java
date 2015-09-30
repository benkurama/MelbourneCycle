package com.melbourne.cycle.objects;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;
import com.melbourne.cycle.utils.Utils;

public class LongCourseOverlay extends Overlay{
	private Projection projection;
	private ArrayList<GeoPointObj> GeoPointList;
 //=========================================================================
	public LongCourseOverlay(Projection proj){
		
		this.projection = proj;
	}
 //=========================================================================
	public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when){
        super.draw(canvas, mapView, shadow);
        
        GeoPointList = new ArrayList<GeoPointObj>();
        // -- >>
        if(shadow){
     	   return false;
        }
        
        Paint   mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setColor(Color.rgb(0, 90, 171));
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
        setGeoPoints("-37.83497","144.88229");
        setGeoPoints("-37.83694","144.88237");
        
        setGeoPoints("-37.83711","144.88274");
        setGeoPoints("-37.83680","144.88297");
        setGeoPoints("-37.83611","144.88285");
        setGeoPoints("-37.83484","144.88143");
        setGeoPoints("-37.83118","144.84826");
        setGeoPoints("-37.85012","144.84499");
        setGeoPoints("-37.85517","144.86293");
        setGeoPoints("-37.85798","144.88971");
        setGeoPoints("-37.85639","144.89250");
        setGeoPoints("-37.84477","144.88469");
        
        setGeoPoints("-37.84443","144.88533");
        setGeoPoints("-37.84514","144.89229");
        setGeoPoints("-37.83900","144.89332");
        setGeoPoints("-37.83501","144.89473");
        setGeoPoints("-37.83168","144.89516");
        setGeoPoints("-37.83026","144.89559");
        setGeoPoints("-37.82887","144.89559");
        setGeoPoints("-37.82792","144.89473");
        setGeoPoints("-37.81284","144.89748");
        setGeoPoints("-37.80439","144.90139");
        
        setGeoPoints("-37.80673","144.90980");
        setGeoPoints("-37.80624","144.90990");
        setGeoPoints("-37.80549","144.90932");
        setGeoPoints("-37.80443","144.90881");
        setGeoPoints("-37.80076","144.91091");
        setGeoPoints("-37.80043","144.91147");
        setGeoPoints("-37.80041","144.91216");
        setGeoPoints("-37.80065","144.91261");
        setGeoPoints("-37.80122","144.91274");
        setGeoPoints("-37.80149","144.91490");// --- for long course
        
        setGeoPoints("-37.79422","144.92761");
        setGeoPoints("-37.77947","144.91357");
        setGeoPoints("-37.78408","144.90563");
        setGeoPoints("-37.78683","144.90501");
        setGeoPoints("-37.78761","144.90486");
        setGeoPoints("-37.78951","144.90314");
        setGeoPoints("-37.79040","144.90321");
        setGeoPoints("-37.79127","144.90381");
        setGeoPoints("-37.79166","144.90439");
        setGeoPoints("-37.79341","144.90881");
        
        setGeoPoints("-37.79459","144.91280");
        setGeoPoints("-37.79614","144.91546");
        setGeoPoints("-37.79571","144.91658");
        setGeoPoints("-37.79592","144.91769");
        setGeoPoints("-37.79851","144.92007");
        setGeoPoints("-37.80153","144.91490");
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
        setGeoPoints("-37.78457","144.95705");
        setGeoPoints("-37.78164","144.95739");
        setGeoPoints("-37.77974","144.95829");
        
        setGeoPoints("-37.77802","144.95992");
        setGeoPoints("-37.77594","144.94308");
        setGeoPoints("-37.77875","144.94254");
        setGeoPoints("-37.77892","144.94437");
        setGeoPoints("-37.77892","144.94542");
        setGeoPoints("-37.77942","144.94887");
        setGeoPoints("-37.78098","144.95123");
        setGeoPoints("-37.78148","144.95140");
        setGeoPoints("-37.78167","144.95245");
        setGeoPoints("-37.78198","144.95306");
        
        setGeoPoints("-37.78449","144.95610");
        setGeoPoints("-37.78477","144.96001");
        setGeoPoints("-37.78438","144.96067");
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
//=========================================================================
//TODO Final Destination
}
