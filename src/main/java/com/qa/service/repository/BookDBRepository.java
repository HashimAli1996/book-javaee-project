package com.qa.service.repository;

import static javax.transaction.Transactional.TxType.SUPPORTS;

import java.util.Collection;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.qa.domain.Book;
import com.qa.util.JSONUtil;

@Transactional(SUPPORTS)
@Default
public class BookDBRepository implements BookRepository{
	
	@PersistenceContext(unitName = "primary")
	private EntityManager manager;

	@Inject
	private JSONUtil util;
	
	public String getAllBooks() {
		Query query = manager.createQuery("Select a FROM Book a");
		Collection<Book> books = (Collection<Book>) query.getResultList();
		return util.getJSONForObject(books);
	}

	public String createBook(String book) {
		Book aBook = util.getObjectForJSON(book, Book.class);
		manager.persist(aBook);
		return "{\"message\": \"Book has been sucessfully added\"}";
	}

	public String updateBook(Long id, String BookToUpdate) {
		Book updatedBook = util.getObjectForJSON(BookToUpdate, Book.class);
		updatedBook.setId(id);
		if (BookToUpdate != null) {
			manager.merge(updatedBook);
		}
		return "{\"message\": \"account sucessfully updated\"}";
	}

	public String deleteBook(Long id) {
		Book bookInDB = findBook(id);
		if (bookInDB != null) {
			manager.remove(bookInDB);
		}
		return "{\"message\": \"account sucessfully deleted\"}";
	}
	
	private Book findBook(Long id) {
		return manager.find(Book.class, id);
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

	public void setUtil(JSONUtil util) {
		this.util = util;
	}

}
