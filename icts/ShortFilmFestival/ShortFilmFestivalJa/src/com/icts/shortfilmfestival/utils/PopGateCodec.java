package com.icts.shortfilmfestival.utils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class PopGateCodec {
	static private final String latin = "ISO-8859-1";
	static private byte aCode;
	static private final char[] base = {
		  'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S',
		  'T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l',
		  'm','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3','4',
		  '5','6','7','8','9'
		};
	
	static {
		try{
			aCode = "a".getBytes(latin)[0];
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
			aCode = 97;
		}
	}
	
	/**PopGate暗�?�化メソッド。*/
	static final public String encode(String s) throws UnsupportedEncodingException{
		int sum = 0;
		String enc = "";
		byte[] b = s.getBytes(latin);
		
		for(int i=0; i < b.length; i++){
			byte[] xor = new byte[1];
			xor[0] = (byte)(b[i] ^ aCode);
			enc = "" + enc + new String(xor, latin);
			sum = sum + (b[i] - 40);
		}
		
		char parity = base[sum%62];
		int len = s.length() / 62;
		char len1 = base[len];
		char len2 = base[s.length() % 62];
		
		enc = Base64.encodeBytes(enc.getBytes(latin));
		enc = enc.replace('+', '_');
		enc = enc.replace('/', '.');
		enc = enc.replaceAll("=+$", "");
		
		return enc + len1 + len2 + parity;
	}

	/**PopGate復�?�メソッド。復�?�失敗時�?�nullを返�?�。*/
	static final public String decode(String s) throws UnsupportedEncodingException{
		int len = s.length();
		String str = s.substring(0, len - 3);
		StringBuilder sb = new StringBuilder();
		
		for(int i=0; i < base.length; i++){
			sb.append(base[i]);
		}
		
		int len1 = sb.toString().indexOf(s.substring(len-3, len-3+1) );
		int len2 = sb.toString().indexOf(s.substring(len-2, len-2+1) );
		int parity = sb.toString().indexOf(s.substring(len-1, len-1+1) );

		if( parity < 0 || (len1 < 0 || len2 < 0)){
			return null;
		}
		
		len = len1 * 62 + len2;
		str = str.replace('_', '+');
		str = str.replace('.', '/');
		String dec;
		try{
			dec = new String(Base64.decode(str), latin);
		}catch(IOException e){
			e.printStackTrace();
			dec = null;
		}
		str = "";
		int sum=0;
		for(int i=0; i < len; i++){
			byte[] xor = new byte[1];
			xor[0] = (byte)( (dec.substring(i,i+1).getBytes(latin)[0]) ^ aCode );
			sum = sum + (xor[0] - 40);
			str = str + new String(xor, latin);
		}
		
		if( (sum % 62) != parity){
			System.err.println("Parity check error!");
			return null;
		}
		
		return str;
	}

}
