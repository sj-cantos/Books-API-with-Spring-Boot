package com.example.database.dao;

import com.example.database.TestDataUtil;
import com.example.database.dao.impl.AuthorDaoImpl;
import com.example.database.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.any;
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
        Author author = TestDataUtil.createTestAuthor1();

        underTest.create(author);

        verify(jdbcTemplate).update(eq("INSERT INTO authors(id,name,age) VALUES(?,?,?)"),
                eq(1L),
                eq("Shannon John"),
                eq(21));
    }

    @Test
    public void testThatFindOneGenerateCorrectSQL(){
        underTest.findOne(1L);
        verify(jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors WHERE id = ? LIMIT 1"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any(),
                eq(1L));

    }
    @Test
    public void testThatFindManyGenerateCorrectSQL(){
        underTest.findMany();
        verify(jdbcTemplate).query(eq("SELECT id, name, age FROM authors"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any());
    }

}
