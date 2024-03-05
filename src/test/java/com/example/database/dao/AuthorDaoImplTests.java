package com.example.database.dao;

import com.example.database.dao.impl.AuthorDaoImpl;
import com.example.database.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthorDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuthorDaoImpl underTest;

    @Test
    public void testThatCreateAuthorHasCorrectSQL(){
        Author author = Author.builder().
                age(21).
                id(1L).
                name("Shannon John").
                build();

        underTest.create(author);

        verify(jdbcTemplate).update(eq("INSERT INTO authors(id,name,age) VALUES(?,?,?)"),
                eq(1L),
                eq("Shannon John"),
                eq(21));
    }

}
