package com.example.database.repositories;


import com.example.database.domain.entities.AuthorEntity;
import com.example.database.domain.entities.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, String> {
    List<BookEntity> findByAuthorEntityId(Long authorId);
}
