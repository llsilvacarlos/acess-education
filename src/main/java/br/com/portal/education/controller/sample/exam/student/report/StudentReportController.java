package br.com.portal.education.controller.sample.exam.student.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.portal.education.controller.BaseController;
import br.com.portal.education.controller.user.UserPhotoController;
import br.com.portal.education.entity.Discipline;
import br.com.portal.education.entity.Question;
import br.com.portal.education.entity.QuestionResponse;
import br.com.portal.education.entity.SampleExam;
import br.com.portal.education.entity.SampleExamStudentResult;
import br.com.portal.education.entity.User;
import br.com.portal.education.exception.PortalEducationApplicationException;
import br.com.portal.education.service.SampleExamBean;
import br.com.portal.education.service.SampleExamStudentResultBean;
import br.com.portal.education.service.UserBean;
import br.com.portal.education.util.StudentQuestionResult;
import br.com.portal.education.util.vo.SampleExamStudentVO;

@ManagedBean
@SessionScoped
public class StudentReportController extends BaseController {

    Logger logger = Logger.getLogger(StudentReportController.class);

    @Inject
    private UserBean userBean;

    @Inject
    private SampleExamBean sampleExamBean;

    @Inject
    private StudentQuestionResult studentQuestionResult;

    @Inject
    private SampleExamStudentResultBean sampleExamStudentResultBean;
    
    
    @ManagedProperty(value = "#{userPhotoController}")
    private UserPhotoController userPhotoController;

    private SampleExam sampleExam;

    private User user;

    private List<SampleExam> sampleExams = new ArrayList<SampleExam>();

    private List<User> listUsers = new ArrayList<User>();
    
    public List<SampleExamStudentVO> sampleExamStudentVOs;

    private CartesianChartModel categoryModel;

    public void resultSampleStudent() {
	sampleExam = sampleExamBean.find(sampleExam.getId());
	listUsers = sampleExam.getListUserIncription();
    }

    public String viewReport() {
	categoryModel = new CartesianChartModel();
	try {
	    loadBarChart();
	} catch (PortalEducationApplicationException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return "viewReportUser";
    }

    private void loadBarChart() throws PortalEducationApplicationException {
	SampleExamStudentResult sampleExamStudentResult = sampleExamStudentResultBean.findByUser(user, sampleExam);
	List<Question> questionExam = sampleExamBean.find(sampleExam.getId()).getQuestions();
	if (sampleExamStudentResult != null) {
	    
	    List<QuestionResponse> tmp = new ArrayList<QuestionResponse>();
	    
	    for (QuestionResponse questionResponse : sampleExamStudentResult.getQuestionsResponse()) {
		
		try {
		    QuestionResponse questionTemp = (QuestionResponse) questionResponse.clone();
		    questionTemp.setIsCorrect(Boolean.TRUE);
		    tmp.add(questionTemp);
		} catch (CloneNotSupportedException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		
	    }
	    studentQuestionResult.verifyCorrectQuestion(questionExam, tmp);
	    Map<Discipline, Long> chartDate = studentQuestionResult.getChartDate();
	    for (Discipline discipline : chartDate.keySet()) {
		ChartSeries seriesDiscipline = new ChartSeries();
		seriesDiscipline.setLabel(discipline.getDescription());
		seriesDiscipline.set(this.sampleExam.getDescription(), chartDate.get(discipline));

		categoryModel.addSeries(seriesDiscipline);
	    }
	}
	userPhotoController.loadPhoto(user.getPhoto(), user.getId().toString());
	sampleExamStudentVOs = studentQuestionResult.getSampleExamStudentVOs();
    }

    private void clearField() {
	sampleExams = sampleExamBean.findALL();
	listUsers = new ArrayList<User>();
	user = null;
	userPhotoController.setContent(null);
    }

    public String listAll() {
	clearField();
	return "listAllReportSchedule";
    }

    public List<SampleExam> getSampleExams() {
	return sampleExams;
    }

    public void setSampleExams(List<SampleExam> sampleExams) {
	this.sampleExams = sampleExams;
    }

    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    public List<User> getListUsers() {
	return listUsers;
    }

    public void setListUsers(List<User> listUsers) {
	this.listUsers = listUsers;
    }

    public SampleExam getSampleExam() {
	return sampleExam;
    }

    public void setSampleExam(SampleExam sampleExam) {
	this.sampleExam = sampleExam;
    }

    public List<SampleExamStudentVO> getListResultStudentVO() {
	return  sampleExamStudentVOs ;
    }
    // @Inject
    // private SampleExamBean sampleExamBean;
    //
    // @Inject
    // private StudentQuestionResult studentQuestionResult;
    //
    // @Inject
    // private SampleExamStudentResultBean sampleExamStudentResultBean;
    //
    // private SampleExamStudentResult sampleExamStudentResult;
    //
    // private CartesianChartModel categoryModel;
    //
    // private SampleExam sampleExam;
    //
    // private List<QuestionResponse> questionResponsesStudent = new ArrayList<QuestionResponse>();
    //
    // private List<QuestionResponse> questionSelectedStudent = new ArrayList<QuestionResponse>();
    //
    // /**
    // * Questoes dos alunos
    // */
    // private List<Question> listQuestionCorrect = new ArrayList<Question>();
    //
    // private List<Question> listQuestionIncorrect = new ArrayList<Question>();
    //
    // private List<SampleExam> sampleExams = new ArrayList<SampleExam>();
    //
    // @PostConstruct
    // private void init() {
    // clearField();
    // }
    //
    // public void rowSelected(SelectEvent event) {
    // QuestionResponse questionResponse = (QuestionResponse) event.getObject();
    // if (!questionSelectedStudent.contains(questionResponse)) {
    // questionResponse.setIsCorrect(Boolean.TRUE);
    // questionSelectedStudent.add(questionResponse);
    // }
    // }
    //
    // public void rowUnselected(UnselectEvent event) {
    // QuestionResponse questionResponse = (QuestionResponse) event.getObject();
    // questionResponse.setIsCorrect(Boolean.FALSE);
    // questionSelectedStudent.remove(questionResponse);
    // }
    //
    // public void cancel() {
    // try {
    // sampleExamBean.find(sampleExam.getId());
    // sampleExam.getListUserIncription().remove(getUserAuthenticated());
    // sampleExamBean.update(sampleExam);
    // clearField();
    // addMessageInfo("msg_schedule_cancel_student", null);
    // } catch (PortalEducationApplicationException e) {
    // redirectErroPage();
    // }
    //
    // }
    //
    // public String finishSampleExamStudent() {
    // createSampleExamStudentVO();
    // sampleExamStudentResult = new SampleExamStudentResult();
    // sampleExamStudentResult.setQuestionsResponse(questionSelectedStudent);
    // sampleExamStudentResult.setSampleExam(sampleExam);
    // sampleExamStudentResult.setUser(getUserAuthenticated());
    // sampleExamStudentResult.setTotalNote(studentQuestionResult.getNoteTotalExam());
    // try {
    // sampleExamStudentResultBean.create(sampleExamStudentResult);
    // } catch (PortalEducationApplicationException e) {
    // logger.error(e);
    // }
    // return "resultStudentPage";
    // }
    //
    // public String listAll() {
    // clearField();
    // return "listAllMySchedule";
    // }
    //
    // public String resultSampleStudent() {
    // return "sampleResultStudent";
    // }
    //
    // private void clearField() {
    // sampleExams = userBean.findAllIscriptionSampleExam(getUserAuthenticated());
    // questionResponsesStudent = new ArrayList<QuestionResponse>();
    // questionSelectedStudent = new ArrayList<QuestionResponse>();
    // sampleExam = null;
    // categoryModel = new CartesianChartModel();
    // }
    //
    // public List<SampleExam> getSampleExams() {
    // return sampleExams;
    // }
    //
    // public void setSampleExams(List<SampleExam> sampleExams) {
    // this.sampleExams = sampleExams;
    // }
    //
    // public SampleExam getSampleExam() {
    // return sampleExam;
    // }
    //
    // public void setSampleExam(SampleExam sampleExam) {
    // this.sampleExam = sampleExam;
    // }
    //
    // public List<QuestionResponse> getQuestionResponsesStudent() {
    // return questionResponsesStudent;
    // }
    //
    // public void setQuestionResponsesStudent(List<QuestionResponse> questionResponsesStudent) {
    // this.questionResponsesStudent = questionResponsesStudent;
    // }
    //
    // public List<Question> getListQuestionCorrect() {
    // return listQuestionCorrect;
    // }
    //
    // public List<Question> getListQuestionIncorrect() {
    // return listQuestionIncorrect;
    // }
    //
    // public void setListQuestionCorrect(List<Question> listQuestionCorrect) {
    // this.listQuestionCorrect = listQuestionCorrect;
    // }
    //
    // public void setListQuestionIncorrect(List<Question> listQuestionIncorrect) {
    // this.listQuestionIncorrect = listQuestionIncorrect;
    // }
    //
    // public List<SampleExamStudentVO> getListResultStudentVO() {
    // return studentQuestionResult.getSampleExamStudentVOs();
    // }
    //
    // private void createSampleExamStudentVO() {
    // List<Question> questionExam = sampleExamBean.find(sampleExam.getId()).getQuestions();
    // studentQuestionResult.verifyCorrectQuestion(questionExam, questionSelectedStudent);
    // Map<Discipline, Long> chartDate = studentQuestionResult.getChartDate();
    // for (Discipline discipline : chartDate.keySet()) {
    // ChartSeries seriesDiscipline = new ChartSeries();
    // seriesDiscipline.setLabel(discipline.getDescription());
    // seriesDiscipline.set(this.sampleExam.getDescription(), chartDate.get(discipline));
    // categoryModel.addSeries(seriesDiscipline);
    //
    // }
    // }
    //
    // public Long getTotalNoteExam() {
    // return studentQuestionResult.getNoteTotalExam();
    // }
    //
    public CartesianChartModel getCategoryModel() {
	return categoryModel;
    }

    public UserPhotoController getUserPhotoController() {
        return userPhotoController;
    }

    public void setUserPhotoController(UserPhotoController userPhotoController) {
        this.userPhotoController = userPhotoController;
    }

}
