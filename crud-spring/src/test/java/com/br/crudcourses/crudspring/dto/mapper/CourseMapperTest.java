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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

        assertEquals("Java for Beginners", course.getName());

        assertEquals(course.getId(), courseDTO.id());
        assertEquals(course.getName(), courseDTO.name());
        assertEquals(course.getCategory().getValue(), courseDTO.category());
        assertEquals(course.getLessons().size(), courseDTO.lessons().size());

        assertThat(courseDTO.lessons())
                .hasSameSizeAs(course.getLessons())
                .allSatisfy(lessonDTO -> {
                    int index = courseDTO.lessons().indexOf(lessonDTO);
                    Lesson lesson = course.getLessons().get(index);

                    assertEquals(lesson.getId(), lessonDTO.id(), "Lesson id does not match");
                    assertEquals(lesson.getName(), lessonDTO.name(), "Lesson name does not match");
                    assertEquals(lesson.getUrl(), lessonDTO.url(), "Lesson url does not match");
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

        assertEquals(courseDTO.id(), course.getId());
        assertEquals(courseDTO.name(), course.getName());
        assertEquals(courseDTO.category(), course.getCategory().getValue());
        assertEquals(courseDTO.lessons().size(), course.getLessons().size());

        assertThat(course.getLessons())
                .hasSameSizeAs(courseDTO.lessons())
                .allSatisfy(lesson -> {
                    int index = course.getLessons().indexOf(lesson);
                    LessonDTO lessonDTO = courseDTO.lessons().get(index);

                    assertEquals(lessonDTO.id(), lesson.getId(), "Lesson id does not match");
                    assertEquals(lessonDTO.name(), lesson.getName(), "Lesson name does not match");
                    assertEquals(lessonDTO.url(), lesson.getUrl(), "Lesson url does not match");
                });
    }

    @Test
    void convertToCategory() {
        when(Category.valueOf("back-end")).thenReturn(Category.BACK_END);
        when(Category.valueOf("front-end")).thenReturn(Category.FRONT_END);
    }  

}