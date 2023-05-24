package com.techeer.fashioncloud.domain.post.service;

import com.techeer.fashioncloud.domain.post.dto.request.LookBookCreateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.request.LookBookPostCreateRequestDto;
import com.techeer.fashioncloud.domain.post.entity.LookBook;
import com.techeer.fashioncloud.domain.post.entity.LookBookPost;
import com.techeer.fashioncloud.domain.post.exception.BookNotFoundException;
import com.techeer.fashioncloud.domain.post.repository.LookBookPostRepository;
import com.techeer.fashioncloud.domain.post.repository.LookBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class LookBookService {

    private final LookBookRepository bookRepository;
    private final LookBookPostRepository bookPostRepository;
    public LookBook bookCreate(LookBookCreateRequestDto dto) {
        LookBook entity = bookRepository.save(LookBook.builder()
                .title(dto.getTitle()).build());
        return entity;
    }

    public LookBookPost bookPostCreate(LookBookPostCreateRequestDto dto) {
        LookBookPost entity = bookPostRepository.save(LookBookPost.builder()
                .lookBook(dto.getLookBook())
                .post(dto.getPost()).build());
        return entity;
    }

    public LookBookPost findBookById(UUID id) {
        return bookPostRepository.findById(id).orElseThrow(()-> new BookNotFoundException());
    }
}