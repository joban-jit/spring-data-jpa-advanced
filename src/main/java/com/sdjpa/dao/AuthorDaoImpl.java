package com.sdjpa.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.sdjpa.domain.Author;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Component
public class AuthorDaoImpl implements AuthorDao {
	
	private final EntityManagerFactory emf;
	
	public AuthorDaoImpl(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public Author getById(Long id) {
		return getEntityManager().find(Author.class, id);
	}

	@Override
	public Author findAuthorByName(String firstName, String lastName) {
		TypedQuery<Author> query = getEntityManager()
				.createQuery(
						"SELECT a FROM Author a WHERE a.firstName = :first_name and a.lastName = :last_name" 
						,Author.class
						);
		query.setParameter("first_name", firstName);
		query.setParameter("last_name", lastName);
		return query.getSingleResult();

	}
	
	@Override
	public Author findAuthorByNameUsingNamedQuery(String firstName, String lastName) {
		TypedQuery<Author> query = getEntityManager().createNamedQuery("find_by_name", Author.class);
		query.setParameter("first_name", firstName);
		query.setParameter("last_name", lastName);
		return query.getSingleResult();
		
	}
	@Override
	public Author findAuthorByNameUsingCriteria(String firstName, String lastName) {
		EntityManager em = getEntityManager();
		try {
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<Author> criteriaQuery = criteriaBuilder.createQuery(Author.class);
			Root<Author> root = criteriaQuery.from(Author.class);
			ParameterExpression<String> firstNameParam = criteriaBuilder.parameter(String.class);
			ParameterExpression<String> lastNameParam = criteriaBuilder.parameter(String.class);
			Predicate firstNamePred = criteriaBuilder.equal(root.get("firstName"), firstNameParam);
			Predicate lastNamePred = criteriaBuilder.equal(root.get("lastName"), lastNameParam);
			criteriaQuery.select(root).where(criteriaBuilder.and(firstNamePred, lastNamePred));
			TypedQuery<Author> typedQuery = em.createQuery(criteriaQuery);
			typedQuery.setParameter(firstNameParam, firstName);
			typedQuery.setParameter(lastNameParam, lastName);
			return typedQuery.getSingleResult();
			
		} finally {
			em.close();
		}
	}
	

	@Override
	public Author saveNewAuthor(Author author) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		em.persist(author);
		em.flush();
		em.getTransaction().commit();
		em.close();
		return author;
	}

	@Override
	public Author updateAuthor(Author author) {
		EntityManager eManager = getEntityManager();
		eManager.getTransaction().begin();
		eManager.merge(author);
		eManager.flush();
		eManager.getTransaction().commit();
		eManager.close();
		return author;
	}

	@Override
	public void deleteAuthorById(Long id) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		Author author = em.find(Author.class, id);
		em.remove(author);
		em.flush();
		em.getTransaction().commit();
		em.close();

	}

	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Author> listAuthorByLastNameLike(String lastName) {
		EntityManager em = getEntityManager();
		try {
			Query query = em.createQuery("SELECT a FROM Author a WHERE a.lastName LIKE :last_name");
			query.setParameter("last_name", lastName+"%");
			return query.getResultList();
		} finally {
			em.close();
		}
	}
	
	@Override
	public List<Author> findAll() {
		EntityManager em = getEntityManager();
		try {
			TypedQuery<Author> query = em.createNamedQuery("author_find_all", Author.class);
			return query.getResultList();
		} finally {
			em.close();
		}
	}
	@Override
	public Author findAuthorByNameUsingNativeQuery(String firstName, String lastName) {
		EntityManager em = getEntityManager();
		try {
			Query query = em.createNativeQuery("select * from author where first_name=? and last_name=?", Author.class);
			query.setParameter(1, firstName);
			query.setParameter(2, lastName);
			return (Author) query.getSingleResult();
		} finally {
			em.close();
		}
	}
}
