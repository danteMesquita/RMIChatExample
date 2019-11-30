package com.rmi.shared.objects;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String userName;
	
	protected User() {}
	
	public User(String _userName){
		id = generateUniqueId();
		userName = _userName;
	}
	
	public String getId() {
		return id;
	}
	
	public String getUserName() {
		return userName;
	}
	
	private static String generateUniqueId() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			return generateString(sdf.toString(), "MD5", 32);

		}catch(Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}
	
	private static String generateString(String input, String algorithm, int minLength) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        byte[] bytes = messageDigest.digest(input.getBytes());
        BigInteger integer = new BigInteger(1, bytes);
        String result = integer.toString(16);
        while (result.length() < minLength) {
            result = "0" + result;
        }
        return result;
    }
	
}
