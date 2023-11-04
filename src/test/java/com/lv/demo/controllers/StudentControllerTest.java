package com.lv.demo.controllers;

import com.lv.demo.entities.Student;
import com.lv.demo.services.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static groovy.json.JsonOutput.toJson;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        student = Student
                .builder()
                .id(1L)
                .name("John Doe")
                .build();
    }

    @Test
    void createStudent_ResourceNotFoundException() throws Exception {
        when(studentService.createStudent(any(Student.class))).thenReturn(null);

        mockMvc.perform(post("/api/v1/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(student)))
                .andExpect(jsonPath("$.timeStamp", notNullValue()))
                .andExpect(jsonPath("$.error",     equalTo(400)))
                .andExpect(jsonPath("$.message",   containsString("Could not create user with name")))
                .andExpect(jsonPath("$.path",      equalTo("uri=/api/v1/student")));
    }

    @Test
    void createStudent_String() throws Exception {
        when(studentService.createStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/api/v1/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(student)))
                .andExpect(header().string("Location", "/api/v1/student/1"));
    }

}