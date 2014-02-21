package br.com.portal.education.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "type_user")
public class TypeUser extends BaseEntity<Long> {

    /**
	 * 
	 */
    private static final long serialVersionUID = 6520593491366571616L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_type_user")
    private Long id;

    @Column(name = "type_user", nullable = false)
    private String typeUser;

    public String getTypeUser() {
	return typeUser;
    }

    public void setTypeUser(String typeUser) {
	this.typeUser = typeUser;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
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
	TypeUser other = (TypeUser) obj;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	return true;
    }

}
