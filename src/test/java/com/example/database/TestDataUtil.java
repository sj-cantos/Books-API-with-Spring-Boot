package com.example.database;

import com.example.database.domain.Author;
import com.example.database.domain.Book;
import org.springframework.test.annotation.TestAnnotationUtils;

public final class TestDataUtil {
    private TestDataUtil(){

    }

    public static Author createTestAuthor() {
        return Author.builder().
                age(21).
                id(1L).
                name("Shannon John").
                build();
    }

    public static Book createBook() {
        return Book.builder()
                .isbn("QWEQ-123421-3432")
                .title("The Little Prince")
                .authorId(1L)
                .build();
    }
}
