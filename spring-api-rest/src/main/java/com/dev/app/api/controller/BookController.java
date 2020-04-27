package com.dev.app.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dev.app.api.dto.BookVO;
import com.dev.app.api.service.BookService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Book Endpoint", description = "Description for Book", tags = { "Book Endpoint V1" })
@RestController
@RequestMapping("/api/book/v1")
public class BookController {

	@Autowired
	@Qualifier("bookService")
	private BookService bookService;
	
	@ApiOperation(value = "Find All Books Recorded")
	@RequestMapping(value = "/all", method = RequestMethod.GET, 
					produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml" })
	public List<BookVO> findAll() {
		List<BookVO> booksVO = bookService.findAll();
		booksVO.stream()
				.forEach((b) -> {
					b.add(linkTo(methodOn(BookController.class).findById(b.getKey())).withSelfRel());
				});
		return booksVO;
	}
	
	@ApiOperation(value = "Find A Specific Book by your ID")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET,
					produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml" })
	public BookVO findById(@PathVariable(value = "id") Long id) {
		BookVO bookVO = bookService.findById(id);
		bookVO.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		bookVO.add(linkTo(methodOn(BookController.class).findAll()).withRel("books"));
		return bookVO;
	}
	
	@ApiOperation(value = "Create a new Book")
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml" },
					produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml" })
	public BookVO createBook(@RequestBody BookVO book) {
		BookVO bookVO = bookService.create(book);
		bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel());
		bookVO.add(linkTo(methodOn(BookController.class).findAll()).withRel("books"));
		return bookVO;
	}
	
	@ApiOperation(value = "Update A Specific Book")
	@RequestMapping(method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml" },
					produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml" })
	public BookVO updateBook(@RequestBody BookVO book) {
		BookVO bookVO = bookService.update(book);
		bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel());
		bookVO.add(linkTo(methodOn(BookController.class).findAll()).withRel("books"));
		return bookVO;
	}
	
	@ApiOperation(value = "Delete A Specific Book by your ID")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteBook(@PathVariable(value = "id") Long id) {
		bookService.delete(id);
		return ResponseEntity.ok().build();
	}
}