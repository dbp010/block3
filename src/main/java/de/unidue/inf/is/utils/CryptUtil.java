package de.unidue.inf.is.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class CryptUtil {

	private CryptUtil() {}
	
	public static String createSHA1Hash(String in){
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		if(digest == null)
			return null;
		
		try {
			digest.update(in.getBytes("utf8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		
		byte[] digestBytes = digest.digest();
		return javax.xml.bind.DatatypeConverter.printHexBinary(digestBytes);
	}
	
}
