package br.com.portal.education.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "question_response")
public class QuestionResponse extends BaseEntity<Long> implements Comparable<QuestionResponse>, Cloneable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -8001620508087009202L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_question_response")
    private Long id;

    @Column(name = "description_response", nullable = false)
    private String descriptionResponse;
    
    @Column(name = "iscorrect", nullable = true)
    private Boolean isCorrect = false;

    @ManyToOne  
    @JoinColumn(name = "id_question")  
    private Question question;  
    
    public String getDescriptionResponse() {
	return descriptionResponse;
    }

    public void setDescriptionResponse(String descriptionResponse) {
	this.descriptionResponse = descriptionResponse.toUpperCase();
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    
    @Override
    public int compareTo(QuestionResponse o) {
	return this.id.compareTo(o.getId());
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
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
	QuestionResponse other = (QuestionResponse) obj;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	return true;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
