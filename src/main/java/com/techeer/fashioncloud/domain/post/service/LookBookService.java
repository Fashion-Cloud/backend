package com.techeer.fashioncloud.domain.post.service;

import com.techeer.fashioncloud.domain.post.dto.request.LookBookCreateRequestDto;
import com.techeer.fashioncloud.domain.post.entity.LookBook;
import com.techeer.fashioncloud.domain.post.repository.LookBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LookBookService {

    private final LookBookRepository lookBookRepository;
    public LookBook create(LookBookCreateRequestDto dto) {
        LookBook entity = lookBookRepository.save(LookBook.builder()
                .title(dto.getTitle()).build());
        return entity;
    }
}