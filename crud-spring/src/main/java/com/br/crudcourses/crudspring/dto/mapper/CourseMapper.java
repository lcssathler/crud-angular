package com.br.crudcourses.crudspring.dto.mapper;

import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.br.crudcourses.crudspring.dto.CourseDTO;
import com.br.crudcourses.crudspring.enums.Category;
import com.br.crudcourses.crudspring.model.Course;

@Component
public class CourseMapper {

    public CourseDTO toDto(Course course) {
        if (course == null) return null;

        return new CourseDTO(course.getId(), 
                            course.getName(), 
                            course.getCategory().getValue(),
                            course.getLessons());
    }

    public Course toEntity(CourseDTO courseDTO) {
        if (courseDTO == null) return null;

        Course course = new Course();
        if (courseDTO.id() != null) {
            course.setId(courseDTO.id());
        }
        course.setName(courseDTO.name());
        course.setCategory(convertToCategory(courseDTO.category()));
        return course;
    }  
    
    public Category convertToCategory(String value) {
        return switch (value) {
            case "front-end" -> Category.FRONT_END;
            case "back-end" -> Category.BACK_END;
            default -> throw new IllegalArgumentException();
        };

        // return Stream.of(Category.values())
        //     .filter(category -> category.getValue().equals(value))
        //     .findFirst()
        //     .orElseThrow(IllegalArgumentException::new)
        //     .getValue();
    }
}
