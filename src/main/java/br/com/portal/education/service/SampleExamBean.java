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

import br.com.portal.education.dao.SampleExamDAO;
import br.com.portal.education.entity.SampleExam;
import br.com.portal.education.entity.User;
import br.com.portal.education.exception.PortalEducationApplicationException;

/**
 * 
 * @author lcsilva
 */
@Stateless
public class SampleExamBean implements Serializable {

    @Inject
    private SampleExamDAO sampleExamDAO;
    
    

    public void create(SampleExam sampleExam) throws PortalEducationApplicationException {
	sampleExamDAO.insert(sampleExam);
    }

    public SampleExam update(SampleExam sampleExam) throws PortalEducationApplicationException {
	return sampleExamDAO.update(sampleExam);
    }


    public List<SampleExam> findALL() {
	return sampleExamDAO.findAll();
    }

    public SampleExam find(Long id) {
	return sampleExamDAO.find(id);
    }


}
