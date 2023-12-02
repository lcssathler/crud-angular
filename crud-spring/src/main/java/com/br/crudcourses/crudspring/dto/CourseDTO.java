package com.br.crudcourses.crudspring.dto;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.br.crudcourses.crudspring.enums.Category;
import com.br.crudcourses.crudspring.enums.validation.ValueOfEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
public record CourseDTO(
    @JsonProperty("_id") 
    Long id,

    @NotBlank
    @NotNull
    @Length(min = 5, max = 100)
    String name,

    @NotNull
    @NotEmpty
    @Valid
    @Length(max = 10)
    @ValueOfEnum(enumClass = Category.class)
    String category,

    @NotNull
    @NotEmpty
    @Valid
    List<LessonDTO> lessons) {
        
}
