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
@Table(name = "contact")
public class Contact extends BaseEntity<Long> {
    /**
	 * 
	 */
    private static final long serialVersionUID = 1671473630787034191L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_contact")
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "fone", nullable = false)
    private String fone;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    public String getEmail() {
	return email;
    }

   

    public void setEmail(String email) {
	this.email = email;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((email == null) ? 0 : email.hashCode());
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
	Contact other = (Contact) obj;
	if (email == null) {
	    if (other.email != null)
		return false;
	} else if (!email.equals(other.email))
	    return false;
	return true;
    }



    public String getFone() {
	return fone;
    }

    public void setFone(String fone) {
	this.fone = fone;
    }

    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

}
