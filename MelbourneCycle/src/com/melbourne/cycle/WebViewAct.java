package com.melbourne.cycle;

import com.melbourne.cycle.utils.ActiveCustom;
import com.melbourne.cycle.utils.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewAct extends ActiveCustom {
 // =========================================================================
 // TODO Variables
 // =========================================================================	
	private WebView Web;
    private String url;
 // =========================================================================
 // TODO Activity Life Cycle
 // =========================================================================		
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_act);
        registerBaseActivityReceiver();
     
        SetupControls();
        
        Web.setWebViewClient(webViewClient);
        Web.getSettings().setSupportZoom(true);
        Web.getSettings().setBuiltInZoomControls(true);
        Web.getSettings().setJavaScriptEnabled(true);
        
//		if(getIntent().getData() != null) {
//			url = getIntent().getData().toString();
//		}
        url = GloVars.getWebLink(this);
		
		Web.clearCache(true);
		Web.loadUrl(this.url);
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
		
		// Animation transition
		this.overridePendingTransition(R.anim.slide_right_in,R.anim.slide_right_out);	
		GloVars.setPageNavi(false);
    }
 // =========================================================================
 // TODO onClick View
 // =========================================================================

 // =========================================================================
 // TODO Functions
 // =========================================================================
    public void SetupControls(){
    	
    	Web = (WebView) findViewById(R.id.wvWeb);
    }
 // ========================================================================= 
	private void showError() {
		new AlertDialog.Builder(this)
		.setTitle("Error")
		.setMessage("Error Loading...")
		.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		})
		.create().show();
	}
 // ========================================================================= 
	public void onRefresh() {
		if (!TextUtils.isEmpty(this.url)) {
			Web.reload();
		}
	}
 // ========================================================================= 
	public void clearWebView() {
		Web.clearView();
	}
 // ========================================================================= 
	public void clearCache(boolean includeDiskFiles) {
		Web.clearCache(includeDiskFiles);
	}
 // =========================================================================
 // TODO New Class
 // =========================================================================
    /**
	 * Implement our own WebViewClient to handle loading and displaying progress
	 * bar for the webView.
	 */
    private WebViewClient webViewClient = new WebViewClient() {
    	
	private int webViewPreviousState;
	private final int PAGE_STARTED = 0x1;
	private final int PAGE_REDIRECTED = 0x2;
	private ProgressDialog dialog;
	private boolean isDone;
	// =========================================================================
	@Override
	public boolean shouldOverrideUrlLoading(WebView view,
			String urlNewString) {
		webViewPreviousState = PAGE_REDIRECTED;
		Web.loadUrl(urlNewString);
		return true;
	}
	// =========================================================================
	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		super.onPageStarted(view, url, favicon);
		webViewPreviousState = PAGE_STARTED;
		if (dialog == null || !dialog.isShowing()) {
			dialog = Utils.showProgressDialog(WebViewAct.this, null,
					"Loading..", true, true, null);
		}

	}
	// =========================================================================
	@Override
	public void onPageFinished(WebView view, String url) {
		isDone = true;

		if (webViewPreviousState == PAGE_STARTED && dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}
	// =========================================================================
	@Override
	public void onReceivedError(WebView view, int errorCode,
			String description, String failingUrl) {
		super.onReceivedError(view, errorCode, description, failingUrl);
		// in case we want to intercept and handle other errors, we do it
		// here
		showError();
	}
    };
 // =========================================================================
 // TODO Final Destination
}
