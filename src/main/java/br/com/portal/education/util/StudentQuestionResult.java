package br.com.portal.education.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.portal.education.entity.Discipline;
import br.com.portal.education.entity.Question;
import br.com.portal.education.entity.QuestionResponse;
import br.com.portal.education.student.chart.DisciplineComparator;
import br.com.portal.education.student.chart.StudentChartUtil;
import br.com.portal.education.util.vo.SampleExamStudentVO;

@Named
@RequestScoped
public class StudentQuestionResult {

    Logger logger = Logger.getLogger(StudentQuestionResult.class);

    private List<SampleExamStudentVO> sampleExamStudentVOs = new ArrayList<SampleExamStudentVO>();

    private Map<Discipline, Integer> totalSampleExam = null;

    private Map<Discipline, Long> mapGroupExamDisciplineStrudent = new TreeMap<Discipline, Long>(new DisciplineComparator());

    private Long noteTotalExam = 0L;

    @Inject
    private StudentChartUtil studentChartUtil;

    public void verifyCorrectQuestion(List<Question> listSampleExam, List<QuestionResponse> reponseStudent) {

	totalSampleExam = new HashMap<Discipline, Integer>();

	Map<Question, List<QuestionResponse>> mapStudent = getMapStudent(reponseStudent);

	for (QuestionResponse questionResponse : reponseStudent) {
	    List<QuestionResponse> questionFalse = mapStudent.get(questionResponse.getQuestion());
	    questionFalse.set(questionFalse.indexOf(questionResponse), questionResponse);

	}

	long noteQuestion = 0;
	for (Question question : listSampleExam) {

	    totalSampleExam.put(question.getDiscipline(), question.getQuestionResponses().size());

	    if (!isMultResponseQuestion(question)) {
		if (mapStudent.get(question) !=null && !isMultResponseQuestion(mapStudent.get(question))) {
		    if (checkResponse(question.getQuestionResponses(), mapStudent.get(question))) {
			noteQuestion++;
			mapGroupExamDisciplineStrudent.put(question.getDiscipline(), noteQuestion);
			noteTotalExam += 1;
		    }

		}
	    } else {
		noteQuestion = getNoteMultQuestion(question, mapStudent.get(question));

		mapGroupExamDisciplineStrudent.put(question.getDiscipline(), noteQuestion);
	    }
	    SampleExamStudentVO sampleExamStudentVO = new SampleExamStudentVO(question, mapStudent.get(question), noteQuestion);
	    sampleExamStudentVOs.add(sampleExamStudentVO);

	    noteQuestion = 0;
	}

	studentChartUtil.loadMapDiscipline(listSampleExam, mapGroupExamDisciplineStrudent);

    }

    private Map<Question, List<QuestionResponse>> getMapStudent(List<QuestionResponse> studentResponse) {
	Map<Question, List<QuestionResponse>> map = new HashMap<Question, List<QuestionResponse>>();

	for (QuestionResponse questionResponse : studentResponse) {
	    if (map.get(questionResponse.getQuestion()) == null) {
		map.put(questionResponse.getQuestion(), new ArrayList<QuestionResponse>());
		settingResponse(map, questionResponse);
	    }

	}

	return map;

    }

    private void settingResponse(Map<Question, List<QuestionResponse>> map, QuestionResponse questionResponse) {
	try {
	    Question question = questionResponse.getQuestion();
	    for (QuestionResponse questionCorrects : question.getQuestionResponses()) {
		QuestionResponse tmp = (QuestionResponse) questionCorrects.clone();
		tmp.setIsCorrect(Boolean.FALSE);
		map.get(question).add(tmp);
	    }
	} catch (CloneNotSupportedException e) {
	    // TODO: handle exception
	}

    }

    private boolean checkResponse(List<QuestionResponse> responseCorrect, List<QuestionResponse> responseStudent) {

	boolean response = true;
	for (QuestionResponse questionResponse : responseStudent) {

	    QuestionResponse questionC = responseCorrect.get(responseCorrect.indexOf(questionResponse));

	    if (!questionC.getIsCorrect().equals(questionResponse.getIsCorrect())) {
		response = false;
	    }

	}

	return response;
    }

    private Long getNoteMultQuestion(Question questionCorrect, List<QuestionResponse> listQuestionResponseUser) {
	long noteQuestion = 0l;

	int numberNP = getQuestionNP(questionCorrect);

	List<QuestionResponse> listQuestionNTPC = getListQuestionNTPC(questionCorrect);

	int numberNTPC = listQuestionNTPC.size();

	List<QuestionResponse> listQuestionNPC = getListQuestionNPC(listQuestionResponseUser, listQuestionNTPC);

	int numberNPC = listQuestionNPC.size();

	int numberNPI = getQuestionNPI(listQuestionResponseUser, listQuestionNTPC);

	if (numberNPC > numberNPI) {
	    // =(NP-(NTPC-(NPC -NI)))/NP
	    noteQuestion = (numberNP - (numberNTPC - (numberNPC - numberNPI))) / numberNP;
	}

	return noteQuestion;
    }

    private List<QuestionResponse> getListQuestionNTPC(Question question) {
	List<QuestionResponse> questionsNTPC = new ArrayList<QuestionResponse>();
	List<QuestionResponse> questionResponses = question.getQuestionResponses();
	for (QuestionResponse questionResponse : questionResponses) {
	    questionsNTPC.add(questionResponse);
	}
	return questionsNTPC;
    }

    private List<QuestionResponse> getListQuestionNPC(List<QuestionResponse> listQuestionResponseUser, List<QuestionResponse> listQuestionNTPC) {
	List<QuestionResponse> questionsNPC = new ArrayList<QuestionResponse>();
	for (QuestionResponse questionResponse : listQuestionNTPC) {
	    int indexOf = listQuestionResponseUser.indexOf(questionResponse);
	    if (indexOf != -1) {
		QuestionResponse questionResponseNPCCorrect = listQuestionResponseUser.get(indexOf);
		questionsNPC.add(questionResponseNPCCorrect);
	    }
	}
	return questionsNPC;
    }

    private int getQuestionNPI(List<QuestionResponse> listQuestionResponseUser, List<QuestionResponse> listQuestionNTPC) {
	int npi = 0;
	for (QuestionResponse questionResponse : listQuestionNTPC) {
	    int indexOf = listQuestionResponseUser.indexOf(questionResponse);
	    if (indexOf == -1) {
		npi++;
	    }
	}

	return npi;
    }

    private int getQuestionNP(Question question) {
	return question.getQuestionResponses().size();
    }

    private boolean isMultResponseQuestion(Question question) {
	List<QuestionResponse> questionResponses = question.getQuestionResponses();
	return isMultResponseQuestion(questionResponses);
    }

    private boolean isMultResponseQuestion(List<QuestionResponse> questionResponses) {
	int count = 0;
	boolean returnResponseMult = false;
	for (QuestionResponse questionResponse : questionResponses) {
	    if (questionResponse.getIsCorrect() != null && questionResponse.getIsCorrect()) {
		count++;
	    }

	}
	if (count > 1) {
	    returnResponseMult = true;
	}
	return returnResponseMult;
    }

    public List<SampleExamStudentVO> getSampleExamStudentVOs() {
	return sampleExamStudentVOs;
    }

    public Long getNoteTotalExam() {
	return noteTotalExam;
    }

    public Map<Discipline, Integer> getTotalSampleExam() {
	return totalSampleExam;
    }

    public Map<Discipline, Long> getChartDate() {
	return studentChartUtil.getChartDate();
    }
}
