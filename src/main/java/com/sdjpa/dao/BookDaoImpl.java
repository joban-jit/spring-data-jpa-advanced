package com.sdjpa.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.sdjpa.domain.Book;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

@Component
public class BookDaoImpl implements BookDao {
	
	private EntityManagerFactory emf;
	
	public BookDaoImpl(EntityManagerFactory emf) {
		this.emf= emf;
	}
	
	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	

	@Override
	public Book getById(Long id) {
		return getEntityManager().find(Book.class, id);
	}

	@Override
	public Book findBookByTitle(String title) {
		TypedQuery<Book> query = getEntityManager().createQuery(
				"SELECT b FROM Book b WHERE b.title=:title", 
				Book.class
				);
		query.setParameter("title", title);
		return query.getSingleResult();
	}
	
	@Override
	public Book findBookByTitleUsingNativeQuery(String title) {
		Query query = getEntityManager().createNativeQuery(
				"SELECT * FROM Book b WHERE b.title=?", 
				Book.class
				);
		query.setParameter(1, title);
		return (Book) query.getSingleResult();
	}

	@Override
	public Book saveNewBook(Book book) {
		EntityManager eManager = getEntityManager();
		eManager.getTransaction().begin();
		eManager.persist(book);
		eManager.flush();
		eManager.getTransaction().commit();
		eManager.close();
		
		return book;
	}

	@Override
	public Book updateBook(Book book) {
		EntityManager eManager = getEntityManager();
		eManager.getTransaction().begin();
		eManager.merge(book);
		eManager.flush();
		eManager.getTransaction().commit();
		eManager.close();
		return book;
		
	}

	@Override
	public void deleteBookById(Long id) {
		EntityManager eManager = getEntityManager();
		eManager.getTransaction().begin();
		Book book = eManager.find(Book.class, id);
		eManager.remove(book);
		eManager.flush();
		eManager.getTransaction().commit();
		eManager.close();
		
		
	}
	
	@Override
	public Book findByISBN(String isbn) {
		EntityManager eManager = getEntityManager();
		try {
			TypedQuery<Book> query = getEntityManager().createQuery(
					"SELECT b FROM Book b WHERE b.isbn=:isbn", 
					Book.class
					);
			query.setParameter("isbn", isbn);
			return query.getSingleResult();
		} finally {

			eManager.close();
		}
	}

	@Override
	public List<Book> findAll() {
		TypedQuery<Book> query = getEntityManager().createNamedQuery(
				"find_all_books", 
				Book.class
				);
		return query.getResultList();
	}

	@Override
	public Book findBookByTitleUsingNamedQuery(String title) {
		TypedQuery<Book> query = getEntityManager().createNamedQuery(
				"find_book_by_title", 
				Book.class
				);
		query.setParameter("title", title);
		return query.getSingleResult();
	}
	
	@Override
	public List<Book> findAllBooks(int offset, int pageSize) {
		EntityManager em = getEntityManager();
		try {
			TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
			query.setFirstResult(offset);
			query.setMaxResults(pageSize);
			return query.getResultList();
		} finally {
			em.close();
		}
	}
	
	@Override
	public List<Book> findAllBooks(Pageable pageable) {
		EntityManager em = getEntityManager();
		try {
			TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
			query.setFirstResult(Math.toIntExact(pageable.getOffset()));
			query.setMaxResults(pageable.getPageSize());
			return query.getResultList();
		} finally {
			em.close();
		}
	}
	
	@Override
	public List<Book> findAllBooksSortByTitle(Pageable pageable) {
		EntityManager em = getEntityManager();
		try {
			String hql = "select b from Book b order by b.title "
		+pageable.getSort().getOrderFor("title").getDirection().name();
			TypedQuery<Book> query = em.createQuery(hql, Book.class);
			query.setFirstResult(Math.toIntExact(pageable.getOffset()));
			query.setMaxResults(pageable.getPageSize());
			return query.getResultList();
		} finally {
			em.close();
		}
	}



}
