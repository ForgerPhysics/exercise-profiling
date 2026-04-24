package com.advpro.profiling.tutorial.service;

import com.advpro.profiling.tutorial.model.Student;
import com.advpro.profiling.tutorial.model.StudentCourse;
import com.advpro.profiling.tutorial.repository.StudentCourseRepository;
import com.advpro.profiling.tutorial.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author muhammad.khadafi
 */
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    public List<StudentCourse> getAllStudentsWithCourses() {
        //Mencegah N+1 Query Problem dengan langsung mengambil seluruh relasi
        return studentCourseRepository.findAll();
    }

    public Optional<Student> findStudentWithHighestGpa() {
        //Menggunakan query level database (ORDER BY gpa DESC LIMIT 1)
        // jauh lebih cepat daripada memuat 20.000 data ke memori aplikasi.
        return Optional.ofNullable(studentRepository.findFirstByOrderByGpaDesc());
    }

    public String joinStudentNames() {
        List<Student> students = studentRepository.findAll();
        //Menggunakan Stream API dan Collectors.joining (StringBuilder internal)
        // untuk menghindari penalti performa fatal dari konkatenasi String (+=) di dalam loop.
        return students.stream()
                .map(Student::getName)
                .collect(Collectors.joining(", "));
    }
}