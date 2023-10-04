package com.techeer.fashioncloud.global.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedResponse<T> {
    private int size;
    private int currentPage;
    private int totalPages;
    private long totalElements;
    private List<T> content;

    // 엔티티 그대로 반환
    public static <E> PaginatedResponse<E> of(Page<E> pageResult) {
        return PaginatedResponse.<E>builder()
                .size(pageResult.getSize())
                .currentPage(pageResult.getPageable().getPageNumber())
                .totalPages(pageResult.getTotalPages())
                .totalElements(pageResult.getTotalElements())
                .content(pageResult.getContent())
                .build();
    }

    // Dto로 변환하여 반환
    public static <E, D> PaginatedResponse<D> of(Page<E> pageResult, Function<E, D> function) {
        return PaginatedResponse.<D>builder()
                .size(pageResult.getSize())
                .currentPage(pageResult.getPageable().getPageNumber())
                .totalPages(pageResult.getTotalPages())
                .totalElements(pageResult.getTotalElements())
                .content(pageResult.getContent().stream().map(function).toList())
                .build();
    }
}
