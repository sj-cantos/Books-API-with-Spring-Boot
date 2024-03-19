package com.example.database.dao;

import com.example.database.TestDataUtil;
import com.example.database.dao.impl.BookDaoImpl;
import com.example.database.domain.Author;
import com.example.database.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static  org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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
        Book book = TestDataUtil.createBookA();
        book.setAuthorId(author.getId());
        underTest.create(book);
        Optional<Book> result = underTest.findOne(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void TestThatBooksAreFetchedAfterCreation(){

        Author author = TestDataUtil.createTestAuthor1();
        authorDao.create(author);

        Book bookA = TestDataUtil.createBookA();
        bookA.setAuthorId(author.getId());
        underTest.create(bookA);

        Book bookB = TestDataUtil.createBookB();
        bookB.setAuthorId(author.getId());
        underTest.create(bookB);

        Book bookC = TestDataUtil.createBookC();
        bookC.setAuthorId(author.getId());
        underTest.create(bookC);

        List<Book> result = underTest.findMany();
        assertThat(result).hasSize(3).containsExactly(bookA,bookB,bookC);

    }

    @Test
    public void TestThatBooksAreUpdated(){
        Author author = TestDataUtil.createTestAuthor1();
        authorDao.create(author);

        Book book = TestDataUtil.createBookA();
        underTest.create(book);
        book.setAuthorId(author.getId());
        book.setTitle("Hello");
        underTest.update(book, book.getIsbn());
        Optional<Book> result = underTest.findOne(book.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void TestThatBooksAreDeleted(){
        Author author = TestDataUtil.createTestAuthor1();
        authorDao.create(author);

        Book book = TestDataUtil.createBookA();
        book.setAuthorId(author.getId());
        underTest.create(book);

        underTest.delete(book.getIsbn());

        Optional<Book> result = underTest.findOne(book.getIsbn());
        assertThat(result).isEmpty();

    }

}
