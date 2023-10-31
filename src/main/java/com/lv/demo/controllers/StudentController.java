package com.lv.demo.controllers;

import com.lv.demo.entities.Student;
import com.lv.demo.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<String> createStudent(@Valid @RequestBody Student st) {
        Student student = studentService.createStudent(st);
        if (student == null) {
            throw new RuntimeException(st.getName());
        }
        return ResponseEntity.created(URI.create("/api/v1/student/" + student.getId())).build();
    }
}
