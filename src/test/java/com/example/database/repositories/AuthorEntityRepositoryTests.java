package com.example.database.repositories;

import com.example.database.TestDataUtil;
import com.example.database.domain.entities.AuthorEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static  org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorEntityRepositoryTests {

    private AuthorRepository underTest;

    @Autowired
    public AuthorEntityRepositoryTests(AuthorRepository underTest){
        this.underTest = underTest;
    }
    @Test
    public void TestThatAuthorIsCreated(){

        AuthorEntity authorEntity = TestDataUtil.createTestAuthor1();
        underTest.save(authorEntity);
        Optional<AuthorEntity> result = underTest.findById(authorEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorEntity);

    }

    @Test
    public void TestThatAuthorIsFetchedAfterCreation(){
        AuthorEntity authorEntity1 = TestDataUtil.createTestAuthor1();
        underTest.save(authorEntity1);
        AuthorEntity authorEntity2 = TestDataUtil.createTestAuthor2();
        underTest.save(authorEntity2);
        AuthorEntity authorEntity3 = TestDataUtil.createTestAuthor3();
        underTest.save(authorEntity3);

        Iterable<AuthorEntity> results = underTest.findAll();
        assertThat(results).hasSize(3).
                            containsExactly(authorEntity1, authorEntity2, authorEntity3);

    }

    @Test
    public void TestThatAuthorIsUpdated(){
        AuthorEntity authorEntity1 = TestDataUtil.createTestAuthor1();
        underTest.save(authorEntity1);
        authorEntity1.setName("Updated");
        underTest.save(authorEntity1);
        Optional<AuthorEntity> result = underTest.findById(authorEntity1.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorEntity1);
    }
    @Test
    public void TestThatAuthorIsDeleted(){
        AuthorEntity authorEntity1 = TestDataUtil.createTestAuthor1();
        underTest.save(authorEntity1);
        underTest.delete(authorEntity1);
        Optional<AuthorEntity> result = underTest.findById(authorEntity1.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void TestThatAgeLessThanIsFetched(){
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthor1();
        underTest.save(authorEntityA);
        AuthorEntity authorEntityB = TestDataUtil.createTestAuthor2();
        underTest.save(authorEntityB);
        AuthorEntity authorEntityC = TestDataUtil.createTestAuthor3();
        underTest.save(authorEntityC);

        Iterable<AuthorEntity> results = underTest.ageLessThan(50);
        assertThat(results).containsExactly(authorEntityB, authorEntityC);
    }

    @Test
    public void TestAuthorAgeIsGreaterThan(){
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthor1();
        underTest.save(authorEntityA);
        AuthorEntity authorEntityB = TestDataUtil.createTestAuthor2();
        underTest.save(authorEntityB);
        AuthorEntity authorEntityC = TestDataUtil.createTestAuthor3();
        underTest.save(authorEntityC);

        Iterable<AuthorEntity> results = underTest.ageIsGreaterThan(50);
        assertThat(results).containsExactly(authorEntityA);
    }


}
