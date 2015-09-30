package com.melbourne.cycle.objects;

import android.graphics.Bitmap;

public class ContactExt {
	  
	private String name;
    private String numail;
    private Bitmap photo;
    private int viewy;
    private String letter;
    
    private boolean checked = false ;
    
    public ContactExt( String name,String numail,Bitmap photo,Boolean checkin,int view,String let) {
    	
      this.name = name ;
      this.numail = numail;
      this.photo = photo;
      this.checked = checkin;
      this.viewy = view;
      this.letter = let;
    }
    //-->
    public String getName() {
      return name;
    }
    
    public String getNumail() {
        return numail;
     }
    
    public Bitmap getPhoto() {
        return photo;
    }
    
    public int getView(){
  	  return viewy;
    }
    
    public String getLetter(){
  	  return letter;
    }
    //--<
    //-->
    public boolean isChecked() {
      return checked;
    }
    
    public void setChecked(boolean checked) {
      this.checked = checked;
    }

    public void toggleChecked() {
      checked = !checked ;
    }
    //--<
  }
