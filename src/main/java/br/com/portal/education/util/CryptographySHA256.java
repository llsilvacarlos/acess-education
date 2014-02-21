package br.com.portal.education.util;

import java.security.MessageDigest;

import javax.inject.Named;

import br.com.portal.education.exception.CryptographyException;

@Named
public class CryptographySHA256 {

    public String cryptographyPassword(String password) throws CryptographyException {
	try {
	    MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
	    byte messageDigest[] = algorithm.digest(password.getBytes("UTF-8"));
	    StringBuilder hexString = new StringBuilder();
	    for (byte b : messageDigest) {
		hexString.append(String.format("%02X", 0xFF & b));
	    }
	    String passwordCryp = hexString.toString();
	    return passwordCryp;
	} catch (Exception e) {
	    throw new CryptographyException(e);
	}
    }
    
    
    public static void main(String[] args) throws CryptographyException {
	System.out.println(new CryptographySHA256().cryptographyPassword("123456"));
    }

}
