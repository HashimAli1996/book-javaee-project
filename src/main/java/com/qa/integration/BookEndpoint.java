package com.qa.integration;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.qa.service.business.BookService;

@Path("/book")
public class BookEndpoint {

	@Inject
	private BookService service;

	@Path("/json")
	@GET
	@Produces({ "application/json" })
	public String getAllBooks() {
		return service.getAllBooks();
	}

	@Path("/json")
	@POST
	@Produces({ "application/json" })
	public String addBook(String Book) {
		return service.addBook(Book);
	}

	@Path("/json/{id}")
	@PUT
	@Produces({ "application/json" })
	public String updateBook(@PathParam("id") Long id, String book) {
		return service.updateBook(id, book);
	}

	@Path("/json/{id}")
	@DELETE
	@Produces({ "application/json" })
	public String deleteBook(@PathParam("id") Long id) {
		return service.deleteBook(id);

	}

	public void setService(BookService service) {
		this.service = service;
	}

}
