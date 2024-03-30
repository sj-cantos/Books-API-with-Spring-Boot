package com.example.database.repositories;

import com.example.database.TestDataUtil;
import com.example.database.domain.AuthorEntity;
import com.example.database.domain.BookEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static  org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookEntityRepositoryTests {
    private final BookRepository underTest;
    private final AuthorRepository authorDao;

    @Autowired
    public BookEntityRepositoryTests(BookRepository underTest, AuthorRepository authorDao){
        this.underTest = underTest;
        this.authorDao = authorDao;
    }

    @Test
    public void TestThatBookIsCreated(){

        AuthorEntity authorEntity = TestDataUtil.createTestAuthor1();
        authorDao.save(authorEntity);
        BookEntity bookEntity = TestDataUtil.createBookA(authorEntity);
        underTest.save(bookEntity);
        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntity);
    }
//
    @Test
    public void TestThatBooksAreFetchedAfterCreation(){

        AuthorEntity authorEntity = TestDataUtil.createTestAuthor1();

        BookEntity bookEntityA = TestDataUtil.createBookA(authorEntity);
        underTest.save(bookEntityA);

        BookEntity bookEntityB = TestDataUtil.createBookB(authorEntity);
        underTest.save(bookEntityB);

        BookEntity bookEntityC = TestDataUtil.createBookC(authorEntity);
        underTest.save(bookEntityC);

        Iterable<BookEntity> result = underTest.findAll();
        assertThat(result).hasSize(3).containsExactly(bookEntityA, bookEntityB, bookEntityC);

    }

    @Test
    public void TestThatBooksAreUpdated(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor1();

        BookEntity bookEntity = TestDataUtil.createBookA(authorEntity);
        underTest.save(bookEntity);
        bookEntity.setTitle("Hello");
        underTest.save(bookEntity);
        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntity);
    }
//
    @Test
    public void TestThatBooksAreDeleted(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor1();

        BookEntity bookEntity = TestDataUtil.createBookA(authorEntity);
        underTest.save(bookEntity);

        underTest.deleteById(bookEntity.getIsbn());

        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());
        assertThat(result).isEmpty();

    }

}
