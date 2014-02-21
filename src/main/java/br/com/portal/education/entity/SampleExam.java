package br.com.portal.education.entity;

import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "sample_exam")
public class SampleExam extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_sample_exam")
    private Long id;

    @Column(name = "description", nullable = true)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_start")
    private Calendar dataStart;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_end")
    private Calendar dataEnd;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_sample_exam")
    private List<Question> questions;

    @Column(name = "quantityAvailable")
    private Integer quantityAvailable;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_sample_status", nullable = false)
    private SampleStatus sampleStatus;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "inscription_user", joinColumns = @JoinColumn(name = "id_sample_exam"), inverseJoinColumns = @JoinColumn(name = "id_user"))
    private List<User> listUserIncription;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public List<Question> getQuestions() {
	return questions;
    }

    public Integer getQuantityAvailable() {
	return quantityAvailable;
    }

    public void setQuestions(List<Question> questions) {
	this.questions = questions;
    }

    public void setQuantityAvailable(Integer quantityAvailable) {
	this.quantityAvailable = quantityAvailable;
    }

    public SampleStatus getSampleStatus() {
	return sampleStatus;
    }

    public void setSampleStatus(SampleStatus sampleStatus) {
	this.sampleStatus = sampleStatus;
    }

    public Calendar getDataStart() {
	return dataStart;
    }

    public Calendar getDataEnd() {
	return dataEnd;
    }

    public void setDataStart(Calendar dataStart) {
	this.dataStart = dataStart;
    }

    public void setDataEnd(Calendar dataEnd) {
	this.dataEnd = dataEnd;
    }

    public List<User> getListUserIncription() {
	return listUserIncription;
    }

    public void setListUserIncription(List<User> listUserIncription) {
	this.listUserIncription = listUserIncription;
    }

    public boolean isValidateDateSchedule() {
	if (this.getDataStart().compareTo(this.getDataEnd()) > 0) {
	    return Boolean.FALSE;
	}

	return Boolean.TRUE;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	SampleExam other = (SampleExam) obj;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	return true;
    }

}
