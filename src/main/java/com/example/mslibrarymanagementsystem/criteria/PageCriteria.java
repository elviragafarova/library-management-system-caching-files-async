package com.example.mslibrarymanagementsystem.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageCriteria {
    private Integer page = 0;
    private Integer count = 10;
    private String sortBy = "id";
    private String direction = "asc";
}