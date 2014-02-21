package br.com.portal.education.controller.user;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.portal.education.controller.BaseController;
import br.com.portal.education.entity.Contact;

@ManagedBean(name = "userContactController")
@SessionScoped
public class UserContactController extends BaseController {

    private Contact contact = new Contact();
    private List<Contact> contacts = new ArrayList<Contact>();

    public void clearField() {
	this.contact = new Contact();
	contacts = new ArrayList<Contact>();
    }

    public void addContact() {
	contacts.add(contact);
	contact = new Contact();
    }

    public void removeContact() {
	contacts.remove(contact);
	addMessageInfo("msg_contact_removed", null);

    }

    public Contact getContact() {
	return contact;
    }

    public void setContact(Contact contact) {
	this.contact = contact;
    }

    public List<Contact> getContacts() {
	return contacts;
    }

    public void setContacts(List<Contact> contacts) {
	this.contacts = contacts;
    }

}
