package br.com.portal.education.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.portal.education.dao.SampleStatusDAO;
import br.com.portal.education.entity.SampleStatus;

@Stateless
public class SampleStatusBean {

    @Inject
    private SampleStatusDAO sampleStatusDAO;

    public List<SampleStatus> findAll() {
	return sampleStatusDAO.findAll();
    }

    public SampleStatus find(Long idtSampleStatus) {
	return this.sampleStatusDAO.find(idtSampleStatus);
    }

}
