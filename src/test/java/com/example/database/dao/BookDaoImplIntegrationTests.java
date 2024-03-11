package com.example.database.dao;

import com.example.database.TestDataUtil;
import com.example.database.dao.impl.BookDaoImpl;
import com.example.database.domain.Author;
import com.example.database.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static  org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BookDaoImplIntegrationTests {
    private final BookDaoImpl underTest;
    private final AuthorDao authorDao;

    @Autowired
    public BookDaoImplIntegrationTests(BookDaoImpl underTest, AuthorDao authorDao){
        this.underTest = underTest;
        this.authorDao = authorDao;
    }

    @Test
    public void TestThatBookIsCreated(){

        Author author = TestDataUtil.createTestAuthor1();
        authorDao.create(author);
        Book book = TestDataUtil.createBook();
        book.setAuthorId(author.getId());
        underTest.create(book);
        Optional<Book> result = underTest.findOne(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void TestThatBooksAreFetchedAfterCreation(){
        Book book = TestDataUtil.createBook();
        Author author = TestDataUtil.createTestAuthor1();
        authorDao.create(author);
        book.setAuthorId(author.getId());
        underTest.create(book);
        List<Book> result = underTest.findMany();
        assertThat(result).hasSize(1).containsExactly(book);

    }

}
