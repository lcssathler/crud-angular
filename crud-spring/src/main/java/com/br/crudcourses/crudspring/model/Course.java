package com.br.crudcourses.crudspring.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.DialectOverride.Where;
import org.hibernate.annotations.DialectOverride.Wheres;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SQLDelete(sql = "UPDATE courses SET status = 'inactive' WHERE id = ?")
@org.hibernate.annotations.Where(clause = "WHERE status = 'active'")
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

    @Column(length = 10, nullable = false)
    @NotBlank
    @NotNull
    @Pattern(regexp = "active|inactive")
    private String status = "active";


    public Course(String name, String category) {
        this.name = name;
        this.category = category;
    }
}
