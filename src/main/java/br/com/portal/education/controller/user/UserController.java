/*
] * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.portal.education.controller.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;

import br.com.portal.education.controller.BaseController;
import br.com.portal.education.entity.Address;
import br.com.portal.education.entity.Contact;
import br.com.portal.education.entity.Discipline;
import br.com.portal.education.entity.TypeUser;
import br.com.portal.education.entity.User;
import br.com.portal.education.exception.CryptographyException;
import br.com.portal.education.exception.PortalEducationApplicationException;
import br.com.portal.education.service.TypeUserBean;
import br.com.portal.education.service.UserBean;
import br.com.portal.education.util.CryptographySHA256;
import br.com.portal.education.util.TypeUserEnum;

/**
 * 
 * @author lcsilva
 */
@ManagedBean(name = "userController")
@SessionScoped
public class UserController extends BaseController {

    Logger logger = Logger.getLogger(UserController.class);

    /** Bussines Components **/
    @Inject
    private UserBean userBean;

    @Inject
    private TypeUserBean typeUserBean;

    @Inject
    private CryptographySHA256 cryptographySHA256;

    /** Components Composite **/
    @ManagedProperty(value = "#{userPhotoController}")
    private UserPhotoController userPhotoController;

    @ManagedProperty(value = "#{userContactController}")
    private UserContactController userContactController;

    @ManagedProperty(value = "#{userDisciplineController}")
    private UserDisciplineController userDisciplineController;

    @PostConstruct
    public void init() {
	clearField();
    }

    private String nameSearch;

    private List<SelectItem> typeUsers = new ArrayList<SelectItem>();
    private User user = new User();
    private List<User> listUser = new ArrayList<User>();

    public String newUser() {
	clearField();
	return "newUser";
    }

    public String myUser() {
	clearField();
	this.user = getUserAuthenticated();
	loadPhotoAndContacts();
	return "newUser";
    }

    public String listAllUser() {
	clearField();
	listUser = userBean.findALL();
	return "userAll";
    }

    private void clearField() {
	user = new User();
	user.setAddress(new Address());
	user.setContacts(new ArrayList<Contact>());
	userContactController.clearField();
	userPhotoController.setContent(null);
	nameSearch = null;

    }

    public List<User> getListUser() {
	return listUser;
    }

    public void setListUser(List<User> listUser) {
	this.listUser = listUser;
    }

    /**
     * Creates a new instance of UserController
     */
    public UserController() {
    }

    public void save(ActionEvent actionEvent) {
	try {
	    if (userPhotoController.getPhotoBytes() == null) {
		addMessageError("msg_photo_mandatory", null);
	    } else {
		fillContactUser();
		fillDisciplineTeacher();
		user.setPhoto(userPhotoController.getPhotoBytes());
		user.setPassword(cryptographySHA256.cryptographyPassword(user.getPassword().trim()));
		if (user.getId() == null) {
		    userBean.create(user);
		    addMessageInfo("msg_user_insert", null);
		} else {
		    userBean.update(user);
		    addMessageInfo("msg_user_update", null);
		}
	    }

	} catch (CryptographyException c) {
	    redirectErroPage();

	} catch (PortalEducationApplicationException e) {
	    addMessageError(e.getLocalizedMessage());
	}

    }

    public void register(ActionEvent actionEvent) {
	try {
	    if (userPhotoController.getPhotoBytes() == null) {
		addMessageError("msg_photo_mandatory", null);
	    } else {
		fillContactUser();
		user.setPhoto(userPhotoController.getPhotoBytes());
		user.setTypeUser(typeUserBean.find(TypeUserEnum.STUDENT.getIdTypeUser()));
		user.setPassword(cryptographySHA256.cryptographyPassword(user.getPassword().trim()));
		if (user.getId() == null) {
		    userBean.create(user);
		}
	    }

	} catch (CryptographyException c) {
	    redirectErroPage();

	} catch (PortalEducationApplicationException e) {
	    redirectErroPage();
	}

	FacesContext ctx = FacesContext.getCurrentInstance();
	HttpSession session = (HttpSession) ctx.getExternalContext().getSession(false);
	session.setAttribute("userAuthentication", user);

	try {
	    ctx.getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath() + "/index.xhtml");
	} catch (IOException e) {
	    logger.error(e);
	}

    }

    public void handleTypeUserChange(AjaxBehaviorEvent e) {
	if (user.getTypeUser().getId().equals(TypeUserEnum.TEACHER.getIdTypeUser())) {
	    userDisciplineController.loadList(null);
	}
    }

    private void fillContactUser() {
	List<Contact> contacts = userContactController.getContacts();
	for (Contact contact : contacts) {
	    contact.setUser(user);
	}
	user.setContacts(contacts);
    }

    private void fillDisciplineTeacher() {
	if (userDisciplineController.getTargetDiscipline() != null && !userDisciplineController.getTargetDiscipline().isEmpty()) {
	    List<Discipline> targetDiscipline = userDisciplineController.getTargetDiscipline();
	    user.getDisciplines().addAll(targetDiscipline);
	}

    }

    public void search() {

	User user = userBean.findByLogin(nameSearch);
	if (user != null) {
	    listUser = new ArrayList<User>();
	    listUser.add(user);
	}

    }

    public String delete() {
	userBean.delete(user);
	addMessageInfo("msg_exclusion_sucess", null);
	clearField();
	return "userAll";
    }

    public String edit() {
	user = userBean.find(user.getId());
	loadPhotoAndContacts();
	if (user.getTypeUser().getId().equals(TypeUserEnum.TEACHER.getIdTypeUser())) {
	    userDisciplineController.loadList(user.getDisciplines());
	}
	return "user";
    }

    private void loadPhotoAndContacts() {
	userContactController.setContacts(user.getContacts());
	userPhotoController.loadPhoto(user.getPhoto(), user.getId().toString());
    }

    public void handleFileUpload(FileUploadEvent event) {
	byte[] photoBytes = event.getFile().getContents();
	userPhotoController.loadPhoto(photoBytes, event.getFile().getFileName());

    }

    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    public List<SelectItem> getTypeUsers() {

	if (typeUsers.size() == 0) {
	    for (TypeUser typeUser : typeUserBean.findAll()) {
		typeUsers.add(new SelectItem(typeUser, typeUser.getTypeUser()));
	    }
	}

	return typeUsers;
    }

    public void setTypeUsers(List<SelectItem> typeUsers) {
	this.typeUsers = typeUsers;
    }

    public String getNameSearch() {
	return nameSearch;
    }

    public void setNameSearch(String nameSearch) {
	this.nameSearch = nameSearch;
    }

    public UserPhotoController getUserPhotoController() {
	return userPhotoController;
    }

    public void setUserPhotoController(UserPhotoController userPhotoController) {
	this.userPhotoController = userPhotoController;
    }

    public UserContactController getUserContactController() {
	return userContactController;
    }

    public void setUserContactController(UserContactController userContactController) {
	this.userContactController = userContactController;
    }

    public UserDisciplineController getUserDisciplineController() {
	return userDisciplineController;
    }

    public void setUserDisciplineController(UserDisciplineController userDisciplineController) {
	this.userDisciplineController = userDisciplineController;
    }

}
