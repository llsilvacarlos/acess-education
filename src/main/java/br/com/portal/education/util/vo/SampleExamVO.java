package br.com.portal.education.util.vo;

import java.io.Serializable;

import br.com.portal.education.entity.SampleExam;
import br.com.portal.education.util.SampleExamStudentEnum;

public class SampleExamVO implements Serializable{
    
    private SampleExam sampleExam;
    
    private SampleExamStudentEnum examStudentEnum;
    
    public SampleExamVO(SampleExam sampleExam, SampleExamStudentEnum examStudentEnum) {
	this.sampleExam = sampleExam;
	this.examStudentEnum = examStudentEnum;
    }
    

    public SampleExam getSampleExam() {
        return sampleExam;
    }

    public void setSampleExam(SampleExam sampleExam) {
        this.sampleExam = sampleExam;
    }


    public SampleExamStudentEnum getExamStudentEnum() {
        return examStudentEnum;
    }


    public void setExamStudentEnum(SampleExamStudentEnum examStudentEnum) {
        this.examStudentEnum = examStudentEnum;
    }

}
