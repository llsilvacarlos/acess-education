/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.portal.education.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.portal.education.dao.UserDAO;
import br.com.portal.education.entity.SampleExam;
import br.com.portal.education.entity.User;
import br.com.portal.education.exception.CryptographyException;
import br.com.portal.education.exception.PortalEducationApplicationException;

/**
 * 
 * @author lcsilva
 */
@Stateless
public class UserBean implements Serializable {

    @Inject
    private UserDAO userDAO;

    public void create(User user) throws PortalEducationApplicationException, CryptographyException {
	userDAO.insert(user);
    }

    public User update(User user) throws PortalEducationApplicationException, CryptographyException {
	return userDAO.update(user);
    }

    public void delete(User user) {
	userDAO.delete(user.getId());
    }

    public List<User> findALL() {
	return userDAO.findAll();
    }

    public User find(Long id) {
	return userDAO.find(id);
    }

    public User findUserByLoginAndPassword(String login, String password) {
	return userDAO.findUserByLoginAndPassword(login, password);
    }

    public List<SampleExam> findAllIscriptionSampleExam(User user) {
	return userDAO.findAllIscriptionSampleExam(user);
    }
    
    
    public User findByLogin(String login){
	return userDAO.findUserByLogin(login);
    }

}
