package com.dev.app.api.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dev.app.api.dto.BookVO;

public interface BookService {

	public BookVO create(BookVO book);
	
	public List<BookVO> findAll();
	
	Page<BookVO> findAll(Pageable pageable);
	
	BookVO findById(Long id);
	
	BookVO update(BookVO book);
	
	void delete(Long id);
}
