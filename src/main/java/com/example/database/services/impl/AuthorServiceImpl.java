package com.example.database.services.impl;

import com.example.database.domain.entities.AuthorEntity;
import com.example.database.repositories.AuthorRepository;
import com.example.database.services.AuthorService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }
    @Override
    public AuthorEntity save(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }
}
