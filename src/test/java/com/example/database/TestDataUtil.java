package com.example.database;

import com.example.database.domain.Author;
import com.example.database.domain.Book;

public final class TestDataUtil {
    private TestDataUtil(){

    }

    public static Author createTestAuthor1() {
        return Author.builder().
                age(70).
                id(1L).
                name("Shannon John").
                build();
    }
    public static Author createTestAuthor2() {
        return Author.builder().
                age(21).
                id(2L).
                name("Mark Josh").
                build();
    }
    public static Author createTestAuthor3() {
        return Author.builder().
                age(22).
                id(3L).
                name("Juan Maria").
                build();
    }

    public static Book createBookA(final Author author) {
        return Book.builder()
                .isbn("QWEQ-123421-3432")
                .title("The Little Prince")
                .author(author)
                .build();
    }
    public static Book createBookB(final Author author) {
        return Book.builder()
                .isbn("QWEQ-123421-3433")
                .title("Noli Me Tangere")
                .author(author)
                .build();
    }
    public static Book createBookC( final Author author) {
        return Book.builder()
                .isbn("QWEQ-123421-3434")
                .title("El FIlibusterismo")
                .author(author)
                .build();
    }
}
