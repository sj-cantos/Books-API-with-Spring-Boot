package com.example.database.dao;

import com.example.database.TestDataUtil;
import com.example.database.dao.impl.BookDaoImpl;
import com.example.database.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDaoImpl underTest;

    @Test
    public void testThatCreateBookGenerateCorrectSQL(){
        Book book = TestDataUtil.createBook();

        underTest.create(book);

       verify(jdbcTemplate).update(eq("INSERT INTO books(isbn,title,author_id) VALUES(?,?,?)"),
               eq("QWEQ-123421-3432"),
               eq("The Little Prince"),
               eq(1L));
    }

    @Test
    public void testThatFindOneBookGenerateCorrectSQL(){
        underTest.findOne("QWEQ-123421-3432");
        verify(jdbcTemplate).query(eq("SELECT author_id,isbn,title FROM books WHERE isbn = ? LIMIT 1"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(),
                eq("QWEQ-123421-3432"));
    }

    @Test
    public void testThatFindManyGenerateCorrectSQL(){
        underTest.findMany();
        verify(jdbcTemplate).query(eq("SELECT author_id, title, isbn FROM books"),
                                    ArgumentMatchers.<BookDaoImpl.BookRowMapper>any());
    }



}
