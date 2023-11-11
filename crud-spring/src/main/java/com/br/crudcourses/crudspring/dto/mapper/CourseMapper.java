package com.br.crudcourses.crudspring.dto.mapper;

import org.springframework.stereotype.Component;

import com.br.crudcourses.crudspring.dto.CourseDTO;
import com.br.crudcourses.crudspring.model.Course;

@Component
public class CourseMapper {

    public CourseDTO toDto(Course course) {
        if (course == null) return null;

        return new CourseDTO(course.getId(), course.getName(), course.getCategory());
    }

    public Course toEntity(CourseDTO courseDTO) {
        if (courseDTO == null) return null;

        Course course = new Course();
        if (courseDTO.id() != null) {
            course.setId(courseDTO.id());
        }
        course.setName(courseDTO.name());
        course.setCategory(courseDTO.category());
        return course;
    }    
}
