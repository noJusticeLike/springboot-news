package com.springboot.news.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleResponse {
    private List<ArticleDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
