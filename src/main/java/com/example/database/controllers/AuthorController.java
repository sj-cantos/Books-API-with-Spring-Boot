package com.example.database.controllers;

import com.example.database.domain.dto.AuthorDto;
import com.example.database.domain.entities.AuthorEntity;
import com.example.database.mappers.AuthorMapper;
import com.example.database.mappers.impl.AuthorMapperImpl;
import com.example.database.services.AuthorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {
    private AuthorService authorService;
    private AuthorMapper<AuthorEntity,AuthorDto> authorMapper;


    public AuthorController(AuthorService authorService,AuthorMapper<AuthorEntity,AuthorDto> authorMapper){
        this.authorMapper = authorMapper;
        this.authorService = authorService;
    }
    @PostMapping("/authors")
    public AuthorDto createAuthor(@RequestBody AuthorDto author){
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity savedAuthorEntity = authorService.createAuthor(authorEntity);
        return authorMapper.mapTo(savedAuthorEntity);
    }
}
