package com.melbourne.cycle;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.melbourne.cycle.utils.ActiveCustom;
import com.melbourne.cycle.utils.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class DonateInAppAct extends ActiveCustom implements OnFocusChangeListener,OnItemSelectedListener{
	 // =========================================================================
	 // TODO Variables
	 // =========================================================================	
		EditText edtAmount,edtDisplayName,edtMessage,edtFirstName,edtLastName,edtMobile,edtHome,edtOffice,edtFax,edtEmail
		,edtNameCard,edtCardNo,edtMonth,edtYear,edtSecurity;
		TextView txtTitle, txtAmount, mobReq, homeReq, officeReq;
	////-----------------------////
		private static final String TAG = "Melbourne";
	////-----------------------////
		Spinner spnTitle, spnPrimary,spnCard;
		String itemSel;
		Integer PrimaryContact;
		ArrayAdapter titleAdap,primeAdap,cardAdap;
	////-----------------------////
		String[] items = { "Mr.", "Miss", "Madam", "Dr.","Master","Captain","Major","Mrs.","Ms." };
		String[] primary = { "Mobile Number", "Home Telephone", "Office Telephone"};
		String str;
	 // =========================================================================
	 // TODO Activity Life Cycle
	 // =========================================================================		
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.donate_in_apps);
	        registerBaseActivityReceiver();
	        
	        setupControls();//--()
	        
	    }
	 // =========================================================================  
	    @Override
		protected void onDestroy() {
			super.onDestroy();
			unRegisterBaseActivityReceiver();
		}
	 // =========================================================================
	 // TODO onClick View
	 // =========================================================================
	    public void onBack(View v){
	    	
	    	this.finish();
	    }
	  // =========================================================================  
	    public void onSubmit(View v){
	    	//-->
	    	if(isOnline()){
	    	
	    	String amount = edtAmount.getText().toString();
	    	String displayName = edtDisplayName.getText().toString();
	    	String message = edtMessage.getText().toString();
	    	String title = spnTitle.getSelectedItem().toString();
	    	String firstName = edtFirstName.getText().toString();
	    	String lastName = edtLastName.getText().toString();
	    	
	    	String mobile = edtMobile.getText().toString();
	    	String home = edtHome.getText().toString();
	    	String office = edtOffice.getText().toString();
	    	String primary = "";
	    	Integer primaryDetail = null;
	    	
	    	String fax = edtFax.getText().toString();
	    	String email = edtEmail.getText().toString();
	    	
	    	String card = edtNameCard.getText().toString();
	    	String cardNo = edtCardNo.getText().toString();
	    	String month = edtMonth.getText().toString();
	    	String year = edtYear.getText().toString();
	    	String security = edtSecurity.getText().toString();
	    	//--<
	    	//-->
	    	switch (PrimaryContact){
		    	case 1:
		    		
		    		primary = mobile;
		    		primaryDetail = 1;
		    		break;
		    	case 2:
		    		
		    		primary = home;
		    		primaryDetail = 2;
			    	break;
		    	case 3:
		    		
		    		primary = office;
		    		primaryDetail = 3;
			    	break;
	    	}
	    	//--<
	    	if((amount.length() != 0) && (firstName.length() != 0) && (lastName.length() != 0) && (primary.length() != 0) && (email.length() != 0)
	    			&& (card.length() != 0) && (cardNo.length() != 0) && (month.length() !=0) && (year.length() != 0) && (security.length() != 0)){

		    	//--
		    	String URL = "http://10.10.10.15:57772/csp/melcycle/Mobile.Donation.cls?soap_method=PayWay"
		    	    	+"&UserID=" + GloVars.getUserName(this)
		    	    	
		    	    	+"&T1="+ amount
		    	    	+"&T2="+ displayName
		    	    	+"&T3="+ message
		    	    	+"&T4="+ title
		    	    	+"&T5="+ firstName
		    	    	+"&T6="+ lastName
		    	    	+"&T7="+ mobile
		    	    	+"&T8="+ home
		    	    	+"&T9="+ office
		    	    	+"&T10="+ primary
		    	    	+"&T11="+ fax
		    	    	+"&T12="+ email
		    	    	
		    	    	+"&T13="+ card
		    	    	+"&T14="+ cardNo
		    	    	+"&T15="+ month
		    	    	+"&T16="+ year
		    	    	+"&T17="+ security;

		    	//--
		    	getHtmlCode(URL);
				//--
	    	}else{
	    		//-->
	    		if(amount.length() == 0){ edtAmount.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.error),null);} else {edtAmount.setCompoundDrawablesWithIntrinsicBounds(null, null, null,null);}
	    		if(firstName.length() == 0){ edtFirstName.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.error),null);} else {edtFirstName.setCompoundDrawablesWithIntrinsicBounds(null, null, null,null);}
	    		if(lastName.length() == 0){ edtMobile.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.error),null);} else {edtLastName.setCompoundDrawablesWithIntrinsicBounds(null, null, null,null);}
	    		
	    		switch(primaryDetail){
	    		case 1:
	    			if(mobile.length() == 0){ edtLastName.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.error),null);} else {edtMobile.setCompoundDrawablesWithIntrinsicBounds(null, null, null,null);}
	    			edtHome.setCompoundDrawablesWithIntrinsicBounds(null, null, null,null);
	    			edtOffice.setCompoundDrawablesWithIntrinsicBounds(null, null, null,null);
	    			break;
	    		case 2:
	    			if(home.length() == 0){ edtHome.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.error),null);} else {edtHome.setCompoundDrawablesWithIntrinsicBounds(null, null, null,null);}
	    			edtMobile.setCompoundDrawablesWithIntrinsicBounds(null, null, null,null);
	    			edtOffice.setCompoundDrawablesWithIntrinsicBounds(null, null, null,null);
	    			break;
				case 3:
					if(office.length() == 0){ edtOffice.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.error),null);} else {edtOffice.setCompoundDrawablesWithIntrinsicBounds(null, null, null,null);}
					edtHome.setCompoundDrawablesWithIntrinsicBounds(null, null, null,null);
	    			edtMobile.setCompoundDrawablesWithIntrinsicBounds(null, null, null,null);
					break;
	    		}
	    		
	    		if(email.length() == 0){ edtEmail.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.error),null);} else {edtEmail.setCompoundDrawablesWithIntrinsicBounds(null, null, null,null);}
	    		if(card.length() == 0){ edtNameCard.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.error),null);} else {edtNameCard.setCompoundDrawablesWithIntrinsicBounds(null, null, null,null);}
	    		if(cardNo.length() == 0){ edtCardNo.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.error),null);} else {edtCardNo.setCompoundDrawablesWithIntrinsicBounds(null, null, null,null);}
	    		if(month.length() == 0){ edtMonth.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.error),null);} else {edtMonth.setCompoundDrawablesWithIntrinsicBounds(null, null, null,null);}
	    		if(year.length() == 0){ edtYear.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.error),null);} else {edtYear.setCompoundDrawablesWithIntrinsicBounds(null, null, null,null);}
	    		if(security.length() == 0){ edtSecurity.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.error),null);} else {edtSecurity.setCompoundDrawablesWithIntrinsicBounds(null, null, null,null);}
	    		//--<
	    		//-->
	    		AlertDialog.Builder alert = new AlertDialog.Builder(this);
	    		alert.setTitle("ERROR!");
	     		alert.setMessage("Fill in the fields that were marked");
	     		alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
	     	    public void onClick(DialogInterface dialog, int which) {
	     	        //return;   
	     	    }});
	     		alert.show();
	    		//--<
	     		
	    	}
	    	
	    	}else{
	    		
	    		Utils.MessageBox("Either slow or no internet connection", this);
	    	}
	    }
	 // =========================================================================
	 // TODO Implementation
	 // =========================================================================
	    public void onFocusChange(View v, boolean hasFocus) {
	    	
	    	switch(v.getId()){
	    	
	    	case R.id.etxtAmount:
	    		
	    		if(!hasFocus){
	        		
	        		if(edtAmount.getText().length() != 0){
	        		
	    	    		Double Amount =   Double.valueOf((edtAmount.getText().toString()));
	    	    		
	    	    		DecimalFormat dec = new DecimalFormat("#,###,###,###,###.00");
	    	    		
	    	    		txtAmount.setText("$ "+ dec.format(Amount));
	        		}
	        	}
	    		break;
	    		
	    	case R.id.etxtMonth:
	    		
	    		if(!hasFocus){
	    			
	    			if(edtMonth.getText().length() !=0 ){
	    				//--
	    				if (edtMonth.getText().length() == 1){
	    					
	    					edtMonth.setText("0" + edtMonth.getText().toString());
			    		}
	    				
	    				String Month = edtMonth.getText().toString();
	    				
			    		Integer set = Integer.parseInt(Month);
			    		//--
			    		if(set == 00){
			    			
			    			edtMonth.setText("01");
			    		}
			    		//--
			    		if (set > 12 && set > 00){
			    			
			    			edtMonth.setText("12");
			    		}
	    			}
	    		}
	    		break;
	    		
	    	case R.id.etxtSecurityCode:
	    		
	    		if(!hasFocus){
	    			
	    			if(edtSecurity.getText().length() != 0){
	    				
	    				if(edtSecurity.getText().length() < 3){
	    					
	    					Utils.MessageBox("Minimum of 3 numbers", this);
	    				}
	    			}
	    		}
	    		break;
	    	}
	    	
	    }
	 // =========================================================================   
	    public void onItemSelected(AdapterView<?> parent, View v, int pos, long arg3) {
	    	
	    	switch (parent.getId()) {
	    	
	    	case R.id.spnTitle:
	    		
	    		itemSel = items[pos];
	    		break;
	    		
	    	case R.id.spnPrimary:
	    		
	    		if(primary[pos].toString().equals("Mobile Number")){
	    			
	    			mobReq.setVisibility(View.VISIBLE);
	    			homeReq.setVisibility(View.INVISIBLE);
	    			officeReq.setVisibility(View.INVISIBLE);
	    			
	    			PrimaryContact = 1;
	    		}
	    		
	    		if(primary[pos].toString().equals("Home Telephone")){
	    			
	    			mobReq.setVisibility(View.INVISIBLE);
	    			homeReq.setVisibility(View.VISIBLE);
	    			officeReq.setVisibility(View.INVISIBLE);
	    			
	    			PrimaryContact = 2;
	    		}
	    		
	    		if(primary[pos].toString().equals("Office Telephone")){
	    			
	    			mobReq.setVisibility(View.INVISIBLE);
	    			homeReq.setVisibility(View.INVISIBLE);
	    			officeReq.setVisibility(View.VISIBLE);
	    			
	    			PrimaryContact = 3;
	    		}
	    		
	    		break;	
	    	}
	    }
	 // --------------------------------------------------------------------------
	    public void onNothingSelected(AdapterView<?> parent) {
	    	
	    }
	 // =========================================================================
	 // TODO Functions
	 // =========================================================================
	    public void setupControls(){
	    	
	    	
	    	txtTitle = (TextView)findViewById(R.id.tvTitle);
	        txtTitle.setText("In App Donate");
	        
	        txtAmount = (TextView)findViewById(R.id.txtAmount);
	        //-->
	        edtAmount = (EditText)findViewById(R.id.etxtAmount);
	        edtAmount.requestFocus();
	        edtAmount.setOnFocusChangeListener(this);
	        //--<
	        edtDisplayName = (EditText)findViewById(R.id.etxtDisplayName);
	        
	        edtMessage = (EditText)findViewById(R.id.etxtMessage);
	        //-->
	        spnTitle = (Spinner)findViewById(R.id.spnTitle);
	        spnTitle.setOnItemSelectedListener(this);
	        titleAdap = new ArrayAdapter(this,android.R.layout.simple_spinner_item, items);
	        titleAdap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spnTitle.setAdapter(titleAdap);
			//--<
			edtFirstName = (EditText)findViewById(R.id.etxtFirstName);
			
			edtLastName = (EditText)findViewById(R.id.etxtLastName);
			//-->
			spnPrimary = (Spinner)findViewById(R.id.spnPrimary);
			spnPrimary.setOnItemSelectedListener(this);
			primeAdap = new ArrayAdapter(this,android.R.layout.simple_spinner_item, primary);
			primeAdap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spnPrimary.setAdapter(primeAdap);
			//--<
			
			//-->
			mobReq = (TextView)findViewById(R.id.txtMobReq);
			homeReq = (TextView)findViewById(R.id.txtHomeReq);
			officeReq = (TextView)findViewById(R.id.txtOfficeReq);
			
			mobReq.setVisibility(View.INVISIBLE);
			homeReq.setVisibility(View.INVISIBLE);
			officeReq.setVisibility(View.INVISIBLE);
			//--<
			edtMobile = (EditText)findViewById(R.id.etxtMobile);
			
			edtHome = (EditText)findViewById(R.id.etxtHome);
			
			edtOffice = (EditText)findViewById(R.id.etxtOffice);
			
			edtFax = (EditText)findViewById(R.id.etxtFax);
			
			edtEmail = (EditText)findViewById(R.id.etxtEmail);
			//-------------
			edtNameCard = (EditText)findViewById(R.id.etxtCardName);
			//-->
			//--<
			
			edtCardNo = (EditText)findViewById(R.id.etxtCardNo);
			
			edtMonth = (EditText)findViewById(R.id.etxtMonth);
			edtMonth.setOnFocusChangeListener(this);
			
			edtYear = (EditText)findViewById(R.id.etxtYear);
			
			edtSecurity = (EditText)findViewById(R.id.etxtSecurityCode);
			edtSecurity.setOnFocusChangeListener(this);
			
	    }
	 // =========================================================================
	    public void getHtmlCode(String Url) {
	    	
			try {
				
				URL url = new URL(Url);
	    	
		        URLConnection con = url.openConnection();
		        Pattern p = Pattern.compile("text/html;\\s+charset=([^\\s]+)\\s*");
		        Matcher m = p.matcher(con.getContentType());
		        /* If Content-Type doesn't match this pre-conception, choose default and hope for the best.  */
		        String charset = m.matches() ? m.group(1) : "ISO-8859-1";
		        Reader r;
				
				r = new InputStreamReader(con.getInputStream(), charset);
				StringBuilder buf = new StringBuilder();
				
				
		        while (true) {
		        	
		        	int ch = r.read();
		        	if (ch < 0)
		            break;
		        	buf.append((char) ch);
		        	
		        	str = buf.toString();
		        }
		        
		        setTitle(str);
		        
		        String[] filterArrayOne = str.split("<PayWayResult>");
		        String[] filterArrayTwo = filterArrayOne[1].split(":");
		        //setTitle(filterArrayTwo[0]);
		        
		        if (filterArrayTwo[0].equals("0")){
		        	
		        	//GlobalVars.MessageBox("Donation Successfully Submitted", this);
		        	Toast.makeText(this, "Donation Successfully Submitted", Toast.LENGTH_LONG).show();
		        	this.finish();
		        }else{
		        	Toast.makeText(this, "Donation Failed", Toast.LENGTH_LONG).show();
		        	//GlobalVars.MessageBox("Donation Failed", this);
		        }
	        
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	 // ======================================================================== 
		public boolean isOnline() {
			
			boolean haveConnectedWifi = false,haveConnectedMobile = false;
			
		    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		    
		    for (NetworkInfo ni : netInfo) {
		        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
		            if (ni.isConnected())
		                haveConnectedWifi = true;
		        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
		            if (ni.isConnected())
		                haveConnectedMobile = true;
		    }
		    return haveConnectedWifi || haveConnectedMobile;
		}
	 // ========================================================================
	 // TODO Final Destination
}
