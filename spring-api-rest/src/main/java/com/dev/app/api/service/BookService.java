package com.dev.app.api.service;

import java.util.List;

import com.dev.app.api.dto.BookVO;

public interface BookService {

	public BookVO create(BookVO book);
	
	public List<BookVO> findAll();
	
	BookVO findById(Long id);
	
	BookVO update(BookVO book);
	
	void delete(Long id);
}
