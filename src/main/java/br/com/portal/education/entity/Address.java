/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.portal.education.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author lcsilva
 */
@Entity
@Table(name = "address")
public class Address extends BaseEntity<Long> {

    /**
	 * 
	 */
    private static final long serialVersionUID = -7561805033300701944L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_address")
    private Long id;

    @Column(name = "street_address", nullable = false)
    private String streetAddress;

    @Column(name = "zip", nullable = false)
    private String zip;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "complement", nullable = true)
    private String complement;

    public String getComplement() {
	return complement;
    }

    public void setComplement(String complement) {
	this.complement = complement;
    }

    @Column(name = "neighborhood", nullable = false)
    private String neighborhood;

    public String getZip() {
	return zip;
    }

    public void setZip(String zip) {
	this.zip = zip;
    }

    public String getNumber() {
	return number;
    }

    public void setNumber(String number) {
	this.number = number;
    }

    public String getNeighborhood() {
	return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
	this.neighborhood = neighborhood;
    }

    public String getStreetAddress() {
	return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
	this.streetAddress = streetAddress;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

}
