/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.portal.education.entity;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author lcsilva
 */
@Entity
@Table(name = "user")
public class User extends BaseEntity<Long> {

    /**
	 * 
	 */
    private static final long serialVersionUID = 6049303229236483967L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_user")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_birth")
    private Calendar dateBirth;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "id_address")
    private Address address;

    @Column(nullable = false, name = "login", unique = true)
    private String login;

    @Column(nullable = false, name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "id_type_user")
    private TypeUser typeUser;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contact> contacts;

    @ManyToMany
    @JoinTable(name = "discipline_user", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_discipline"))
    private Set<Discipline> disciplines;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "listUserIncription")
    private List<SampleExam> sampleExams;

    @Lob
    @Column(name = "photo_user")
    private byte[] photo;
    
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "date_creation", nullable = false)
//    private Calendar dateCreation;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Calendar getDateBirth() {
	return dateBirth;
    }

    public void setDateBirth(Calendar dateBirth) {
	this.dateBirth = dateBirth;
    }

    public Address getAddress() {
	return address;
    }

    public void setAddress(Address address) {
	this.address = address;
    }

    public TypeUser getTypeUser() {
	return typeUser;
    }

    public void setTypeUser(TypeUser typeUser) {
	this.typeUser = typeUser;
    }

    public List<Contact> getContacts() {
	return contacts;
    }

    public void setContacts(List<Contact> contacts) {
	this.contacts = contacts;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public byte[] getPhoto() {
	return photo;
    }

    public void setPhoto(byte[] photo) {
	this.photo = photo;
    }

    public String getLogin() {
	return login;
    }

    public void setLogin(String login) {
	this.login = login;
    }

    public Set<Discipline> getDisciplines() {
	return disciplines;
    }

    public void setDisciplines(Set<Discipline> disciplines) {
	this.disciplines = disciplines;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	User other = (User) obj;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	return true;
    }

    public List<SampleExam> getSampleExams() {
        return sampleExams;
    }

    public void setSampleExams(List<SampleExam> sampleExams) {
        this.sampleExams = sampleExams;
    }

//    public Calendar getDateCreation() {
//        return dateCreation;
//    }
//
//    public void setDateCreation(Calendar dateCreation) {
//        this.dateCreation = dateCreation;
//    }

    

}
