package com.example.database.controllers;

import com.example.database.domain.dto.AuthorDto;
import com.example.database.services.AuthorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {
    AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }
    @PostMapping("/authors")
    public AuthorDto createAuthor(@RequestBody AuthorDto author){
        return authorService.createAuthor(author);
    }
}
