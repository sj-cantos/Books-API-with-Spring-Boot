package com.example.database.controllers;

import com.example.database.domain.dto.AuthorDto;
import com.example.database.domain.dto.BookDto;
import com.example.database.domain.entities.AuthorEntity;
import com.example.database.domain.entities.BookEntity;
import com.example.database.mappers.Mapper;
import com.example.database.mappers.impl.BookMapperImpl;
import com.example.database.services.BookService;
import com.example.database.services.impl.BookServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {
    private BookService bookService;
    private Mapper<BookEntity, BookDto> bookMapper;

    public BookController(BookService bookService,Mapper<BookEntity, BookDto> bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> createBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto){
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedBookEntity = bookService.save(bookEntity,isbn);
        return new ResponseEntity<>(bookMapper.mapTo(savedBookEntity),HttpStatus.CREATED);
    }

    @GetMapping(path = "/books")
    public List<BookDto> findAll(){
        List<BookEntity> bookEntityList = bookService.findAll();
        return bookEntityList.stream()
                .map(bookMapper::mapTo)
                .collect(Collectors.toList());
    }
    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> getBooks(@PathVariable("isbn") String isbn){
        BookEntity bookEntity = bookService.findOne(isbn);
        if(bookEntity == null){
            return ResponseEntity.notFound().build();
        }
       return ResponseEntity.ok(bookMapper.mapTo(bookEntity));
    }

    @GetMapping(path = "/books/author/{author_id}")
    public List<BookDto> getBooks(@PathVariable("author_id") Long id){
        List<BookEntity> bookEntityList = bookService.findByAuthorId(id);
        return bookEntityList.stream()
                .map(bookMapper::mapTo)
                .collect(Collectors.toList());
    }




}
