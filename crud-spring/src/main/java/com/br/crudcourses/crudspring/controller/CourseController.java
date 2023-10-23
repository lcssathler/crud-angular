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

    @GetMapping("/{id}")
    public ResponseEntity<Course> findById(@PathVariable Long id) {
        return courseRepository.findById(id)
        .map(course -> ResponseEntity.ok().body(course))
        .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping()
    public ResponseEntity<Course> createCourse (@RequestBody Course course) {
        System.out.println(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseRepository.save(course));
    }
}
 