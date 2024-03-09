package com.example.database.dao;

import com.example.database.TestDataUtil;
import com.example.database.dao.impl.AuthorDaoImpl;
import com.example.database.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AuthorDaoImplIntegrationTests {

    private AuthorDaoImpl underTest;

    public AuthorDaoImplIntegrationTests(AuthorDaoImpl underTest){
        this.underTest = underTest;
    }
    @Test
    public void TestThatAuthorIsCreated(){
        Author author = TestDataUtil.createTestAuthor();
    }


}
