package br.com.portal.education.exception;

import javax.ejb.ApplicationException;


@ApplicationException(rollback=true)
public class PortalEducationApplicationException extends Exception {

    public PortalEducationApplicationException() {
	super();
    }

    public PortalEducationApplicationException(String message) {
	super(message);
    }

    public PortalEducationApplicationException(Exception exception) {
	super(exception);
    }
}
