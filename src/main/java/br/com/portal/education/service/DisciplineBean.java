package br.com.portal.education.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.portal.education.dao.DisciplineDAO;
import br.com.portal.education.entity.Discipline;
import br.com.portal.education.exception.PortalEducationApplicationException;

@Stateless
public class DisciplineBean {
    
    @Inject
    private DisciplineDAO disciplineDAO;

    public List<Discipline> findAll() {
	return disciplineDAO.findAll();
    }

    public void create(Discipline discipline) throws PortalEducationApplicationException {
	this.disciplineDAO.insert(discipline);
    }
    
    public Discipline update(Discipline discipline) throws PortalEducationApplicationException {
	return this.disciplineDAO.update(discipline);
    }
    
    public void delete(Discipline discipline) {
	this.disciplineDAO.delete(discipline.getId());
    }
}
