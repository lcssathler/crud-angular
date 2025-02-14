package com.br.crudcourses.crudspring.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.br.crudcourses.crudspring.dto.CourseDTO;
import com.br.crudcourses.crudspring.dto.CoursePageDTO;
import com.br.crudcourses.crudspring.dto.mapper.CourseMapper;
import com.br.crudcourses.crudspring.exceptions.RecordNotFoundException;
import com.br.crudcourses.crudspring.model.Course;
import com.br.crudcourses.crudspring.repository.CourseRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    @GetMapping
    public CoursePageDTO coursesList(@PositiveOrZero int page, @Positive @Max(100) int pageSize) {
        Page<Course> coursePage = courseRepository.findAll(PageRequest.of(page, pageSize));
        List<CourseDTO> coursesDTO = coursePage.get()
            .map(courseMapper::toDto)
            .collect(Collectors.toList());
        return new CoursePageDTO(coursesDTO, coursePage.getTotalElements(), coursePage.getTotalPages());
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
            .map(recordFound -> {
                Course course = courseMapper.toEntity(courseDTO);
                recordFound.setName(courseDTO.name());
                recordFound.setCategory(courseMapper.convertToCategory(courseDTO.category()));
                recordFound.getLessons().clear();
                course.getLessons().forEach(lesson -> recordFound.getLessons().add(lesson));
                return courseMapper.toDto(courseRepository.save(recordFound));
            })
            .orElseThrow(() -> new RecordNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public void delete(@NotNull @Positive Long id) {
        courseRepository.delete(courseRepository.findById(id)
            .orElseThrow(() -> new RecordNotFoundException(id)));
    }

    @DeleteMapping
    public void deleteAllSelected(List<CourseDTO> coursesDTO) {
        List<Long> ids = coursesDTO.stream()
            .map(CourseDTO::id)
            .collect(Collectors.toList());
        
        courseRepository.deleteAllById(ids);
    }
}
