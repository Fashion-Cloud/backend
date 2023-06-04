package com.techeer.fashioncloud.domain.post.dto.mapper;

import com.techeer.fashioncloud.domain.post.dto.response.LookBookResponseDto;
import com.techeer.fashioncloud.domain.post.dto.response.BookResponseDto;
import com.techeer.fashioncloud.domain.post.entity.Book;
import com.techeer.fashioncloud.domain.post.entity.LookBook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LookBookMapper {
    public BookResponseDto toBookResponseDto(Book entity) {
        return BookResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .build();
    }

    public LookBookResponseDto toBookPostResponseDto(LookBook entity) {
        return LookBookResponseDto.builder()
                .id(entity.getId())
                .lookBook(entity.getBook())
                .post(entity.getPost())
                .build();
    }
}
