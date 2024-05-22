package com.example.database.controllers;

import com.example.database.domain.dto.BookDto;
import com.example.database.domain.entities.BookEntity;
import com.example.database.mappers.Mapper;
import com.example.database.services.BookService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Log
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
        boolean isExists = bookService.isExists(isbn);
        BookEntity savedBook = bookService.createUpdate(bookEntity,isbn);
        if (isExists){
            log.info("updated " + isbn);
            return new ResponseEntity<>(bookMapper.mapTo(savedBook),HttpStatus.OK);
        }
        else {
            log.info("created " + isbn);
            return new ResponseEntity<>(bookMapper.mapTo(savedBook),HttpStatus.CREATED);
        }


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
        Optional<BookEntity> foundBook = bookService.findOne(isbn);
        return foundBook.map(
                bookEntity -> {
                    BookDto bookDto = bookMapper.mapTo(bookEntity);
                    return new ResponseEntity<>(bookDto,HttpStatus.OK);
                }).orElse(
               new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
    @GetMapping(path = "/books/author/{author_id}")
    public List<BookDto> getBooksByAuthor(@PathVariable("author_id") Long id){
        List<BookEntity> bookEntityList = bookService.findByAuthorId(id);
        return bookEntityList.stream()
                .map(bookMapper::mapTo)
                .collect(Collectors.toList());
    }

    @PatchMapping(path = "books/{isbn}")
    public ResponseEntity<BookDto> updateBook(@RequestBody BookDto bookDto, @PathVariable("isbn") String isbn){
        if(!bookService.isExists(isbn)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity patchedBook = bookService.partialUpdate(isbn,bookEntity);
        return new ResponseEntity<>(bookMapper.mapTo(patchedBook),HttpStatus.OK);
    }

    @DeleteMapping(path = "books/{isbn}")
    public ResponseEntity deleteBook(@PathVariable("isbn") String isbn){

        bookService.delete(isbn);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }





}
