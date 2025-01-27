package com.br.crudcourses.crudspring.dto.mapper;

import com.br.crudcourses.crudspring.dto.CourseDTO;
import com.br.crudcourses.crudspring.dto.LessonDTO;
import com.br.crudcourses.crudspring.enums.Category;
import com.br.crudcourses.crudspring.model.Course;
import com.br.crudcourses.crudspring.model.Lesson;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CourseMapperTest {

    CourseMapper courseMapper = new CourseMapper();

    @Test
    void shouldMapEntityToDtoSuccessfully() {
        List<Lesson> lessons = Arrays.asList(
                new Lesson(1L, "How to configure Java environment", "abcdefghij"),
                new Lesson(2L, "Lambda functions and functional programming", "jihgfedcba")
        );
        Course course = new Course(1L, "Java for Beginners",  Category.BACK_END, lessons);
        CourseDTO courseDTO = courseMapper.toDto(course);

        assertNotNull(course);
        assertEquals(1L, course.getId());
        assertEquals("Java for Beginners", course.getName());
        assertEquals(Category.BACK_END, course.getCategory());
        assertEquals(lessons, course.getLessons());

        assertEquals(course.getId(), courseDTO.id());
        assertEquals(course.getName(), courseDTO.name());
        assertEquals(course.getCategory().getValue(), courseDTO.category());
        assertEquals(course.getLessons().size(), courseDTO.lessons().size());

        IntStream.range(0, lessons.size()).forEach(i -> {
            Lesson lessonsObtained = course.getLessons().get(i);
            LessonDTO lessonsDTOObtained = courseDTO.lessons().get(i);
            assertEquals(lessonsObtained.getId(), lessonsDTOObtained.id());
            assertEquals(lessonsObtained.getName(), lessonsDTOObtained.name());
            assertEquals(lessonsObtained.getUrl(), lessonsDTOObtained.url());
        });
    }

    @Test
    void shouldMapDtoToEntitySuccessfully() {
        List<LessonDTO> lessonsDTO = Arrays.asList(
                new LessonDTO(1L, "First lessons of Java Expert Bootcamp", "abcdefghij"),
                new LessonDTO(2L, "Second lessons of Java Expert Bootcamp", "jihgfedcba")
        );
        CourseDTO courseDTO = new CourseDTO(1L, "Java Expert Bootcamp", "back-end", lessonsDTO);
        Course course = courseMapper.toEntity(courseDTO);

        assertNotNull(courseDTO);
        assertEquals(1L, courseDTO.id());
        assertEquals("Java Expert Bootcamp", courseDTO.name());
        assertEquals(Category.BACK_END.getValue(), courseDTO.category());
        assertEquals(lessonsDTO, courseDTO.lessons());

        assertEquals(courseDTO.id(), course.getId());
        assertEquals(courseDTO.name(), course.getName());
        assertEquals(courseDTO.category(), course.getCategory().getValue());
        assertEquals(courseDTO.lessons().size(), course.getLessons().size());

        IntStream.range(0, lessonsDTO.size()).forEach(i -> {
            LessonDTO lessonsDTOObtained = courseDTO.lessons().get(i);
            Lesson lessonObtained = course.getLessons().get(i);
            assertEquals(lessonsDTOObtained.id(), lessonObtained.getId());
            assertEquals(lessonsDTOObtained.name(), lessonObtained.getName());
            assertEquals(lessonsDTOObtained.url(), lessonObtained.getUrl());
        });
    }

    @Test
    void convertToCategory() {
    }

}