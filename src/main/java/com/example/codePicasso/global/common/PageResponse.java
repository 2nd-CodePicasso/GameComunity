package com.example.codePicasso.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageResponse<T> {
    private List<T> content;
    private int page;
    private int totalPages;
    private long totalElements;
}
