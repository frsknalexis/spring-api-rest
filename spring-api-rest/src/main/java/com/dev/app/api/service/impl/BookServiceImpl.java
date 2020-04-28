package com.dev.app.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dev.app.api.controller.exception.ResourceNotFoundException;
import com.dev.app.api.converter.DozerConverter;
import com.dev.app.api.dto.BookVO;
import com.dev.app.api.model.Book;
import com.dev.app.api.repository.BookRepository;
import com.dev.app.api.service.BookService;

@Service("bookService")
public class BookServiceImpl implements BookService {

	@Autowired
	@Qualifier("bookRepository")
	private BookRepository bookRepository;
	
	@Override
	public BookVO create(BookVO book) {
		Book entity = DozerConverter.parseObject(book, Book.class);
		Book entityBookResponse = bookRepository.save(entity);
		return DozerConverter.parseObject(entityBookResponse, BookVO.class);
	}

	@Override
	public List<BookVO> findAll() {
		List<Book> entityBookList = bookRepository.findAll();
		return DozerConverter.parseListObject(entityBookList, BookVO.class);
	}
	
	@Override
	public Page<BookVO> findAll(Pageable pageable) {
		Page<Book> entityList = bookRepository.findAll(pageable);
		return entityList.map((b) -> DozerConverter.parseObject(b, BookVO.class));
	}

	@Override
	public BookVO findById(Long id) {
		Book entity = bookRepository.findById(id)
							.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		return DozerConverter.parseObject(entity, BookVO.class);
	}

	@Override
	public BookVO update(BookVO book) {
		Book entity = bookRepository.findById(book.getKey())
							.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		entity.setAuthor(book.getAuthor());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		entity.setTitle(book.getTitle());
		Book entityBookResponse = bookRepository.save(entity);
		return DozerConverter.parseObject(entityBookResponse, BookVO.class);
	}

	@Override
	public void delete(Long id) {
		Book entity = bookRepository.findById(id)
							.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		bookRepository.delete(entity);
	}
}