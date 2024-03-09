package com.example.database;

import com.example.database.domain.Author;
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
}
