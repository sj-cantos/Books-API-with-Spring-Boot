package com.example.database.dao.impl;

import com.example.database.dao.BookDao;
import com.example.database.domain.Book;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class BookDaoImpl implements BookDao {

    private final JdbcTemplate jdbcTemplate;

    public BookDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Book book) {
        jdbcTemplate.update("INSERT INTO books(isbn,title,author_id) VALUES(?,?,?)",
                book.getIsbn(),
                book.getTitle(),
                book.getAuthorId());
    }

    @Override
    public Optional<Book> findOne(String isbn){
        List<Book> books = jdbcTemplate.query("SELECT author_id,isbn,title FROM books WHERE isbn = ? LIMIT 1",
                new BookRowMapper(), isbn);
        return books.stream().findFirst();
    }

    @Override
    public List<Book> findMany() {
        return jdbcTemplate.query("SELECT author_id, title, isbn FROM books", new BookRowMapper());
    }

    @Override
    public void update(Book book, String isbn) {
        jdbcTemplate.update("UPDATE books SET author_id = ?, title = ? WHERE isbn = ?",
                                book.getAuthorId(),
                                book.getTitle(),
                                isbn);
    }

    public static class BookRowMapper implements RowMapper {
        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Book.builder()
                    .authorId(rs.getLong("author_id"))
                    .title(rs.getString("title"))
                    .isbn(rs.getString("isbn"))
                    .build();
        }
    }


}
