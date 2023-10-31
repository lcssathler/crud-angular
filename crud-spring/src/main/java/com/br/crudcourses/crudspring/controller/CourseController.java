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
                .map(response -> ResponseEntity.ok().body(response))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public Course createCourse(@RequestBody Course course) {
        return courseRepository.save(course);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@PathVariable Long id, @RequestBody Course course) {
        return courseRepository.findById(id)
                .map(courseFound -> {
                    courseFound.setName(course.getName());
                    courseFound.setCategory(course.getCategory());
                    Course updated = courseRepository.save(courseFound);
                    return ResponseEntity.ok().body(courseFound);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return courseRepository.findById(id)
            .map(courseFound -> {
                courseRepository.deleteById(courseFound.getId());
                return ResponseEntity.noContent().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
    }
}
