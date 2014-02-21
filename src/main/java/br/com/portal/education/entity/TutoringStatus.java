package br.com.portal.education.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "tutoring_status")
public class TutoringStatus extends BaseEntity<Long> {

    /**
  	 * 
  	 */
    private static final long serialVersionUID = 6520593491366571616L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_tutoring_status")
    private Long id;

    @Column(name = "tutoring_status", nullable = false)
    private String tutoringStatus;

    public Long getId() {
	return id;
    }

    public String getTutoringStatus() {
	return tutoringStatus;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public void setTutoringStatus(String tutoringStatus) {
	this.tutoringStatus = tutoringStatus;
    }

}
