package br.com.portal.education.exception;

public class CryptographyException extends Exception {

    public CryptographyException() {
	super();
    }

    public CryptographyException(String message) {
	super(message);
    }

    public CryptographyException(Exception exception) {
	super(exception);
    }

}
