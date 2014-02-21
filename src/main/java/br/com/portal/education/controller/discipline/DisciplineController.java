package br.com.portal.education.controller.discipline;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import br.com.portal.education.controller.BaseController;
import br.com.portal.education.entity.Discipline;
import br.com.portal.education.exception.PortalEducationApplicationException;
import br.com.portal.education.service.DisciplineBean;

@ManagedBean(name = "disciplineController")
@SessionScoped
public class DisciplineController extends BaseController {

    @Inject
    private DisciplineBean disciplineBean;
    private Discipline discipline = new Discipline();
    private List<Discipline> disciplines = null;

    @PostConstruct
    public void init() {
	discipline = new Discipline();
    }

    public void save() {
	try {
	    if (discipline.getId() == null) {
		disciplineBean.create(discipline);
		addMessageInfo("msg_discipline_insert", null);
	    } else {
		disciplineBean.update(discipline);
		addMessageInfo("msg_discipline_update", null);
	    }
	} catch (PortalEducationApplicationException e) {
	    redirectErroPage();
	}

    }

    public String listAllDiscipline() {
	return "displineAll";
    }

    public String edit() {
	return "discipline";
    }

    public String newDiscipline() {
	discipline = new Discipline();
	return "newDiscipline";
    }

    public String delete() {
	disciplineBean.delete(discipline);
	return "disciplineAll";
    }

    public List<Discipline> getDisciplines() {
	disciplines = disciplineBean.findAll();
	return disciplines;
    }

    public void setDisciplines(List<Discipline> disciplines) {
	this.disciplines = disciplines;
    }

    public Discipline getDiscipline() {
	return discipline;
    }

    public void setDiscipline(Discipline discipline) {
	this.discipline = discipline;
    }

}
