package com.br.crudcourses.crudspring.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import com.br.crudcourses.crudspring.enums.Category;
import com.br.crudcourses.crudspring.enums.Status;
import com.br.crudcourses.crudspring.enums.converters.CategoryConverter;
import com.br.crudcourses.crudspring.enums.converters.StatusConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SQLDelete(sql = "UPDATE courses SET status = 'inactive' WHERE id = ?")
@Where(clause = "status = 'Active'")
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
    @NotNull
    @Convert(converter = CategoryConverter.class)
    private Category category;

    @Column(length = 10, nullable = false)
    @NotNull
    @Convert(converter = StatusConverter.class)
    private Status status = Status.ACTIVE;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "course")
    private List<Lesson> lessons = new ArrayList<>();

    public Course(Long id, @NotBlank @NotNull @Length(min = 5, max = 100) String name, @NotNull Category category,
            List<Lesson> lessons) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.lessons = lessons;
    }

    
}
