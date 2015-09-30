package com.melbourne.cycle.objects;

import android.graphics.Bitmap;
import android.view.View;

public class ContactObj {
	 // =========================================================================
	 // TODO Properties
	 // =========================================================================
		public Integer ID;
		public String Name;
		public String NumMail;
		public Bitmap Photo;
		public String Email;
		public Boolean Check;
		
		public int Visible;
		public String Letter;
	 // =========================================================================
	 // TODO Constructors
	 // =========================================================================
		public ContactObj(){
			this.ID = 0;
			this.Name = "";
			this.NumMail = "";
			this.Photo = null;
			this.Email = "";
			this.Check = false;
			
			this.Visible = View.VISIBLE;
			this.Letter = "";
		}
	 // =========================================================================
	 // TODO Methods
	 // =========================================================================
		public void setChecked(boolean checked) {
		    this.Check = checked;
		}
		
		public void setTrue(){
			this.Check = true;		
		}
		
		public void setFalse(){
			this.Check = false;		
		}
	 // =========================================================================
		public void setView(int view){
			this.Visible = view;
		}
		
		public void setVisible(){
			this.Visible = View.VISIBLE;
		}
		
		public void setInvisible(){
			this.Visible = View.GONE;
		}
	 // =========================================================================
	 // TODO Final Destination	
}
