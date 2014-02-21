package br.com.portal.education.controller.sample.exam;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.primefaces.event.FlowEvent;

import br.com.portal.education.controller.BaseController;
import br.com.portal.education.entity.Question;
import br.com.portal.education.entity.QuestionResponse;
import br.com.portal.education.entity.SampleExam;
import br.com.portal.education.exception.PortalEducationApplicationException;
import br.com.portal.education.service.SampleExamBean;
import br.com.portal.education.service.SampleStatusBean;
import br.com.portal.education.util.SampleStatusEnum;

@ManagedBean(name = "sampleExamController")
@SessionScoped
public class SampleExamController extends BaseController {

    @ManagedProperty(value = "#{sampleExamQuestionController}")
    private SampleExamQuestionController sampleExamQuestionController;
    private SampleExam sampleExam;
    private List<Question> listQuestions = new ArrayList<Question>();

    @Inject
    private SampleExamBean sampleExamBean;

    @Inject
    private SampleStatusBean sampleStatusBean;

    @PostConstruct
    public void init() {
	clearField();
    }

    private void clearField() {
	sampleExam = new SampleExam();
	sampleExamQuestionController.clearField();
	listQuestions.clear();
    }

    public void addQuestion() {

	List<QuestionResponse> questionResponseAll = sampleExamQuestionController.getQuestionResponses();

	List<QuestionResponse> questionResponseCorrect = sampleExamQuestionController.getQuestionResponsesCorrect();

	if (questionResponseCorrect.isEmpty()) {
	    addMessageError("mensage_validate_number_question_correct", null);
	} else {

	    settingCorrectAll(questionResponseAll, questionResponseCorrect);

	    Question question = sampleExamQuestionController.getQuestion();
	    question.setQuestionResponses(new ArrayList<QuestionResponse>(questionResponseAll));

	    for (QuestionResponse questionResponse : question.getQuestionResponses()) {
		questionResponse.setQuestion(question);
	    }

	    if (!contains(question)) {
		listQuestions.add(question);
	    }
	    sampleExamQuestionController.clearField();

	}
    }

    private boolean contains(Question newQuestion) {

	for (Question question : this.listQuestions) {
	    if (question.getDescription().equals(newQuestion.getDescription())) {
		return true;
	    }
	}
	return false;

    }

    public void removeQuestion() {
	listQuestions.remove(sampleExamQuestionController.getQuestion());
    }

    private void settingCorrectAll(List<QuestionResponse> questionResponseAll, List<QuestionResponse> questionResponseCorrect) {
	for (QuestionResponse questionResponse : questionResponseCorrect) {
	    getQuestionResponseCorrect(questionResponseAll, questionResponse);
	}
    }
    
    private void getQuestionResponseCorrect(List<QuestionResponse> listQuestionResponse, QuestionResponse questionResponse){
	
	for (QuestionResponse q : listQuestionResponse) {
	    if(q.getDescriptionResponse().equals(questionResponse.getDescriptionResponse())){
		q.setIsCorrect(Boolean.TRUE);
	    }
	}
    }

    public void save() {
	try {
	    getSampleExam().setQuestions(getListQuestions());
	    getSampleExam().setSampleStatus(sampleStatusBean.find(SampleStatusEnum.SCHEDULED.getStatus()));
	    sampleExamBean.create(sampleExam);
	    clearField();
	    addMessageInfo("msg_insert_sample_exam", null);
	} catch (PortalEducationApplicationException e) {
	    redirectErroPage();
	}

    }

    public String onFlowProcess(FlowEvent event) {
	String oldStep = event.getOldStep();
	if (oldStep.equals("sample")) {
	    if (!getSampleExam().isValidateDateSchedule()) {
		addMessageError("msg_validate_date_start_and_date_end", null);
		return "sample";
	    }
	}
	return event.getNewStep();
    }

    public String newSample() {
	clearField();
	return "newSampleExam";
    }

    public SampleExam getSampleExam() {
	return sampleExam;
    }

    public void setSampleExam(SampleExam sampleExam) {
	this.sampleExam = sampleExam;
    }

    public SampleExamQuestionController getSampleExamQuestionController() {
	return sampleExamQuestionController;
    }

    public void setSampleExamQuestionController(SampleExamQuestionController sampleExamQuestionController) {
	this.sampleExamQuestionController = sampleExamQuestionController;
    }

    public List<Question> getListQuestions() {
	return listQuestions;
    }

    public void setListQuestions(List<Question> listQuestions) {
	this.listQuestions = listQuestions;
    }

}
