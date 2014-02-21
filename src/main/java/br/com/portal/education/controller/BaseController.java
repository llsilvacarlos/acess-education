package br.com.portal.education.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import br.com.portal.education.entity.User;
import br.com.portal.education.util.MessageUtil;

public abstract class BaseController implements Serializable {

    @Inject
    private MessageUtil messageUtil;

    protected void addMessageInfo(String message, String... params) {
	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, messageUtil.getMessage(message, params), null));
    }

    protected void addMessageError(String message, String... params) {
	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, messageUtil.getMessage(message, params), null));
    }

    protected void addMessageError(String message) {
	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }

    protected User getUserAuthenticated() {
	FacesContext facesContext = FacesContext.getCurrentInstance();
	ExternalContext ec = facesContext.getExternalContext();
	HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
	User currentUser = (User) session.getAttribute("userAuthentication");
	return currentUser;

    }

    protected void redirectErroPage() {
	FacesContext facesContext = FacesContext.getCurrentInstance();
	ExternalContext ec = facesContext.getExternalContext();
	NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
	nh.handleNavigation(facesContext, null, "erroPage");
    }

}
