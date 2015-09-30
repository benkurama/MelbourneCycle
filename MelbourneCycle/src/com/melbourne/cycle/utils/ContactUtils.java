package com.melbourne.cycle.utils;

import java.util.ArrayList;

import com.melbourne.cycle.R;
import com.melbourne.cycle.objects.ContactObj;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;

public class ContactUtils {
 // =========================================================================
 // TODO Get Contact Number | Page: ContactAct
 // =========================================================================
	@SuppressLint({ "NewApi", "NewApi" })
	public static ArrayList<ContactObj> getContactNumber(Context context){
		
		ArrayList<ContactObj> contactlist = new ArrayList<ContactObj>();
		//-->
		ContentResolver cr = context.getContentResolver();
		String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
		
		String[] projection = new String[] {ContactsContract.Contacts._ID,ContactsContract.Contacts.DISPLAY_NAME,Phone.NUMBER,
				ContactsContract.Contacts.PHOTO_ID};	
		
		Cursor cur = cr.query(Phone.CONTENT_URI, projection, null, null, sortOrder);
		//---< RawContacts.ACCOUNT_TYPE + " <> 'com.anddroid.contacts.sim' "
		if (cur.getCount() > 0) {

			while (cur.moveToNext()) {
				ContactObj con = new ContactObj();
				//--->					
				con.ID = cur.getInt(cur.getColumnIndex(ContactsContract.Contacts._ID));
				con.NumMail = cur.getString(cur.getColumnIndex(Phone.NUMBER));
				con.Name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				//---<
				// for getting photo contact in phone contaclist
				//--->
				try{ 
				
					byte[] photoBytes = null;
					int photoid = cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.PHOTO_ID));
					Uri photoUri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, photoid);					
					Cursor c = cr.query(photoUri, new String[] {ContactsContract.CommonDataKinds.Photo.PHOTO}, null, null, null);
					
					try {
			            if (c.moveToFirst()) photoBytes = c.getBlob(0);
			        } catch (Exception e) {
			            //Log.w(TAG, e.toString());
			        } finally {
			            c.close();
			        } 	
					
					if (photoBytes == null) {
				    	con.Photo = setPhotoToNull(context);
				    }else{
				    	con.Photo = setPhoto(context, photoBytes);
				    }	
				
				}catch(Throwable t){ // if try catch detect if sim is countered photo, goes to catch the error and post default null image
					//Log.i(TAG,"Got Error");
			    	con.Photo = setPhotoToNull(context);
				}
				//---<
				//-- adds to an array list
				contactlist.add(con);
			}				
		}		
		cur.close();
		//--<
		
		return contactlist;
	}
 // =========================================================================
 // TODO Get Email Data | Page: ContactAct
 // =========================================================================
	@SuppressLint({ "NewApi", "NewApi" })
	public static ArrayList<ContactObj> getContactEmail(Context context){
		
		ArrayList<ContactObj> contactlist = new ArrayList<ContactObj>();
		//--->
		ContentResolver crt = context.getContentResolver();
		Uri uri = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
		
		String[] projection2 = new String[] { ContactsContract.Contacts._ID,ContactsContract.Contacts.DISPLAY_NAME,
				ContactsContract.CommonDataKinds.Email.DATA,ContactsContract.Contacts.PHOTO_ID };
		
		String selection = ContactsContract.Contacts.IN_VISIBLE_GROUP + "='1'";
		// showing only visible contacts
		String[] selectionArgs = null;
		String sortOrder2 = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
		Cursor cur2 = crt.query(uri, projection2, selection, selectionArgs, sortOrder2);
		//---<
		
		if (cur2.getCount() > 0) {
			
			while (cur2.moveToNext()) {
				ContactObj con2 = new ContactObj();
				//--->
				con2.ID = cur2.getInt(cur2.getColumnIndex(ContactsContract.Contacts._ID));
				con2.Name = cur2.getString(cur2.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));						
				con2.NumMail = cur2.getString(cur2.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));						
				//---<

				//--->  for getting photo contact in phone contaclist
				byte[] photoBytes = null;
				int photoid = cur2.getInt(cur2.getColumnIndex(ContactsContract.Contacts.PHOTO_ID));
				Uri photoUri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, photoid);					
				Cursor c = crt.query(photoUri, new String[] {ContactsContract.CommonDataKinds.Photo.PHOTO}, null, null, null);
				
				try 
		        {
		            if (c.moveToFirst()) photoBytes = c.getBlob(0);
		        } catch (Exception e) {
		            //Log.w(TAG, e.toString());
		        } finally {
		            c.close();
		        } 			
				if (photoBytes == null) {
					
			    	con2.Photo = setPhotoToNull(context);
			    }else{
			    	
			    	con2.Photo = setPhoto(context, photoBytes);
			    }			
				//---<
				if (con2.NumMail.toString().matches(Properties.EMAIL_VALIDATION)) {
					contactlist.add(con2);
				}
			}
		}
		cur2.close();
		
		return contactlist;
	}
 // =========================================================================
 // TODO Functions
 // =========================================================================
	public static Bitmap setPhotoToNull(Context context){
		
		Bitmap myBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.contact_null);					
		Bitmap resize = Bitmap.createScaledBitmap(myBitmap, 65, 65, true);
		return resize;
	}
 // =========================================================================	
	public static Bitmap setPhoto(Context context,byte[] photo){
		
		Bitmap bitmap = BitmapFactory.decodeByteArray(photo,0,photo.length);
    	Bitmap resize = Bitmap.createScaledBitmap(bitmap, 65, 65, true);
    	
		return resize;
	}
 // =========================================================================
 // TODO Final Destination
}
