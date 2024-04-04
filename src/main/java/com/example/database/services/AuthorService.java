package com.example.database.services;

import com.example.database.domain.entities.AuthorEntity;

import java.util.List;
import java.util.Optional;


public interface AuthorService {
    AuthorEntity save(AuthorEntity authorEntity);
    List<AuthorEntity> findAll();
    Optional<AuthorEntity> findOne(long id);
}
