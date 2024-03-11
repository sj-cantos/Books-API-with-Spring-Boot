package com.example.database.dao;

import com.example.database.TestDataUtil;
import com.example.database.dao.impl.AuthorDaoImpl;
import com.example.database.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static  org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AuthorDaoImplIntegrationTests {

    private AuthorDaoImpl underTest;

    @Autowired
    public AuthorDaoImplIntegrationTests(AuthorDaoImpl underTest){
        this.underTest = underTest;
    }
    @Test
    public void TestThatAuthorIsCreated(){

        Author author = TestDataUtil.createTestAuthor1();
        underTest.create(author);
        Optional<Author> result = underTest.findOne(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);

    }

    @Test
    public void TestThatAuthorIsFetchedAfterCreation(){
        Author author1 = TestDataUtil.createTestAuthor1();
        underTest.create(author1);
        Author author2 = TestDataUtil.createTestAuthor2();
        underTest.create(author2);
        Author author3 = TestDataUtil.createTestAuthor3();
        underTest.create(author3);

        List<Author> results = underTest.findMany();
        assertThat(results).hasSize(3).
                            containsExactly(author1,author2,author3);



    }


}
