package br.com.portal.education.controller.auth;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import br.com.portal.education.controller.BaseController;
import br.com.portal.education.entity.User;
import br.com.portal.education.exception.CryptographyException;
import br.com.portal.education.service.UserBean;
import br.com.portal.education.util.CryptographySHA256;

@ManagedBean(name = "authenticationController")
@ViewScoped
public class AuthenticationController extends BaseController {

    Logger logger = Logger.getLogger(AuthenticationController.class);

    private User user, userAuthentication;

    @Inject
    private UserBean userBean;

    @Inject
    private CryptographySHA256 cryptographySHA256;

    @PostConstruct
    public void init() {
	// Chamado só quando o managed bean é colocado no escopo view,
	// e não a cada requisição como acontecia com o escopo request
	user = new User();
    }

    public void authentication() {
	try {
	    userAuthentication = userBean.findUserByLoginAndPassword(user.getLogin(), cryptographySHA256.cryptographyPassword(user.getPassword()));
	} catch (CryptographyException e) {
	    logger.error(e);
	}
	if (userAuthentication == null) {
	    addMessageError("msg_erro_user_not_authentication", user.getLogin());
	} else {
	    FacesContext ctx = FacesContext.getCurrentInstance();
	    HttpSession session = (HttpSession) ctx.getExternalContext().getSession(false);
	    session.setAttribute("userAuthentication", userAuthentication);
	    try {
		ctx.getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath() + "/index.xhtml");
	    } catch (IOException e) {
		logger.error(e);
	    }
	}
    }

    @PreDestroy
    public void destroy() {
	/* chamado quando outra view for chamada através do UIViewRoot.setViewId(String viewId) */
    }

    public void endSession() {
	try {
	    FacesContext ctx = FacesContext.getCurrentInstance();
	    HttpSession session = (HttpSession) ctx.getExternalContext().getSession(false);
	    session.setAttribute("userAuthentication", null);
	    ctx.getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath() + "/login.xhtml");
	    session.invalidate();
	} catch (Exception e) {
	}
    }

    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }

}
