package com.techeer.fashioncloud.global.dto;

import com.techeer.fashioncloud.global.util.validation.ValidEnum;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomPageRequest {

    @Min(value = 1)
    @Parameter(description = "(default: 1)")
    private int page = 1;

    @Min(value = 1)
    @Parameter(description = "(default: 10)")
    private int size = 10;

    @ValidEnum(enumClass = Sort.Direction.class)
    @Parameter(description = "(default: DESC)")
    private Sort.Direction order = Sort.Direction.DESC;

    @Parameter(description = "정렬 조건 (default: createdAt)")
    private String[] sort = {"createdAt"};

    public PageRequest set() {
        return PageRequest.of(page - 1, size, order, sort);
    }
}