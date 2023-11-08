package com.br.crudcourses.crudspring.model;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(length = 10, nullable = false)
    @NotBlank
    @NotNull
    @Pattern(regexp = "Back-End|Front-End")
    private String category;

    public Course(String name, String category) {
        this.name = name;
        this.category = category;
    }
}
