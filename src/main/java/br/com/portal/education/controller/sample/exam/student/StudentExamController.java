package br.com.portal.education.controller.sample.exam.student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.portal.education.controller.BaseController;
import br.com.portal.education.entity.Discipline;
import br.com.portal.education.entity.Question;
import br.com.portal.education.entity.QuestionResponse;
import br.com.portal.education.entity.SampleExam;
import br.com.portal.education.entity.SampleExamStudentResult;
import br.com.portal.education.exception.PortalEducationApplicationException;
import br.com.portal.education.service.SampleExamBean;
import br.com.portal.education.service.SampleExamStudentResultBean;
import br.com.portal.education.service.UserBean;
import br.com.portal.education.util.SampleExamStudentEnum;
import br.com.portal.education.util.StudentQuestionResult;
import br.com.portal.education.util.vo.SampleExamStudentVO;
import br.com.portal.education.util.vo.SampleExamVO;

@ManagedBean
@SessionScoped
public class StudentExamController extends BaseController {

    Logger logger = Logger.getLogger(StudentExamController.class);

    @Inject
    private UserBean userBean;

    @Inject
    private SampleExamBean sampleExamBean;

    @Inject
    private StudentQuestionResult studentQuestionResult;

    @Inject
    private SampleExamStudentResultBean sampleExamStudentResultBean;

    private SampleExamStudentResult sampleExamStudentResult;

    private CartesianChartModel categoryModel;

    private SampleExam sampleExam;
    
    private List<SampleExamStudentVO> listResultStudentVO;

    private List<QuestionResponse> questionResponsesStudent = new ArrayList<QuestionResponse>();

    private List<QuestionResponse> questionSelectedStudent = new ArrayList<QuestionResponse>();

    /**
     * Questoes dos alunos
     */
    private List<Question> listQuestionCorrect = new ArrayList<Question>();

    private List<Question> listQuestionIncorrect = new ArrayList<Question>();

    private List<SampleExamVO> sampleExams = new ArrayList<SampleExamVO>();

    private Long totalExam;

    @PostConstruct
    private void init() {
	clearField();
    }

    public void rowSelected(SelectEvent event) {
	QuestionResponse questionResponse = (QuestionResponse) event.getObject();
	if (!questionSelectedStudent.contains(questionResponse)) {
	    questionResponse.setIsCorrect(Boolean.TRUE);
	    questionSelectedStudent.add(questionResponse);
	}
    }

    public void rowUnselected(UnselectEvent event) {
	QuestionResponse questionResponse = (QuestionResponse) event.getObject();
	questionResponse.setIsCorrect(Boolean.FALSE);
	questionSelectedStudent.remove(questionResponse);
    }

    public void cancel() {
	try {
	    sampleExamBean.find(sampleExam.getId());
	    sampleExam.getListUserIncription().remove(getUserAuthenticated());
	    sampleExamBean.update(sampleExam);
	    clearField();
	    addMessageInfo("msg_schedule_cancel_student", null);
	} catch (PortalEducationApplicationException e) {
	    redirectErroPage();
	}

    }

    public String finishSampleExamStudent() {
	createSampleExamStudentVO();
	sampleExamStudentResult = new SampleExamStudentResult();
	sampleExamStudentResult.setQuestionsResponse(questionSelectedStudent);
	sampleExamStudentResult.setSampleExam(sampleExam);
	sampleExamStudentResult.setUser(getUserAuthenticated());
	sampleExamStudentResult.setTotalNote(studentQuestionResult.getNoteTotalExam());
	listResultStudentVO = studentQuestionResult.getSampleExamStudentVOs();
	totalExam = studentQuestionResult.getNoteTotalExam();
	try {
	    sampleExamStudentResultBean.create(sampleExamStudentResult);
	} catch (PortalEducationApplicationException e) {
	    logger.error(e);
	}
	return "resultStudentPage";
    }

    public String listAll() {
	clearField();
	return "listAllMySchedule";
    }

    public String resultSampleStudent() {
	return "sampleResultStudent";
    }

    private void clearField() {
	List<SampleExam> filterList = userBean.findAllIscriptionSampleExam(getUserAuthenticated());
	sampleExams.clear();
	for (SampleExam sampleExam : filterList) {
	    if(sampleExamStudentResultBean.findByUser(getUserAuthenticated(), sampleExam) != null){
		sampleExams.add(new SampleExamVO(sampleExam,SampleExamStudentEnum.FINISH));
	    }else{
		sampleExams.add(new SampleExamVO(sampleExam,SampleExamStudentEnum.NOT_FINISH));
	    }
	}
	questionResponsesStudent = new ArrayList<QuestionResponse>();
	questionSelectedStudent = new ArrayList<QuestionResponse>();
	sampleExam = null;
	categoryModel = new CartesianChartModel();
    }

    public List<SampleExamVO> getSampleExams() {
	return sampleExams;
    }

    public void setSampleExams(List<SampleExamVO> sampleExams) {
	this.sampleExams = sampleExams;
    }

    public SampleExam getSampleExam() {
	return sampleExam;
    }

    public void setSampleExam(SampleExam sampleExam) {
	this.sampleExam = sampleExam;
    }

    public List<QuestionResponse> getQuestionResponsesStudent() {
	return questionResponsesStudent;
    }

    public void setQuestionResponsesStudent(List<QuestionResponse> questionResponsesStudent) {
	this.questionResponsesStudent = questionResponsesStudent;
    }

    public List<Question> getListQuestionCorrect() {
	return listQuestionCorrect;
    }

    public List<Question> getListQuestionIncorrect() {
	return listQuestionIncorrect;
    }

    public void setListQuestionCorrect(List<Question> listQuestionCorrect) {
	this.listQuestionCorrect = listQuestionCorrect;
    }

    public void setListQuestionIncorrect(List<Question> listQuestionIncorrect) {
	this.listQuestionIncorrect = listQuestionIncorrect;
    }

    public List<SampleExamStudentVO> getListResultStudentVO() {
	return listResultStudentVO ;
    }

    private void createSampleExamStudentVO() {
	List<Question> questionExam = sampleExamBean.find(sampleExam.getId()).getQuestions();
	studentQuestionResult.verifyCorrectQuestion(questionExam, questionSelectedStudent);
	Map<Discipline, Long> chartDate = studentQuestionResult.getChartDate();
	for (Discipline discipline : chartDate.keySet()) {
	    ChartSeries seriesDiscipline = new ChartSeries();
	    seriesDiscipline.setLabel(discipline.getDescription());
	    seriesDiscipline.set(this.sampleExam.getDescription(), chartDate.get(discipline));
	    categoryModel.addSeries(seriesDiscipline);

	}
    }

    public Long getTotalNoteExam() {
	return totalExam;
    }

    public CartesianChartModel getCategoryModel() {
	return categoryModel;
    }


}
