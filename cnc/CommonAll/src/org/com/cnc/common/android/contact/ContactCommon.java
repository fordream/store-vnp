package org.com.cnc.common.android.contact;

import java.util.List;

import android.content.ContentResolver;
import android.database.Cursor;

public interface ContactCommon {
	public List<Contact> getAllContatNone();

	public List<Contact> getAllPhone(List<Contact> lContactsALL);

	public List<Phone> getlPhones(String id, ContentResolver contentResolver);

	public List<Contact> getAllEmail(List<Contact> lContacts);

	public List<String> getlMail(String id, ContentResolver contentResolver);

	public List<Contact> getAllEmail();

	public List<Contact> getAllContatPhoneEmail();

	public List<Contact> getAllContact();

	public List<Phone> getlPhones(String id, ContentResolver contentResolver,
			Cursor cursor);

	public List<String> getlMail(String id, ContentResolver contentResolver,
			Cursor cursor);

	public List<Phone> getlPhone(String id);

	public List<String> getlEmail(String id);
}
