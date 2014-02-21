package br.com.portal.education.controller.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import br.com.portal.education.controller.BaseController;
import br.com.portal.education.entity.Discipline;
import br.com.portal.education.service.DisciplineBean;

@ManagedBean(name = "userDisciplineController")
@SessionScoped
public class UserDisciplineController extends BaseController {

    @Inject
    private DisciplineBean disciplineBean;

    private List<Discipline> targetDiscipline = new ArrayList<Discipline>();

    private DualListModel<Discipline> disciplinesDualListModel;

    public void onTransfer(TransferEvent event) {
	targetDiscipline.clear();
	for (Object item : event.getItems()) {
	   Discipline discipline = (Discipline) item;
	   targetDiscipline.add(discipline);
	}
    }

    public void loadList(Collection<Discipline> target) {
	targetDiscipline.clear();
	List<Discipline> listAll = disciplineBean.findAll();
	if(target == null){ 
	    target = new ArrayList<Discipline>();
	}
	targetDiscipline.addAll(target);
	removeExisting(listAll, target);	
	disciplinesDualListModel = new DualListModel<Discipline>(listAll, new ArrayList<Discipline>(target));
    }

    private void removeExisting(List<Discipline> listAll, Collection<Discipline> target) {
	for (Discipline discipline : target) {
	    if(listAll.contains(discipline)){
		listAll.remove(discipline);
	    }
	    
	}
	
    }

    public DualListModel<Discipline> getDisciplinesDualListModel() {
	return disciplinesDualListModel;
    }

    public void setDisciplinesDualListModel(DualListModel<Discipline> disciplinesDualListModel) {
	this.disciplinesDualListModel = disciplinesDualListModel;
    }

    public List<Discipline> getTargetDiscipline() {
	return targetDiscipline;
    }

}
