package com.example.database.controllers;


import com.example.database.TestDataUtil;
import com.example.database.domain.dto.AuthorDto;
import com.example.database.domain.entities.AuthorEntity;
import com.example.database.services.AuthorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private AuthorService authorService;


    @Autowired
    public AuthorControllerIntegrationTests(MockMvc mockMvc, AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.authorService = authorService;
    }
    @Test
    public void testThatCreateAuthorIsSuccessful() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor1();
        authorEntity.setId(null);
        String authorJson = objectMapper.writeValueAsString(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );

    }
    @Test
    public void testThatCreateAuthorIsReturned() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor1();
        authorEntity.setId(null);
        String authorJson = objectMapper.writeValueAsString(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Shannon John")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(70));

    }
    @Test
    public void testThatGetAuthorsReturnsList() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor1();

        authorService.save(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("Shannon John")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value(70));

    }
    @Test
    public void testThatCreateAuthorReturns201WhenExists() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor1();
        authorEntity.setId(null);
        authorService.save(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );

    }

    @Test
    public void testThatNonAuthorReturns404When() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );

    }
    @Test
    public void testThatGetAuthorReturnsCorrectData() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor1();
        authorEntity.setId(null);
        authorService.save(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Shannon John")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(70));

    }
    @Test
    public void testThatReturns404WhenAuthorNotExists() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor1();
        authorEntity.setId(null);
        authorService.save(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/99")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );

    }
    @Test
    public void testThatAuthorEditedisOK() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor1();
        AuthorEntity savedAuthor = authorService.save(authorEntity);

        AuthorDto authorDto = TestDataUtil.createTestAuthorDto();
        String json = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)

        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }
    @Test
    public void testThatAuthorIsEdited() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor1();
        AuthorEntity savedAuthor = authorService.save(authorEntity);

        AuthorDto authorDto = TestDataUtil.createTestAuthorDto();
        authorDto.setId(savedAuthor.getId());
        String json = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(authorDto.getAge()));
    }

    @Test
    public void testThatAuthorPatchedReturnsStatusOK() throws Exception{
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor1();
        AuthorEntity savedAuthor = authorService.save(authorEntity);

        AuthorDto authorDto = TestDataUtil.createTestAuthorDto();
        authorDto.setId(savedAuthor.getId());
        String json = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/"+savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatAuthorPatchReturnsCorrectData() throws Exception{
        AuthorEntity authorEntity = TestDataUtil.createTestAuthor1();
        AuthorEntity savedAuthor = authorService.save(authorEntity);

        AuthorDto authorDto = TestDataUtil.createTestAuthorDto();
        authorDto.setName("Updated");
        String json = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(22)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Updated")
        );
    }





}
