package com.example.database.controllers;

import com.example.database.TestDataUtil;
import com.example.database.domain.dto.BookDto;
import com.example.database.domain.entities.AuthorEntity;
import com.example.database.domain.entities.BookEntity;
import com.example.database.mappers.Mapper;
import com.example.database.mappers.impl.BookMapperImpl;
import com.example.database.services.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.awt.print.Book;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private BookService bookService;
    private Mapper<BookEntity,BookDto> bookMapper;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, BookService bookService,Mapper<BookEntity,BookDto> bookMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @Test
    public void testThatBookCreatedIsSuccessful() throws Exception {
        BookDto bookDto= TestDataUtil.createBookDtoA(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn() ).contentType(MediaType.APPLICATION_JSON).content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }
    @Test
    public void testThatBookCreatedIsReturned() throws Exception {
        BookDto bookDto= TestDataUtil.createBookDtoA(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn() ).contentType(MediaType.APPLICATION_JSON).content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("QWEQ-123421-3434")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("El FIlibusterismo")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author").isEmpty()
        );
    }
    @Test
    public void testThatGetBookByIdIsSuccess() throws Exception {
        BookDto bookDto= TestDataUtil.createBookDtoA(null);
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        bookService.save(bookEntity,bookEntity.getIsbn());
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + bookDto.getIsbn() ).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("QWEQ-123421-3434")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("El FIlibusterismo")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author").isEmpty()
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatBookEditedReturnsCorrectData() throws Exception {
        BookDto bookDto = TestDataUtil.createBookDtoA(null);
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedBook = bookService.save(bookEntity,bookDto.getIsbn());

        BookDto editBookData = TestDataUtil.createBookDtoA(null);
        editBookData.setTitle("Math");
        String json = objectMapper.writeValueAsString(editBookData);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn() ).contentType(MediaType.APPLICATION_JSON).content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("QWEQ-123421-3434")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("Math")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author").isEmpty()
        );
    }
    @Test
    public void testThatBookEditedReturns201() throws Exception {
        BookDto bookDto = TestDataUtil.createBookDtoA(null);
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedBook = bookService.save(bookEntity,bookDto.getIsbn());

        BookDto editBookData = TestDataUtil.createBookDtoA(null);
        editBookData.setTitle("Math");
        String json = objectMapper.writeValueAsString(editBookData);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn() ).contentType(MediaType.APPLICATION_JSON).content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }


}
