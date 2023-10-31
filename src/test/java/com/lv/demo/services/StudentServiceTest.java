package com.lv.demo.services;

import com.lv.demo.entities.Student;
import com.lv.demo.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;

    private Student student;

    private StudentService studentService;

    @BeforeEach
    void setUp() {
        student = Student
                .builder()
                .id(1L)
                .name("John Doe")
                .build();

        studentService = new StudentService(studentRepository);
    }

    @Test
    void createStudent_Student() {
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        assertThat(studentService.createStudent(student), equalTo(student));
    }
}