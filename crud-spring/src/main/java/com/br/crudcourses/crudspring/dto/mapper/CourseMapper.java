package com.br.crudcourses.crudspring.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.br.crudcourses.crudspring.dto.CourseDTO;
import com.br.crudcourses.crudspring.dto.LessonDTO;
import com.br.crudcourses.crudspring.enums.Category;
import com.br.crudcourses.crudspring.model.Course;
import com.br.crudcourses.crudspring.model.Lesson;

@Component
public class CourseMapper {
    public CourseDTO toDto(Course course) {
        if (course == null) return null;

        List<LessonDTO> lessonsDTO = course.getLessons().stream()
            .map(lesson -> new LessonDTO(lesson.getId(), lesson.getName(), lesson.getUrl()))
            .collect(Collectors.toList());

        return new CourseDTO(course.getId(), 
            course.getName(), 
            course.getCategory().getValue(),
            lessonsDTO);
    }

    public Course toEntity(CourseDTO courseDTO) {
        if (courseDTO == null) return null;

        Course course = new Course();
        if (courseDTO.id() != null) {
            course.setId(courseDTO.id());
        }
        course.setName(courseDTO.name());
        course.setCategory(convertToCategory(courseDTO.category()));
        List<Lesson> lessons = courseDTO.lessons().stream()
            .map(lessonDTO -> {
                Lesson lesson = new Lesson();
                lesson.setId(lessonDTO.id());
                lesson.setName(lessonDTO.name());
                lesson.setUrl(lessonDTO.url());
                lesson.setCourse(course);
                return lesson;  
            })
            .collect(Collectors.toList());

        course.setLessons(lessons);
        
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
