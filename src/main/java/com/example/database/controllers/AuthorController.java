package com.example.database.controllers;

import com.example.database.domain.dto.AuthorDto;
import com.example.database.domain.entities.AuthorEntity;
import com.example.database.mappers.Mapper;
import com.example.database.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthorController {
    private AuthorService authorService;
    private Mapper<AuthorEntity,AuthorDto> authorMapper;


    public AuthorController(AuthorService authorService, Mapper<AuthorEntity,AuthorDto> authorMapper){

        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }
    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author){
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity savedAuthorEntity = authorService.save(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity),HttpStatus.CREATED);
    }
    @GetMapping(path = "/authors")
    public List<AuthorDto> findAllAuthors(){
        List<AuthorEntity> authorEntityList = authorService.findAll();
        return authorEntityList.stream()
                .map(authorMapper::mapTo)
                .collect(Collectors.toList());

    }
}
