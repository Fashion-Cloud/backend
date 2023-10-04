package com.techeer.fashioncloud.global.dto;

import com.techeer.fashioncloud.global.util.validation.ValidEnum;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomPageRequest {

    @Min(value = 0)
    @Parameter(description = "(default: 0)")
    private int page = 0;

    @Min(value = 1)
    @Parameter(description = "(default: 10)")
    private int size = 10;

    @ValidEnum(enumClass = Sort.Direction.class)
    @Parameter(description = "(default: DESC)")
    private Sort.Direction order = Sort.Direction.DESC;

    @Parameter(description = "정렬 조건 (default: createdAt)")
    private String[] sort = {"createdAt"};

    public PageRequest of() {
        return PageRequest.of(page, size, order, sort);
    }
}