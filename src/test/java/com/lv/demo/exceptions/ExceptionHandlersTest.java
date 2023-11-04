package com.lv.demo.exceptions;

import com.lv.demo.entities.Student;
import jakarta.validation.Valid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static groovy.json.JsonOutput.toJson;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ExceptionHandlersTest.TestController.class)
@ContextConfiguration(classes = {
        ExceptionHandlers.class,
        ExceptionHandlersTest.TestController.class
})
@AutoConfigureMockMvc
@AutoConfigureWebClient
class ExceptionHandlersTest {
    @Autowired
    MockMvc mockMvc;

    Student student;

    @BeforeEach
    void setUp() {
        student = Student
                .builder()
                .name("John Doe")
                .build();
    }

    @Test
    void runtimeException_ResourceNotFoundException() throws Exception {
        mockMvc
                .perform(post("/test/runtime-exception")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(student)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof RuntimeException))
                .andExpect(jsonPath("$.timeStamp", notNullValue()))
                .andExpect(jsonPath("$.error",     equalTo(400)))
                .andExpect(jsonPath("$.message",   containsString("Could not create user with name")))
                .andExpect(jsonPath("$.path",      equalTo("uri=/test/runtime-exception")));
    }

    @RestController
    @RequestMapping("/test")
    public static class TestController {
        @PostMapping("/runtime-exception")
        public ResponseEntity<String> runtimeException(@Valid @RequestBody Student student) {
            throw new ResourceNotFoundException("Name is empty");
        }
    }
}