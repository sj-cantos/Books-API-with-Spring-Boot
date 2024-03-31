package com.example.database.services;

import com.example.database.domain.dto.BookDto;
import com.example.database.domain.entities.BookEntity;

public interface BookService
{
    BookEntity save(BookEntity bookEntity, String isbn);
}
