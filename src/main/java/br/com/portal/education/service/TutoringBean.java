package br.com.portal.education.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.portal.education.dao.TutoringDAO;
import br.com.portal.education.entity.Tutoring;
import br.com.portal.education.exception.PortalEducationApplicationException;

@Stateless
public class TutoringBean {
    
    @Inject
    private TutoringDAO tutoringDAO;

    public List<Tutoring> findAll() {
	return tutoringDAO.findAll();
    }

    public void create(Tutoring tutoring) throws PortalEducationApplicationException {
	this.tutoringDAO.insert(tutoring);
    }
    
    public Tutoring update(Tutoring tutoring) throws PortalEducationApplicationException {
	return this.tutoringDAO.update(tutoring);
    }
    
    public void delete(Tutoring tutoring) {
	this.tutoringDAO.delete(tutoring.getId());
    }
}
