package br.com.portal.education.controller.tutoring;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import br.com.portal.education.controller.BaseController;
import br.com.portal.education.entity.Tutoring;
import br.com.portal.education.service.TutoringBean;

@ManagedBean
@SessionScoped
public class TutoringController extends BaseController {

    private List<Tutoring> listTutoring = new ArrayList<Tutoring>();

    @Inject
    private TutoringBean tutoringBean;

    private Tutoring tutoring;

    public String edit() {
	return "tutoring";
    }

    public String listAll() {
	clearField();
	return "listAllTutoringSchedule";
    }

    private void clearField() {
	listTutoring = tutoringBean.findAll();

    }

    public List<Tutoring> getListTutoring() {
	return listTutoring;
    }

    public void setListTutoring(List<Tutoring> listTutoring) {
	this.listTutoring = listTutoring;
    }

    public Tutoring getTutoring() {
	return tutoring;
    }

    public void setTutoring(Tutoring tutoring) {
	this.tutoring = tutoring;
    }
}
