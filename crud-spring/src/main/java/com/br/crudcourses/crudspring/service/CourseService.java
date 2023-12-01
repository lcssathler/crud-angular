package com.br.crudcourses.crudspring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.br.crudcourses.crudspring.dto.CourseDTO;
import com.br.crudcourses.crudspring.dto.mapper.CourseMapper;
import com.br.crudcourses.crudspring.exceptions.RecordNotFoundException;
import com.br.crudcourses.crudspring.repository.CourseRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
public class CourseService {
    private CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    @GetMapping
    public List<CourseDTO> coursesList() {
        return courseRepository.findAll()
        .stream()
        .map(courseMapper::toDto)
        .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CourseDTO findById(@PathVariable @NotNull @Positive Long id) {
        return courseRepository.findById(id)
        .map(courseMapper::toDto)
        .orElseThrow(() -> new RecordNotFoundException(id));
    }

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public CourseDTO create(@Valid @NotNull CourseDTO courseDTO) {
        return courseMapper.toDto(courseRepository.save(courseMapper.toEntity(courseDTO)));
    }

    @PutMapping("/{id}")
    public CourseDTO update(@NotNull @Positive Long id, @Valid @NotNull CourseDTO courseDTO) {
        return courseRepository.findById(id)
            .map(course -> {
                course.setName(courseDTO.name());
                course.setCategory(courseMapper.convertToCategory(courseDTO.category()));
                course.setLessons(courseMapper.toEntity(courseDTO).getLessons());
                return courseMapper.toDto(courseRepository.save(course));
            })
            .orElseThrow(() -> new RecordNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public void delete(@NotNull @Positive Long id) {
        courseRepository.delete(courseRepository.findById(id)
            .orElseThrow(() -> new RecordNotFoundException(id)));
    }
}
