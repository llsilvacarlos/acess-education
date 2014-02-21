package br.com.portal.education.util.vo;

import java.io.Serializable;
import java.util.List;

import br.com.portal.education.entity.Question;
import br.com.portal.education.entity.QuestionResponse;

public class SampleExamStudentVO implements Serializable {

    private Question question;
    private List<QuestionResponse> responsesStudents;
    private Long noteQuestion;

    public SampleExamStudentVO(Question question, List<QuestionResponse> responsesStudents, Long noteQuestion) {
	this.question = question;
	this.responsesStudents = responsesStudents;
	this.noteQuestion = noteQuestion;
    }
    public SampleExamStudentVO() {
	// TODO Auto-generated constructor stub
    }


    public List<QuestionResponse> getResponsesStudents() {
	return responsesStudents;
    }

    public void setResponsesStudents(List<QuestionResponse> responsesStudents) {
	this.responsesStudents = responsesStudents;
    }

    public Question getQuestion() {
	return question;
    }

    public void setQuestion(Question question) {
	this.question = question;
    }

    public Long getNoteQuestion() {
        return noteQuestion;
    }

    public void setNoteQuestion(Long noteQuestion) {
        this.noteQuestion = noteQuestion;
    }

   

}
