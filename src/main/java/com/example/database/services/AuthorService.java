package com.example.database.services;

import com.example.database.domain.entities.AuthorEntity;

import java.util.List;


public interface AuthorService {
    AuthorEntity save(AuthorEntity authorEntity);
    List<AuthorEntity> findAll();
}
