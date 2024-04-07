package com.example.database.services.impl;

import com.example.database.domain.entities.AuthorEntity;
import com.example.database.repositories.AuthorRepository;
import com.example.database.services.AuthorService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @Override
    public List<AuthorEntity> findAll() {
        Iterable<AuthorEntity> authors = authorRepository.findAll();
        return StreamSupport.stream(authors.spliterator(),false).collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorEntity> findOne(long id) {
        return authorRepository.findById(id);

    }

}
