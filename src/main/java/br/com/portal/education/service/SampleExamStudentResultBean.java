package br.com.portal.education.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.portal.education.dao.SampleExamStudentResultDAO;
import br.com.portal.education.entity.SampleExam;
import br.com.portal.education.entity.SampleExamStudentResult;
import br.com.portal.education.entity.User;
import br.com.portal.education.exception.PortalEducationApplicationException;

@Stateless
public class SampleExamStudentResultBean {

    @Inject
    private SampleExamStudentResultDAO SampleExamStudentResultDAO;

    public void create(SampleExamStudentResult sampleExamStudentResult) throws PortalEducationApplicationException {
	this.SampleExamStudentResultDAO.insert(sampleExamStudentResult);
    }
    
    
    public SampleExamStudentResult findByUser(User user, SampleExam sampleExam) {
	return this.SampleExamStudentResultDAO.findByUser(user, sampleExam);
    }

}
