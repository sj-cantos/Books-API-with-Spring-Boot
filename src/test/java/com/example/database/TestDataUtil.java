package com.example.database;

import com.example.database.domain.entities.AuthorEntity;
import com.example.database.domain.entities.BookEntity;

public final class TestDataUtil {
    private TestDataUtil(){

    }

    public static AuthorEntity createTestAuthor1() {
        return AuthorEntity.builder().
                age(70).
                id(1L).
                name("Shannon John").
                build();
    }
    public static AuthorEntity createTestAuthor2() {
        return AuthorEntity.builder().
                age(21).
                id(2L).
                name("Mark Josh").
                build();
    }
    public static AuthorEntity createTestAuthor3() {
        return AuthorEntity.builder().
                age(22).
                id(3L).
                name("Juan Maria").
                build();
    }

    public static BookEntity createBookA(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("QWEQ-123421-3432")
                .title("The Little Prince")
                .authorEntity(authorEntity)
                .build();
    }
    public static BookEntity createBookB(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("QWEQ-123421-3433")
                .title("Noli Me Tangere")
                .authorEntity(authorEntity)
                .build();
    }
    public static BookEntity createBookC(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("QWEQ-123421-3434")
                .title("El FIlibusterismo")
                .authorEntity(authorEntity)
                .build();
    }
}
