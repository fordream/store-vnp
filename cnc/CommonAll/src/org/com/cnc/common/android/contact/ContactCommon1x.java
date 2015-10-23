package org.com.cnc.common.android.contact;

import java.util.ArrayList;
import java.util.List;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.Contacts;
import android.provider.Contacts.People;

@SuppressWarnings("deprecation")
public class ContactCommon1x implements ContactCommon {
	private Context context;

	public ContactCommon1x(Context context) {
		this.context = context;
	}

	public List<Contact> getAllContatNone() {
		List<Contact> lContacts = new ArrayList<Contact>();
		ContentResolver cr = context.getContentResolver();
		Cursor cursor = cr.query(People.CONTENT_URI, null, null, null, null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				String id = cursor.getString(cursor.getColumnIndex(People._ID));
				String name = cursor.getString(cursor
						.getColumnIndex(People.DISPLAY_NAME));
				Contact contact = new Contact(id, name);
				lContacts.add(contact);
			}
		}
		return lContacts;
	}

	public List<Contact> getAllPhone(List<Contact> lContactsALL) {
		ContentResolver cr = context.getContentResolver();
		for (int i = 0; i < lContactsALL.size(); i++) {
			Contact contact = lContactsALL.get(i);
			contact.setlPhones(getlPhones(contact.getId(), cr));
		}
		return lContactsALL;
	}

	public List<Phone> getlPhones(String id, ContentResolver contentResolver) {
		List<Phone> lPhones = new ArrayList<Phone>();
		Cursor pCursor = contentResolver.query(Contacts.Phones.CONTENT_URI,
				null, Contacts.Phones.PERSON_ID + "=?", new String[] { id },
				null);
		while (pCursor.moveToNext()) {
			String number = pCursor.getString(pCursor
					.getColumnIndex(Contacts.Phones.NUMBER));
			String type = pCursor.getString(pCursor
					.getColumnIndex(Contacts.Phones.TYPE));
			Phone phone = new Phone(number, type);
			lPhones.add(phone);
		}

		return lPhones;
	}

	public List<Contact> getAllEmail(List<Contact> lContacts) {
		ContentResolver cr = context.getContentResolver();
		for (int i = 0; i < lContacts.size(); i++) {
			Contact contact = lContacts.get(i);
			contact.setlEmail(getlMail(contact.getId(), cr));
		}

		return lContacts;
	}

	public List<String> getlMail(String id, ContentResolver contentResolver) {
		List<String> lPhones = new ArrayList<String>();
		Cursor pCursor = contentResolver.query(
				Contacts.ContactMethods.CONTENT_EMAIL_URI, null,
				Contacts.ContactMethods.PERSON_ID + "=?", new String[] { id },
				null);
		while (pCursor.moveToNext()) {
			String number = pCursor.getString(pCursor
					.getColumnIndex(Contacts.ContactMethods.DATA));
			lPhones.add(number);
		}

		return lPhones;
	}

	public List<Contact> getAllEmail() {
		List<Contact> lContacts = new ArrayList<Contact>();
		ContentResolver cr = context.getContentResolver();

		Cursor cursor = cr.query(People.CONTENT_URI, null, null, null, null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				String id = cursor.getString(cursor.getColumnIndex(People._ID));
				String name = cursor.getString(cursor
						.getColumnIndex(People.DISPLAY_NAME));
				Contact contact = new Contact(id, name);
				// add email
				contact.setlEmail(getlMail(id, cr, cursor));

				lContacts.add(contact);
			}
		}
		return lContacts;
	}

	public List<Contact> getAllContatPhoneEmail() {
		List<Contact> lContacts = new ArrayList<Contact>();
		ContentResolver cr = context.getContentResolver();
		Cursor cursor = cr.query(People.CONTENT_URI, null, null, null, null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				String id = cursor.getString(cursor.getColumnIndex(People._ID));
				String name = cursor.getString(cursor
						.getColumnIndex(People.DISPLAY_NAME));
				Contact contact = new Contact(id, name);

				// add Phone
				contact.setlPhones(getlPhones(id, cr, cursor));

				// add email
				contact.setlEmail(getlMail(id, cr, cursor));

				lContacts.add(contact);
			}
		}
		return lContacts;
	}

	public List<Contact> getAllContact() {
		List<Contact> lContacts = new ArrayList<Contact>();
		ContentResolver cr = context.getContentResolver();
		Cursor cursor = cr.query(People.CONTENT_URI, null, null, null, null);
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				String id = cursor.getString(cursor.getColumnIndex(People._ID));
				String name = cursor.getString(cursor
						.getColumnIndex(People.DISPLAY_NAME));
				Contact contact = new Contact(id, name);
				lContacts.add(contact);
				// add Phone
				contact.setlPhones(getlPhones(id, cr, cursor));

				// add email
				contact.setlEmail(getlMail(id, cr, cursor));
			}
		}
		return lContacts;
	}

	public List<Phone> getlPhones(String id, ContentResolver contentResolver,
			Cursor cursor) {
		List<Phone> lPhones = new ArrayList<Phone>();
		int index = cursor.getColumnIndex(People.PRIMARY_PHONE_ID);
		String count = cursor.getString(index);
		if (Integer.parseInt(count) > 0) {
			Cursor pCursor = contentResolver.query(Contacts.Phones.CONTENT_URI,
					null, Contacts.Phones.PERSON_ID + "=?",
					new String[] { id }, null);
			while (pCursor.moveToNext()) {
				String number = pCursor.getString(pCursor
						.getColumnIndex(Contacts.Phones.NUMBER));
				String type = pCursor.getString(pCursor
						.getColumnIndex(Contacts.Phones.TYPE));
				Phone phone = new Phone(number, type);
				lPhones.add(phone);
			}
		}

		return lPhones;
	}

	public List<String> getlMail(String id, ContentResolver contentResolver,
			Cursor cursor) {
		List<String> lPhones = new ArrayList<String>();
		Cursor pCursor = contentResolver.query(
				Contacts.ContactMethods.CONTENT_EMAIL_URI, null,
				Contacts.ContactMethods.PERSON_ID + "=?", new String[] { id },
				null);
		while (pCursor.moveToNext()) {
			String number = pCursor.getString(pCursor
					.getColumnIndex(Contacts.ContactMethods.DATA));
			lPhones.add(number);
		}

		return lPhones;
	}

	public List<Phone> getlPhone(String id) {
		List<Phone> lPhones = new ArrayList<Phone>();
		ContentResolver contentResolver = context.getContentResolver();
		Cursor pCursor = contentResolver.query(Contacts.Phones.CONTENT_URI,
				null, Contacts.Phones.PERSON_ID + "=?", new String[] { id },
				null);
		while (pCursor.moveToNext()) {
			String number = pCursor.getString(pCursor
					.getColumnIndex(Contacts.Phones.NUMBER));
			String type = pCursor.getString(pCursor
					.getColumnIndex(Contacts.Phones.TYPE));
			Phone phone = new Phone(number, type);
			lPhones.add(phone);
		}

		return lPhones;
	}

	public List<String> getlEmail(String id) {
		List<String> lPhones = new ArrayList<String>();
		ContentResolver contentResolver = context.getContentResolver();
		Cursor pCursor = contentResolver.query(
				Contacts.ContactMethods.CONTENT_EMAIL_URI, null,
				Contacts.ContactMethods.PERSON_ID + "=?", new String[] { id },
				null);
		while (pCursor.moveToNext()) {
			String number = pCursor.getString(pCursor
					.getColumnIndex(Contacts.ContactMethods.DATA));
			lPhones.add(number);
		}

		return lPhones;
	}
}
