package com.melbourne.cycle.utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;

public class ActiveCustom extends Activity{
 // =========================================================================
 // TODO Variables
 // =========================================================================	
	public static final String FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION = "com.melbourne.cycle.utils.FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION";
	private BaseActivityReceiver baseActivityReceiver = new BaseActivityReceiver();
	public static final IntentFilter INTENT_FILTER = createIntentFilter();
	
	public static final long DISCONNECT_TIMEOUT = 300000; // 5 min = 5 * 60 * 1000 ms
	
 // =========================================================================
 // TODO Activity Life Cycle
 // =========================================================================	
    @Override
    public void onUserInteraction(){
        resetDisconnectTimer();
    	//Utils.MessageToast("Hello", ActiveCustom.this);
    }
 // =========================================================================
    @Override
    public void onResume() {
        super.onResume();
        resetDisconnectTimer();
    }
 // =========================================================================
    @Override
    public void onStop() {
        super.onStop();
        stopDisconnectTimer();
    }
 // =========================================================================
 // TODO Functions
 // =========================================================================
	private static IntentFilter createIntentFilter(){
		IntentFilter filter = new IntentFilter();
		filter.addAction(FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION);
		return filter;
	}
	// =========================================================================
	protected void registerBaseActivityReceiver() {
		registerReceiver(baseActivityReceiver, INTENT_FILTER);
	}
	// =========================================================================
	protected void unRegisterBaseActivityReceiver() {
		unregisterReceiver(baseActivityReceiver);
	}
	// =========================================================================
	protected void closeAllActivities(){
		sendBroadcast(new Intent(FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION));
	}
 // =========================================================================
 // TODO Classes
 // =========================================================================
	public class BaseActivityReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals(FINISH_ALL_ACTIVITIES_ACTIVITY_ACTION)){
				finish();
			}
		}
	} 
 // =========================================================================
 // TODO Thread Functions
 // =========================================================================	
    private Handler disconnectHandler = new Handler(){
        public void handleMessage(Message msg) {
        }
    };
 // =========================================================================
    public void resetDisconnectTimer(){
        disconnectHandler.removeCallbacks(disconnectCallback);
        disconnectHandler.postDelayed(disconnectCallback, DISCONNECT_TIMEOUT);
    }
 // =========================================================================
    public void stopDisconnectTimer(){
        disconnectHandler.removeCallbacks(disconnectCallback);
    }
// =========================================================================
// TODO Runnable Functions
// =========================================================================	
    private Runnable disconnectCallback = new Runnable() {
        public void run() {
        	//
        	Utils.MessageToast("No User Interaction, Kill the App", ActiveCustom.this);
        	//android.os.Process.killProcess(android.os.Process.myPid());
        	disconnectHandler.postDelayed(KillTheApp, 5000);
        }
    };
    // =========================================================================
    private Runnable KillTheApp = new Runnable() {
        public void run() {
        	//
        	//ActiveCustom.this.finish();
        	closeAllActivities();
        }
    };
 // =========================================================================
 // TODO Final Destination
}
