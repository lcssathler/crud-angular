package com.br.crudcourses.crudspring.dto;

import java.util.List;

public record CoursePageDTO(List<CourseDTO> coursesDTO, long totalElements, int totalPages) {
    
}
