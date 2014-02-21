package br.com.portal.education.dao;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.portal.education.entity.SampleExam;
import br.com.portal.education.entity.User;
import br.com.portal.education.exception.PortalEducationApplicationException;
import br.com.portal.education.util.MessageUtil;

@Stateless
public class UserDAO extends BaseDao<User, Long> {

    @Inject
    private MessageUtil messageUtil;

    public User findUserByLoginAndPassword(String login, String password) {
	StringBuilder sql = new StringBuilder();
	sql.append("SELECT u From User u where u.login= :login and u.password =:password ");
	EntityManager entityManager = getEntityManager();
	Query query = entityManager.createQuery(sql.toString());
	query.setParameter("login", login);
	query.setParameter("password", password);
	try {
	    return (User) query.getSingleResult();
	} catch (NoResultException e) {
	    return null;
	}
    }

    public List<SampleExam> findAllIscriptionSampleExam(User user) {
	StringBuilder sql = new StringBuilder();
	sql.append("SELECT u.sampleExams From User u join fetch u.sampleExams  where u.login= :login ");

	EntityManager entityManager = getEntityManager();
	Query query = entityManager.createQuery(sql.toString());
	query.setParameter("login", user.getLogin());
	return query.getResultList();
    }

    @Override
    public void insert(User t) throws PortalEducationApplicationException {
	if (findUserByLogin(t.getLogin()) == null) {
	    //t.setDateCreation(Calendar.getInstance());
	    super.insert(t);
	} else {
	    throw new PortalEducationApplicationException(messageUtil.getMessage("msg_erro_login_unique"));
	}

    }

    @Override
    public User update(User t) throws PortalEducationApplicationException {

	StringBuilder sql = new StringBuilder();
	sql.append("SELECT u From User u where u.login= :login and  u.id !=:id ");
	EntityManager entityManager = getEntityManager();
	Query query = entityManager.createQuery(sql.toString());
	query.setParameter("login", t.getLogin());
	query.setParameter("id", t.getId());
	try {
	    query.getSingleResult();
	    throw new PortalEducationApplicationException(messageUtil.getMessage("msg_erro_login_unique"));
	} catch (NoResultException e) {
	    return super.update(t);
	}

    }

    public User findUserByLogin(String login) {
	StringBuilder sql = new StringBuilder();
	sql.append("SELECT u From User u where u.login= :login");
	EntityManager entityManager = getEntityManager();
	Query query = entityManager.createQuery(sql.toString());
	query.setParameter("login", login);
	try {
	    return (User) query.getSingleResult();
	} catch (NoResultException e) {
	    return null;
	}
    }

}
