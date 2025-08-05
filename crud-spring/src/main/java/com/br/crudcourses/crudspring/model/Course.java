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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@SQLDelete(sql = "UPDATE courses SET status = 'inactive' WHERE id = ?")
@Where(clause = "status = 'Active'")
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

    @NotNull
    @NotEmpty
    @Valid
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "course")
    private List<Lesson> lessons = new ArrayList<>();

    public Course() {

    }

    public Course(Long id, @NotBlank @NotNull @Length(min = 5, max = 100) String name, @NotNull Category category,
            List<Lesson> lessons) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.lessons = lessons;
    }

    @Override
    public String toString() {
        return "Course [id=" + id + ", name=" + name + ", category=" + category + ", status=" + status + ", lessons="
                + lessons + "]";
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public Status getStatus() {
        return status;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(@NotBlank @NotNull @Length(min = 5, max = 100) String name) {
        this.name = name;
    }

    public void setCategory(@NotNull Category category) {
        this.category = category;
    }

    public void setStatus(@NotNull Status status) {
        this.status = status;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

}
