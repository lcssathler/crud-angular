package com.br.crudcourses.crudspring.dto.mapper;

import com.br.crudcourses.crudspring.dto.CourseDTO;
import com.br.crudcourses.crudspring.dto.LessonDTO;
import com.br.crudcourses.crudspring.enums.Category;
import com.br.crudcourses.crudspring.model.Course;
import com.br.crudcourses.crudspring.model.Lesson;
import com.br.crudcourses.crudspring.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
            List<Lesson> lessonsObtained = course.getLessons();
            List<LessonDTO> lessonsDTOObtained = courseDTO.lessons();
            assertEquals(lessonsObtained.get(i).getId(), lessonsDTOObtained.get(i).id());
            assertEquals(lessonsObtained.get(i).getName(), lessonsDTOObtained.get(i).name());
            assertEquals(lessonsObtained.get(i).getUrl(), lessonsDTOObtained.get(i).url());
        });
    }

    @Test
    void shouldMapDtoToEntitySuccessfully() {

    }

    @Test
    void convertToCategory() {
    }

}