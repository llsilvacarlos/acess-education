package br.com.portal.education.controller.sample.exam;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

import br.com.portal.education.controller.BaseController;
import br.com.portal.education.entity.Discipline;
import br.com.portal.education.entity.Question;
import br.com.portal.education.entity.QuestionResponse;
import br.com.portal.education.service.DisciplineBean;

@ManagedBean(name = "sampleExamQuestionController")
@SessionScoped
public class SampleExamQuestionController extends BaseController {
    
    
    @Inject
    private DisciplineBean disciplineBean;

    private Question question;

    private List<QuestionResponse> questionResponses = new ArrayList<QuestionResponse>();

    private List<QuestionResponse> questionResponsesCorrect = new ArrayList<QuestionResponse>();

    private QuestionResponse questionResponse = new QuestionResponse();
    
    private List<SelectItem> disciplines = new ArrayList<SelectItem>();
    
    

    public void addQuestionResponse() {
	if (!contains(questionResponse)) {
	    questionResponses.add(questionResponse);
	}
	questionResponse = new QuestionResponse();
    }
    
    private boolean contains(QuestionResponse questionResponses){
	
	for(QuestionResponse response: this.questionResponses){
	    if(response.getDescriptionResponse().equals(questionResponses.getDescriptionResponse())){
		return true;
	    }
	}
	return false;
	
    }
    

    public Question getQuestion() {
	return question;
    }

    public void setQuestion(Question question) {
	this.question = question;
    }

    public List<QuestionResponse> getQuestionResponses() {
	return questionResponses;
    }

    public void setQuestionResponses(List<QuestionResponse> questionResponses) {
	this.questionResponses = questionResponses;
    }

    public QuestionResponse getQuestionResponse() {
	return questionResponse;
    }

    public void setQuestionResponse(QuestionResponse questionResponse) {
	this.questionResponse = questionResponse;
    }

    public List<QuestionResponse> getQuestionResponsesCorrect() {
	return questionResponsesCorrect;
    }

    public void setQuestionResponsesCorrect(List<QuestionResponse> questionResponsesCorrect) {
	this.questionResponsesCorrect = questionResponsesCorrect;
    }

    public void clearField() {
	question = new Question();
	questionResponses.clear();
	questionResponsesCorrect.clear();
	questionResponse = new QuestionResponse();
    }

    public void delete() {
	for (int i = 0; i< questionResponses.size(); i++) {
	    QuestionResponse questionInterator = questionResponses.get(i);
	    if(questionInterator.getDescriptionResponse().equals(questionResponse.getDescriptionResponse())){
		questionResponses.remove(i);
	    }
	}
    }

    public List<SelectItem> getDisciplines() {
	if (disciplines.size() == 0) {
	    for (Discipline discipline : disciplineBean.findAll()) {
		disciplines.add(new SelectItem(discipline, discipline.getDescription()));
	    }
	}

	return disciplines;
    }

}
