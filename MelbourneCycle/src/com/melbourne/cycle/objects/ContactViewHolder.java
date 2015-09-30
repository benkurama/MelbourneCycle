package com.melbourne.cycle.objects;

import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ContactViewHolder {
	  
    private CheckBox checkBox ;
    private TextView txtName, txtNumail,txtLetter ;
    private ImageView imgPhoto;
    private RelativeLayout relLayout;

    
    public ContactViewHolder( TextView name,TextView numail,ImageView photo, CheckBox checkBox,RelativeLayout layout,TextView leter ) {
  	  
      this.checkBox = checkBox ;
      this.txtName = name ;
      this.txtNumail = numail;
      this.imgPhoto = photo;
      this.relLayout = layout;
      this.txtLetter = leter;
    }
    //-->
    public TextView getNameView() {
        return txtName;
    }
    
    public TextView getNumailView() {
        return txtNumail;
    }
    
    public ImageView getPhotolView() {
        return imgPhoto;
    }
    
    public RelativeLayout getLayout(){
  	  return relLayout;
    }
    
    public TextView getLetter(){
  	  return txtLetter;
    }
    //--<
    
    public CheckBox getCheckBox() {
      return checkBox;
    }
       
  }