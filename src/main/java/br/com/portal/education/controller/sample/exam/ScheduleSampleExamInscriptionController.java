package br.com.portal.education.controller.sample.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import br.com.portal.education.controller.BaseController;
import br.com.portal.education.entity.SampleExam;
import br.com.portal.education.entity.SampleStatus;
import br.com.portal.education.entity.User;
import br.com.portal.education.exception.PortalEducationApplicationException;
import br.com.portal.education.service.SampleExamBean;
import br.com.portal.education.service.SampleStatusBean;

@ManagedBean(name = "scheduleSampleExamInscriptionController")
@SessionScoped
public class ScheduleSampleExamInscriptionController extends BaseController {

    @Inject
    private SampleExamBean sampleExamBean;

    @Inject
    private SampleStatusBean sampleStatusBean;

    private SampleExam sampleExam;

    private SampleStatus sampleStatus;

    private List<SelectItem> listSampleStatus = new ArrayList<SelectItem>();

    private ScheduleEvent event = new DefaultScheduleEvent();

    private ScheduleModel eventModel;

    @PostConstruct
    public void init() {
	clearField();
    }

    private void clearField() {
	createScheduleModel();
    }

    public String list() {
	clearField();
	return "listScheduleInscriptionSample";
    }

    private void createScheduleModel() {
	eventModel = new DefaultScheduleModel();
	List<SampleExam> sampleExams = sampleExamBean.findALL();
	for (SampleExam sampleExam : sampleExams) {
	    eventModel.addEvent(new DefaultScheduleEvent(sampleExam.getDescription(), sampleExam.getDataStart().getTime(), sampleExam.getDataEnd().getTime(),
		    sampleExam));
	}

    }

    public void onEventSelect(SelectEvent selectEvent) {
	event = (ScheduleEvent) selectEvent.getObject();
	sampleExam = (SampleExam) event.getData();
	sampleStatus = sampleExam.getSampleStatus();
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
	ScheduleEvent scheduleEvent = event.getScheduleEvent();
	Date startDate = scheduleEvent.getStartDate();
	Date endDate = scheduleEvent.getEndDate();
	changeEvent(scheduleEvent, startDate, endDate);
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
	ScheduleEvent scheduleEvent = event.getScheduleEvent();
	Date startDate = scheduleEvent.getStartDate();
	Date endDate = scheduleEvent.getEndDate();
	changeEvent(scheduleEvent, startDate, endDate);
    }

    private void changeEvent(ScheduleEvent scheduleEvent, Date startDate, Date endDate) {
	try {
	    SampleExam sampleExam = (SampleExam) scheduleEvent.getData();
	    sampleExam.getDataStart().setTime(startDate);
	    sampleExam.getDataEnd().setTime(endDate);
	    sampleExamBean.update(sampleExam);
	    addMessageInfo("event_update_sucess", null);
	} catch (PortalEducationApplicationException e) {
	    redirectErroPage();
	}
    }

    public void createInscription() {
	try {
	    if (!verifyQuantity()) {
		addMessageInfo("msg_error_inscription_exceeded", null);
	    } else {

		if (sampleExam.getListUserIncription() == null) {
		    sampleExam.setListUserIncription(new ArrayList<User>());
		}

		if (sampleExam.getListUserIncription().contains(getUserAuthenticated())) {
		    addMessageInfo("msg_erro_already_inscription", null);
		} else {
		    sampleExam.getListUserIncription().add(getUserAuthenticated());
		    sampleExamBean.update(sampleExam);
		    addMessageInfo("msg_inscription_sucess", null);
		}
	    }
	} catch (PortalEducationApplicationException e) {
	    redirectErroPage();
	}
    }

    private boolean verifyQuantity() {
	int quantityAvailable = sampleExam.getQuantityAvailable();
	int quantityInscription = sampleExam.getListUserIncription().size();
	return quantityInscription + 1 <= quantityAvailable;

    }

    public ScheduleModel getEventModel() {
	return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
	this.eventModel = eventModel;
    }

    public ScheduleEvent getEvent() {
	return event;
    }

    public void setEvent(ScheduleEvent event) {
	this.event = event;
    }

    public SampleStatus getSampleStatus() {
	return sampleStatus;
    }

    public void setSampleStatus(SampleStatus sampleStatus) {
	this.sampleStatus = sampleStatus;
    }

    public List<SelectItem> getListSampleStatus() {

	if (listSampleStatus.size() == 0) {
	    for (SampleStatus sampleStatus : sampleStatusBean.findAll()) {
		listSampleStatus.add(new SelectItem(sampleStatus, sampleStatus.getSampleStatus()));
	    }
	}

	return listSampleStatus;

    }

    public SampleExam getSampleExam() {
	return sampleExam;
    }

    public void setSampleExam(SampleExam sampleExam) {
	this.sampleExam = sampleExam;
    }

    public Integer getQuantityInscription() {
	if (sampleExam != null) {
	    return sampleExam.getListUserIncription().size();
	}
	return 0;
    }

}
