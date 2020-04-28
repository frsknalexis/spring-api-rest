package com.dev.app.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.app.api.assembler.BookModelAssembler;
import com.dev.app.api.converter.DozerConverter;
import com.dev.app.api.dto.BookVO;
import com.dev.app.api.model.Book;
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
	
	@Autowired
	private PagedResourcesAssembler<Book> pagedResourceAssembler;
	
	@Autowired
	private BookModelAssembler bookModelAssembler;
	
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
	
	@RequestMapping(value = "/paginate", method = RequestMethod.GET,
					produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml" })
	public ResponseEntity<PagedModel<BookVO>> findBooksPaginate(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "5") int limit, @RequestParam(value = "direction", defaultValue = "asc") String direction) {
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "author"));
		
		Page<BookVO> booksVO = bookService.findAll(pageable);
		
		Page<Book> books = booksVO.map((b) -> {
			return DozerConverter.parseObject(b, Book.class);
		});
		
		PagedModel<BookVO> pagedModel = pagedResourceAssembler.toModel(books, bookModelAssembler);
		return new ResponseEntity<>(pagedModel, HttpStatus.OK);
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