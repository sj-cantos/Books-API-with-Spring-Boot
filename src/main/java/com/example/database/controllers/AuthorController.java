package com.example.database.controllers;

import com.example.database.domain.dto.AuthorDto;
import com.example.database.domain.entities.AuthorEntity;
import com.example.database.mappers.Mapper;
import com.example.database.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
    @GetMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("id") long id){
        Optional<AuthorEntity> foundAuthor = authorService.findOne(id);
        return foundAuthor.map(authorEntity -> {
            AuthorDto authorDto = authorMapper.mapTo(authorEntity);
            return new ResponseEntity<>(authorDto,HttpStatus.OK);
        }).orElse(
                new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );

    }

    @PutMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> editAuthor(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto){
        if(!authorService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity savedAuthor = authorService.save(authorEntity);
        AuthorDto author = authorMapper.mapTo(savedAuthor);
        return new ResponseEntity<>(author,HttpStatus.OK);

    }
}
