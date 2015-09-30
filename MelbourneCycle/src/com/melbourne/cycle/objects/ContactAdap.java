package com.melbourne.cycle.objects;

import java.util.ArrayList;

import com.melbourne.cycle.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ContactAdap extends ArrayAdapter<ContactExt> {
	
    //private ArrayList<Contacts> users;
    private LayoutInflater inflater; 
    
    public ContactAdap( Context context, ArrayList<ContactExt> contactLists ) {
      super( context, R.layout.list_contacts,R.id.ckbBox, contactLists );
      // Cache the LayoutInflate to avoid asking for a new one each time.
      inflater = LayoutInflater.from(context) ;
      
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	ContactExt contactListing = (ContactExt) this.getItem( position ); 

      //--> The child views in each row.
      CheckBox checkBox ; 
      TextView nameView, numailView, letter;
      ImageView photoView;
      RelativeLayout layRelative;
      //--<
      
      // Create a new row view
      if ( convertView == null ) {
      	
        convertView = inflater.inflate(R.layout.list_contacts, null);
        
        // Find the child views.
        checkBox = (CheckBox) convertView.findViewById( R.id.ckbBox );
        nameView = (TextView) convertView.findViewById( R.id.Name );
        numailView = (TextView) convertView.findViewById( R.id.Number );
        photoView = (ImageView)convertView.findViewById(R.id.avatar);
        
        layRelative = (RelativeLayout)convertView.findViewById(R.id.alphaLayout);
        letter = (TextView)convertView.findViewById(R.id.letter);
        // Optimization: Tag the row with it's child views, so we don't have to 
        // call findViewById() later when we reuse the row.
        convertView.setTag( new ContactViewHolder(nameView,numailView,photoView,checkBox,layRelative,letter) );

        //Reserved Codes
        // If CheckBox is toggled, update the planet it is tagged with.
//        checkBox.setOnClickListener( new View.OnClickListener() {
//          public void onClick(View v) {
//          	
//            CheckBox cb = (CheckBox) v ;
//            ContactExt kontacts = (ContactExt) cb.getTag();
//            kontacts.setChecked( cb.isChecked() );
//          }
//        });
        
      }
      // Reuse existing row view
      else {
        //--> Because we use a ViewHolder, we avoid having to call findViewById().
        ContactViewHolder viewHolder = (ContactViewHolder) convertView.getTag();
        
        checkBox = viewHolder.getCheckBox() ;
        nameView = viewHolder.getNameView() ;
        numailView = viewHolder.getNumailView();
        photoView = viewHolder.getPhotolView();
        
        layRelative = viewHolder.getLayout();
        letter = viewHolder.getLetter();
        //--<
      }

      // Tag the CheckBox with the Planet it is displaying, so that we can
      // access the planet in onClick() when the CheckBox is toggled.
      checkBox.setTag( contactListing ); 
      
      //--> Display contact data
      checkBox.setChecked( contactListing.isChecked() );
      nameView.setText( contactListing.getName() );
      numailView.setText(contactListing.getNumail());
      photoView.setImageBitmap(contactListing.getPhoto());
      
      layRelative.setVisibility(contactListing.getView());
      letter.setText(contactListing.getLetter());
      //--<
      return convertView;
    }
  }
