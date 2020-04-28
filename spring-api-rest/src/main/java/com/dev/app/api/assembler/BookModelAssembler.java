package com.dev.app.api.assembler;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;

import com.dev.app.api.controller.BookController;
import com.dev.app.api.converter.DozerConverter;
import com.dev.app.api.dto.BookVO;
import com.dev.app.api.model.Book;

@Component
public class BookModelAssembler extends RepresentationModelAssemblerSupport<Book, BookVO> {

	
	public BookModelAssembler() {
		super(BookController.class, BookVO.class);
	}

	@Override
	public BookVO toModel(Book entity) {
		BookVO bookVO = DozerConverter.parseObject(entity, BookVO.class);
		bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel());
		return bookVO;
	}

	@Override
	public CollectionModel<BookVO> toCollectionModel(Iterable<? extends Book> entities) {
		CollectionModel<BookVO> bookModel = super.toCollectionModel(entities);
		bookModel.add(linkTo(methodOn(BookController.class).findAll()).withRel("books"));
		bookModel.forEach((b) -> {
			b.add(linkTo(methodOn(BookController.class).findById(b.getKey())).withSelfRel());
		});
		return bookModel;
	}
}
