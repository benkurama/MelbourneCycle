package com.melbourne.cycle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.melbourne.cycle.objects.ContactAdap;
import com.melbourne.cycle.objects.ContactExt;
import com.melbourne.cycle.objects.ContactObj;
import com.melbourne.cycle.objects.ContactViewHolder;
import com.melbourne.cycle.utils.ActiveCustom;
import com.melbourne.cycle.utils.ContactUtils;
import com.melbourne.cycle.utils.Properties;
import com.melbourne.cycle.utils.Utils;

public class ContactsAct extends ActiveCustom implements OnItemClickListener{
 // =========================================================================
 // TODO Variables
 // =========================================================================	
	private ArrayList<ContactObj> ListContacts;
	private ArrayAdapter<ContactExt> AdapterList;
////-----------------------////
	private ListView ContactList,lstManual,AlphaList;
	private Button Send;
    private EditText txtManual;
////-----------------------////
	private String EmailList = "";
	private String NumberList = "";
	private ArrayList<String> manualContacts;
	private String letterCon = "All,";
	private boolean initLetter = true;
	private ListAdapter AlphaAdap;
	
	ArrayList<HashMap<String, String>> ArrayLetters = new ArrayList<HashMap<String, String>>();
 // =========================================================================
 // TODO Activity Life Cycle
 // =========================================================================		
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_act);
        registerBaseActivityReceiver();
        
        SetupControls();
        //GloVars.setNumNull();
        
        ListContacts = getContacts();
        
        callListView();
       
        loadAlphaListView();
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
		// --  Option to send in SMS or EMAIL
		if(GloVars.getSendType(getBaseContext()).toString().equals("SMS")){
				
//			for(String ManConts: manualContacts ){
//				GloVars.setNumList(ManConts + " ");
//			}
			
			for(String ManConts: manualContacts ){
				NumberList += ManConts + ",";
			}
		}
		
		if(GloVars.getSendType(getBaseContext()).toString().equals("EMAIL")){
			
			for(String ManConts: manualContacts ){
				EmailList += ManConts + ", ";
			}
		}
    }
 // =========================================================================
 // TODO Menu Methods
 // =========================================================================
  	public boolean onCreateOptionsMenu(Menu menu){
  	
  	MenuInflater inflater = getMenuInflater();
  	inflater.inflate(R.menu.option, menu);
  	
  	return true;
  	
  }
  //=========================================================================	
   @Override
  	public boolean onOptionsItemSelected(MenuItem item) {
  	
      switch (item.getItemId()) {
      
       	case R.id.manual:
    	   
    	   setupDialog();
    	   break;

       	case R.id.refresh:
       		
    	   this.finish();
    	   startActivity(new Intent(this,ContactsAct.class));
    	   Utils.MessageToast("Refresh Successful", this);
    	   break;
    	   
        case R.id.selectAll:
       	   
   		   for(int i=0; i < ListContacts.size(); i++){
       		   
   			ListContacts.get(i).setTrue();
       		}
   		   //--() List all data in listview function
              callListView();
              break;
              
         case R.id.unSelectAll:
          	
       	   for(int i=0; i < ListContacts.size(); i++){
       		   
       		ListContacts.get(i).setFalse();
       		}
   		   //--() List all data in listview function
              callListView();
              break;
      }
      return true;
  }
   
 // =========================================================================
 // TODO onClick View
 // =========================================================================
    public void onBack(View v){
    	this.finish();
    }
 // =========================================================================
    public void onClick(View v){

    	if(GloVars.getSendType(this).equals("SMS")){
    		
    		sendSMS();
			
    	}else if(GloVars.getSendType(this).equals("EMAIL")){
    	
    		sendEMAIL();
    	}
    }
 // =========================================================================   
    public void onOption(View v){
    	openOptionsMenu();
    }
 // =========================================================================
 // TODO Implementation
 // =========================================================================
	public void onItemClick(AdapterView<?> arg0, View item, int position, long arg3) {
		
		ContactExt contact = AdapterList.getItem( position );
		contact.toggleChecked();
        
        ContactViewHolder viewHolder = (ContactViewHolder) item.getTag();
        viewHolder.getCheckBox().setChecked( contact.isChecked() );
        
        if (contact.isChecked()){
        	
        	ListContacts.get(position).Check = true;
        }else{
        	
        	ListContacts.get(position).Check = false;
        }
	}
 // =========================================================================
 // TODO Functions
 // =========================================================================
    @SuppressLint("NewApi")
	public void SetupControls(){
    	
    	ContactList = (ListView) findViewById(R.id.lvContacts);
    	Send = (Button) findViewById(R.id.btnSend);
    	Send.setText("Send Message");
    	
    	manualContacts = new ArrayList<String>();
    	
    	
    	AlphaList = (ListView)findViewById(R.id.lvAlpha);
    	AlphaList.setDivider(null);
    	AlphaList.setDividerHeight(0);
    	
    }
 // =========================================================================
    public ArrayList<ContactObj> getContacts(){
    	
    	ArrayList<ContactObj> list = new ArrayList<ContactObj>();
    	
    	if(GloVars.getSendType(this).equals("SMS")){
    		
    		list = ContactUtils.getContactNumber(this);
    	}else if(GloVars.getSendType(this).equals("EMAIL")){
    	
    		list = ContactUtils.getContactEmail(this);
    	}
    	
    	return list;
    }
// =========================================================================
    public void callListView(){
    	// Find the ListView resource. 
        ArrayList<ContactExt> contactLists = new ArrayList<ContactExt>();
        
        setLetterCaption();
    	//-->
        for (ContactObj contacting : ListContacts) { 
        	
        	String letter = contacting.Name.trim();
        	letter = letter.substring(0,1).toUpperCase();
        	
        	ContactExt user = new ContactExt(contacting.Name,contacting.NumMail,contacting.Photo,contacting.Check,contacting.Visible,contacting.Letter);
        	contactLists.add(user);
            
		}
        //--<
        
        AdapterList = new ContactAdap(this, contactLists);
        ContactList.setAdapter(AdapterList);
        ContactList.setOnItemClickListener(this);
    }
 // =========================================================================    
    public void callAlphaLV(int pos){
    	
    	String let = ArrayLetters.get(pos).get("letter");
    	// Find the ListView resource. 
        ArrayList<ContactExt> contactLists = new ArrayList<ContactExt>();
        
        setLetterCaption();
    	//-->
        for (ContactObj contacting : ListContacts) { 
        	
        	String letter = contacting.Name.trim();
        	letter = letter.substring(0,1).toUpperCase();
        	
        	if(let.equals(letter)){
        		
	        	ContactExt user = new ContactExt(contacting.Name,contacting.NumMail,contacting.Photo,contacting.Check,contacting.Visible,contacting.Letter);
	        	contactLists.add(user);
        	}else if (let.equals("All")){
        		
        		ContactExt user = new ContactExt(contacting.Name,contacting.NumMail,contacting.Photo,contacting.Check,contacting.Visible,contacting.Letter);
	        	contactLists.add(user);
        	}
		}
        //--<
        AdapterList = new ContactAdap(this, contactLists);
        ContactList.setAdapter(AdapterList);
    }
 // ========================================================================= 
    public void setLetterCaption(){
    	///---> set if the Alphabetical order is displayed repeatedly
    	
    	
        for (ContactObj ViewChage : ListContacts){
        	
        	String letter = ViewChage.Name.trim();
        	letter = letter.substring(0, 1).toUpperCase();
        	
        	//-->
        	if(!Utils.detectNumber(letter)){
        		ViewChage.Letter = letter;
        		
        	}else{
        		ViewChage.Letter = " ";
        	}
        	//--<
        	//-->
        	if(letter.equals(GloVars.getLetter(this))){
        		
        		ViewChage.setInvisible();
        	}else{
        		
        		ViewChage.setVisible();
        		
        		if(initLetter){
        			letterCon += letter + ",";
        			
        		}
        	}
        	//--<
        	GloVars.setLetter(letter, this);
        	
        }
        initLetter = false;
    }
  // =========================================================================  
    public void sendSMS(){
    	
    	for (ContactObj listSamp : ListContacts) {
			
			if (listSamp.Check) {
				
					//GloVars.setNumList(listSamp.NumMail + ", ");
				NumberList += listSamp.NumMail + ",";
			}	
		}
		//startActivity(new Intent(this,SendSMSAct.class));
    	
    	Uri sms_uri = Uri.parse("smsto:" + NumberList); 
        Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri); 
        sms_intent.putExtra("sms_body", "Sponsor me at \n\n"
        				+ Properties.MELBOURNE_DONATION_LINK.toString() + GloVars.getUserName(this)
        				+ "\n\n"
        				+ "or Visit my Homepage at \n\n"
        				+ Properties.MELBOURNE_PARTICIPANTS_LINKS.toString() + GloVars.getUserName(this)
        				); 
        
        startActivity(sms_intent); 
        
        NumberList = "";
    }
  // =========================================================================
    public void  sendEMAIL(){
		//--->
		for (ContactObj listSamp : ListContacts) {
			
			if (listSamp.Check) {
				
				EmailList += listSamp.NumMail + ", ";
			}							
		}	
		
		String[] EmailSend = EmailList.split(",");
		
		String[] Send = new String[EmailSend.length-1];
		
		for(int y=0; y < Send.length; y++){
			Send[y] = EmailSend[y];
		}
		
		//---<
		//--->
		Intent sendIntent = new Intent(Intent.ACTION_SEND);
		//sendIntent.putExtra(Intent.EXTRA_EMAIL,	new String[] { EmailList });
		sendIntent.putExtra(Intent.EXTRA_EMAIL,	Send);
		sendIntent.putExtra(Intent.EXTRA_TEXT,
					"Please Sponsor me at \n\n"
					+ Properties.MELBOURNE_DONATION_LINK + GloVars.getUserName(this)
					+"\n\n"
					+"or Visit my Homepage at \n\n"
					+Properties.MELBOURNE_PARTICIPANTS_LINKS + GloVars.getUserName(this)
					);
		sendIntent.putExtra(Intent.EXTRA_SUBJECT,"Check out my Homepage");
		sendIntent.setType("message/rfc822");
		
//		try{
//			//startActivity(Intent.createChooser(sendIntent,"Title:Send to Emails"));
//			startActivity(sendIntent);
//			
//		}catch(android.content.ActivityNotFoundException exe){
//			
//			Utils.MessageToast("There are no email clients installed.", this);
//		}
		
		// Verify it resolves
		PackageManager packageManager = getPackageManager();
		List<ResolveInfo> activities = packageManager.queryIntentActivities(sendIntent, 0);
		boolean isIntentSafe = activities.size() > 0;
		
		if(isIntentSafe){
			startActivity(sendIntent);
		} else {
			
			Utils.MessageBox("There are no email clients installed.", this);
		}
		
		EmailList = "";
		//---<
    }
 // =========================================================================
 	private void setupDialog(){
 		
 		final Dialog dialog = new Dialog(this,android.R.style.Theme_Dialog);
    		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    		// this setting is incorrect format but the output is correct
    		dialog.getWindow().setBackgroundDrawableResource(R.drawable.blanc);
    		dialog.setContentView(R.layout.custom_add_contacts);
    		//-->
    		txtManual = (EditText)dialog.findViewById(R.id.etxtManual);
    		txtManual.setFocusable(true);
    		Button btnAdd = (Button)dialog.findViewById(R.id.btnAdd);
    		Button btnCancel = (Button)dialog.findViewById(R.id.btnCancel);
    		lstManual = (ListView)dialog.findViewById(R.id.lstManual);
 	   		//-->
 	   		if(GloVars.getSendType(this).toString().equals("SMS")){
 	   			txtManual.setInputType(InputType.TYPE_CLASS_PHONE);
 	   		}
 	   		if(GloVars.getSendType(this).toString().equals("EMAIL")){
 	   			txtManual.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
 	   		}
 	   		//--<
    		//--()
    		loadListView();
    		
    		lstManual.setOnItemLongClickListener(new OnItemLongClickListener() {
 			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int pos, long arg3) {
 				
 					//GloVars.setNumNull();
 					NumberList = "";
 					EmailList = "";
 				
 					Toast.makeText(ContactsAct.this, manualContacts.get(pos)+" has remove", Toast.LENGTH_SHORT).show();
 					manualContacts.remove(pos);
 					loadListView();
 					
 				return true;
 			}
    		});
    		//-->
    		btnAdd.setOnClickListener(new OnClickListener() {
 			public void onClick(View v) {
 				
 				if(!(txtManual.getText().length() == 0)){
 					
 					if(GloVars.getSendType(getBaseContext()).toString().equals("SMS")){
 						//-->>
 						manualContacts.add(txtManual.getText().toString());
 						txtManual.setText("");
 						//--<<
 		 	   		}else if(GloVars.getSendType(getBaseContext()).toString().equals("EMAIL")){
 		 	   			//-->>
 		 	   			if(txtManual.getText().toString().matches(Properties.EMAIL_VALIDATION)){
 		 	   				
 		 	   				manualContacts.add(txtManual.getText().toString());
 		 	   				txtManual.setText("");
 		 	   			}else{
 		 	   				Utils.MessageToast("Invalid Email Address", getBaseContext());
 		 	   			}
 		 	   			//--<<
 		 	   		}
				//--()
				loadListView();
 				}
 			}
    		});
    		btnCancel.setOnClickListener(new OnClickListener() {
 			public void onClick(View v) {
 				if(GloVars.getSendType(getBaseContext()).toString().equals("SMS")){
 					
 					//GloVars.setNumNull();
 					for(String ManConts: manualContacts ){
 						//GloVars.setNumList(ManConts + "; ");
 						NumberList += ManConts + ",";
 					}
 					dialog.dismiss();
 				}
 				
 				if(GloVars.getSendType(getBaseContext()).toString().equals("EMAIL")){
 					
 					EmailList = "";
 					for(String ManConts: manualContacts ){
 						EmailList += ManConts + ", ";
 					}
 					dialog.dismiss();
 				}
 			}
    		});
    		//--<
    		dialog.setCancelable(true);
    		dialog.show();
 	}
 // =========================================================================
	public void loadListView(){
	// Create and populate a List of planet names.  
    ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, manualContacts); 
    
    lstManual.setAdapter(listAdapter);
    lstManual.setSelection(listAdapter.getCount() - 1);
    
    //txtManual.setText("");
	}
 // =========================================================================
	public void loadAlphaListView(){
		
		//--> set for AlphaList listView
    	
    	String[] Letters = letterCon.split(",");
    	
    	for(int x = 0; x < Letters.length;x++){
    		
    		HashMap<String, String> rows = new HashMap<String, String>();
    		rows.put("letter", Letters[x]);
    		ArrayLetters.add(rows);
    	}
    	
    	AlphaAdap = new SimpleAdapter(this, ArrayLetters, R.layout.alpha_select, new String[] {"letter"}, new int[] {R.id.tvAlpha});
    	AlphaList.setAdapter(AlphaAdap);
    	
    	AlphaList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,long arg3) {
				
				//callAlphaListView(pos);
				callAlphaLV(pos);
			}
		});
    	//--<
	}
 // =========================================================================
	public void callAlphaListView(int pos){
		
		String let = ArrayLetters.get(pos).get("letter");
		Utils.MessageBox(let, this);
	}
 // =========================================================================
 // TODO Final Destination
}
