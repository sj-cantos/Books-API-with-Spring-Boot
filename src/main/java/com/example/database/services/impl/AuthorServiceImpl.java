package com.example.database.services.impl;

import com.example.database.domain.entities.AuthorEntity;
import com.example.database.services.AuthorService;

public class AuthorServiceImpl implements AuthorService {

    @Override
    public AuthorEntity createAuthor(AuthorEntity authorEntity) {
        return authorEntity;
    }
}
