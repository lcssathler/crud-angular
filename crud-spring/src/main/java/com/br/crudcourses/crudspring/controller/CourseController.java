package com.br.crudcourses.crudspring.controller;

import com.br.crudcourses.crudspring.model.Course;
import com.br.crudcourses.crudspring.repository.CourseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseRepository courseRepository;
    public Course course = new Course("Java Professional", "back-end");

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    
    @GetMapping
    public List<Course> coursesList() {
        return courseRepository.findAll();
    }

    @PostMapping("/save")
    public ResponseEntity<Course> save () {
        return ResponseEntity.status(HttpStatus.OK).body(courseRepository.save(course));
    }
}
