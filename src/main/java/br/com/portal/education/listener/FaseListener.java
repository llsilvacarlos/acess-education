package br.com.portal.education.listener;

import java.io.IOException;

import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

import br.com.portal.education.entity.User;

public class FaseListener implements PhaseListener {

    /**
     * 
     */
    private static final long serialVersionUID = -4438623717276074774L;

    @Override
    public void afterPhase(PhaseEvent event) {
	FacesContext facesContext = event.getFacesContext();
	ExternalContext ec = facesContext.getExternalContext();
	
	String currentPage = facesContext.getViewRoot().getViewId();

	boolean isLoginPage = (currentPage.lastIndexOf("login.xhtml") > -1 || currentPage.lastIndexOf("register.xhtml") > -1);
	HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

	if (session == null) {
	    session = (HttpSession) facesContext.getExternalContext().getSession(true);
	    NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
	    nh.handleNavigation(facesContext, null, "loginPage");
	} else {
	    User currentUser = (User) session.getAttribute("userAuthentication");

	    if (!isLoginPage && (currentUser == null || currentUser.getId() == null)) {
//		NavigationHandler nh = facesContext.getApplication().getNavigationHandler();
//		nh.handleNavigation(facesContext, null, "loginPage");
		
		try {
		    ec.redirect(ec.getRequestContextPath() + "/login.xhtml");
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }
	}

    }

    @Override
    public void beforePhase(PhaseEvent event) {

    }

    @Override
    public PhaseId getPhaseId() {
	return PhaseId.RESTORE_VIEW;
    }

}
