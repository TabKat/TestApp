package com.lv.demo.services;

import com.lv.demo.entities.Student;
import com.lv.demo.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StudentService {
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional
    public Student createStudent(Student student) {
        log.info("creating student: {}", student);
        return studentRepository.save(student);
    }
}
