package com.br.crudcourses.crudspring.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import com.br.crudcourses.crudspring.enums.Category;
import com.br.crudcourses.crudspring.enums.converters.CategoryConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SQLDelete(sql = "UPDATE courses SET status = 'inactive' WHERE id = ?")
@Where(clause = "status = 'active'")
@Data
@Entity
@Table(name = "courses")
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("_id")
    private Long id;

    @Column(length = 100, nullable = false)
    @NotBlank
    @NotNull
    @Length(min = 5, max = 100)
    private String name;

    // @Column(length = 10, nullable = false)
    @NotBlank
    @NotNull
    // @Pattern(regexp = "back-end|front-end")
    @Convert(converter = CategoryConverter.class)
    private Category category;

    @Column(length = 10, nullable = false)
    @NotBlank
    @NotNull
    @Pattern(regexp = "active|inactive")
    private String status = "active";
    
}
