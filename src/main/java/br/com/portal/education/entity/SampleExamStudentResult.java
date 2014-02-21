package br.com.portal.education.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sample_exam_student_result")
public class SampleExamStudentResult extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_sample_exam_student_result")
    private Long id;

    @ManyToMany
    @JoinTable(name = "question_student_response", joinColumns = @JoinColumn(name = "id_sample_exam_student_result"), inverseJoinColumns = @JoinColumn(name = "id_question_response"))
    private List<QuestionResponse> questionsResponse;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_sample_exam")
    private SampleExam sampleExam;

    @Column(name = "total_note", nullable = false)
    private Long totalNote;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_user")
    private User user;

    public SampleExam getSampleExam() {
	return sampleExam;
    }

    public Long getTotalNote() {
	return totalNote;
    }

    public void setSampleExam(SampleExam sampleExam) {
	this.sampleExam = sampleExam;
    }

    public void setTotalNote(Long totalNote) {
	this.totalNote = totalNote;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public List<QuestionResponse> getQuestionsResponse() {
        return questionsResponse;
    }

    public User getUser() {
        return user;
    }

    public void setQuestionsResponse(List<QuestionResponse> questionsResponse) {
        this.questionsResponse = questionsResponse;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
