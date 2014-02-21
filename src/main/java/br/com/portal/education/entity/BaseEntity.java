package br.com.portal.education.entity;

import java.io.Serializable;

public abstract class BaseEntity<ID> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4602581650080366898L;
	

	public abstract ID getId();
	public abstract void setId(ID id);
}
