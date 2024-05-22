package com.example.database.services.impl;

import com.example.database.domain.dto.BookDto;
import com.example.database.domain.entities.AuthorEntity;
import com.example.database.domain.entities.BookEntity;
import com.example.database.repositories.BookRepository;
import com.example.database.services.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createUpdate(BookEntity bookEntity, String isbn) {
        bookEntity.setIsbn(isbn);
        return bookRepository.save(bookEntity);
    }

    @Override
    public List<BookEntity> findAll() {
        return StreamSupport.stream(bookRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }

    @Override
    public Optional<BookEntity> findOne(String isbn) {
        Optional<BookEntity> foundBook = bookRepository.findById(isbn);
        return foundBook;
    }

    @Override
    public List<BookEntity> findByAuthorId(Long id) {
        return bookRepository.findByAuthorEntityId(id);
    }

    @Override
    public boolean isExists(String isbn) {
        return bookRepository.existsById(isbn);
    }

    @Override
    public BookEntity partialUpdate(String isbn, BookEntity bookEntity) {
            bookEntity.setIsbn(isbn);
            return bookRepository.findById(isbn).map( existingBook -> {
                Optional.ofNullable(bookEntity.getTitle()).ifPresent(existingBook::setTitle);
                Optional.ofNullable(bookEntity.getAuthorEntity()).ifPresent(existingBook::setAuthorEntity);
                return bookRepository.save(existingBook);
            }).orElseThrow(() -> new RuntimeException("Book not found."));
    }
}
