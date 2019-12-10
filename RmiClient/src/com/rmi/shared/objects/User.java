package com.rmi.shared.objects;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class User implements Serializable {
	/**
	 * 
	 */
	private static ArrayList<Message> userMessages = new ArrayList<Message>();
	private static final long serialVersionUID = 1L;
	private String id;
	private String userName;
	
	
	//protected User() {}
	
	public User(String _userName){
		userName = _userName;
		id = generateUniqueId();
	}
	
	public String getId() {
		return id;
	}
	
	public String getUserName() {
		return userName;
	}
	
	private String generateUniqueId() {
		try {
			if(userName.equals("")) return "";
			
			String generatedString = generateString(this.userName, "MD5", 5);
			
			if(generatedString.length() > 5) { 
				return generatedString.substring(0, 5);	
			}
			
			return generatedString;

		}catch(Exception ex) {
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
	
	public void receiveMessage(Message message) {
		userMessages.add(message);
	}
	
	public ArrayList<Message> getMessages() {
		return userMessages;
	}
}
