package com.techeer.fashioncloud.global.dto;

import com.techeer.fashioncloud.global.util.validation.ValidEnum;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class CustomPageRequest {

    @Min(value = 0)
    private int page = 0;

    @Min(value = 1)
    private int size = 10;

    @ValidEnum(enumClass = Sort.Direction.class)
    @Parameter(description = "ASC / DESC(default)")
    private Sort.Direction order = Sort.Direction.DESC;

    private String[] sort = {"createdAt"};

    public PageRequest of() {
        return PageRequest.of(page, size, order, sort);
    }
}