package com.example.database.services;

import com.example.database.domain.entities.AuthorEntity;


public interface AuthorService {
    AuthorEntity save(AuthorEntity authorEntity);
}
