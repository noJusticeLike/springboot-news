package com.springboot.news.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "CategoryDto Model Information")
public class CategoryDto {
    private Long id;

    @Schema(description = "Category Name")
    @NotEmpty
    @Size(min = 2, message = "Назва категорії повинна містити принаймні 2 символи")
    private String name;

    @Schema(description = "Category Description")
    @NotEmpty
    @Size(min = 10, message = "Опис категорії повинен містити принаймні 10 символів")
    private String description;
}
