package br.com.portal.education.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.portal.education.entity.SampleExam;
import br.com.portal.education.entity.SampleExamStudentResult;
import br.com.portal.education.entity.User;

@Stateless
public class SampleExamStudentResultDAO extends BaseDao<SampleExamStudentResult, Long> {

    public SampleExamStudentResult findByUser(User user, SampleExam sampleExam) {
	StringBuilder sqlQuery = new StringBuilder("Select s from SampleExamStudentResult s where s.user =:user and s.sampleExam =:sample ");
	EntityManager entityManager = getEntityManager();
	Query query = entityManager.createQuery(sqlQuery.toString());
	query.setParameter("user", user);
	query.setParameter("sample", sampleExam);
	try {
	    return (SampleExamStudentResult) query.getSingleResult();
	} catch (NoResultException e) {
	    return null;
	}
    }

}
