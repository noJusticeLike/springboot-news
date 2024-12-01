package com.springboot.news.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "ArticleDto Model Information")
public class ArticleDto {
    private Long id;

    @Schema(description = "News Article Title")
    @NotEmpty
    @Size(min = 2, message = "Заголовок статті має містити принаймні 2 символи")
    private String title;

    @Schema(description = "News Article Content")
    @NotEmpty
    @Size(min = 10, message = "Вміст статті має містити принаймні 10 символів")
    private String content;

    @Schema(description = "News Article Image URL")
    @NotEmpty
    private String imageURL;

    @Schema(description = "News Article Date")
    private LocalDate date;

    @Schema(description = "News Article Category")
    private Long categoryId;
}
