package br.com.portal.education.exception;

import java.util.List;

import br.com.portal.education.entity.QuestionResponse;

public class QuestionAnsweredException extends Exception {

    private List<QuestionResponse> questionResponses;

    public QuestionAnsweredException() {
	super();
    }

    public QuestionAnsweredException(String message) {
	super(message);
    }

    public QuestionAnsweredException(Exception exception) {
	super(exception);
    }

    public QuestionAnsweredException(String string, List<QuestionResponse> responses) {
	this.questionResponses = questionResponses;
    }

    public List<QuestionResponse> getQuestionResponses() {
        return questionResponses;
    }
}
