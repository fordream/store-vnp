package vn.vvn.bibook.encrypter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.Log;

public class Encrypter {
	// Buffer used to transport the bytes from one stream to another
	byte[] buf = new byte[1024];
	byte[] sercure1;
	byte[] sercure2;
	protected int LENGHT = 32;

	public Encrypter() {
		this.sercure1 = new byte[LENGHT];
		this.sercure2 = new byte[LENGHT];
	}

	public void encrypt(String src, String dst, String pass) {
//		Log.d("test", "src " + src);
//		Log.d("test", "dst " + dst);
		try {
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dst);
			int numRead = 0;
			sercure1 = pass.getBytes();
//			Log.e("SEC", "lenght " + sercure1.length);
			out.write(sercure1, 0, LENGHT);
			while ((numRead = in.read(buf)) >= 0) {
				if (numRead > 128) {
					for (int i = 0; i < 64; i++) {
						byte temp = buf[i];
						buf[i] = buf[127 - i];
						buf[127 - i] = temp;
					}
				}
				out.write(buf, 0, numRead);
			}
			out.close();
			new File(src).delete();
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}

	public boolean decrypt(String src, String dst, String pass) {
		try {
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dst);
			int numRead = 0;

			in.read(sercure2, 0, LENGHT);

			// check pass
			String unpass = new String(sercure2);
			if (pass.equals(unpass)) {

				// ghi file
				while ((numRead = in.read(buf)) >= 0) {
					if (numRead > 128) {
						for (int i = 0; i < 64; i++) {
							byte temp = buf[i];
							buf[i] = buf[127 - i];
							buf[127 - i] = temp;
						}
					}
					out.write(buf, 0, numRead);
				}
				out.close();
				return true;
			} else {
				return false;
			}
		} catch (java.io.IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public String md5(String in) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
			digest.reset();
			digest.update(in.getBytes());
			byte[] a = digest.digest();
			int len = a.length;
			StringBuilder sb = new StringBuilder(len << 1);
			for (int i = 0; i < len; i++) {
				sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
				sb.append(Character.forDigit(a[i] & 0x0f, 16));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
