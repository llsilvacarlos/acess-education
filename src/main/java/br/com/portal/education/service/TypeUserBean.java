package br.com.portal.education.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.portal.education.dao.TypeUserDAO;
import br.com.portal.education.entity.TypeUser;

@Stateless
public class TypeUserBean {
    
    @Inject
    private TypeUserDAO  typeUserDAO;
    
    public List<TypeUser> findAll(){
	return typeUserDAO.findAll();
    }
    
    public TypeUser find(Long idtType){
	return this.typeUserDAO.find(idtType);
    }
    

}
