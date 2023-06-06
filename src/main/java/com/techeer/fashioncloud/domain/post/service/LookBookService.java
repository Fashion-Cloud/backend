package com.techeer.fashioncloud.domain.post.service;

import com.techeer.fashioncloud.domain.post.dto.request.LookBookCreateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.response.LookBookPostDataDto;
import com.techeer.fashioncloud.domain.post.entity.LookBook;
import com.techeer.fashioncloud.domain.post.entity.LookBookPost;
import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.domain.post.exception.BookNotFoundException;
import com.techeer.fashioncloud.domain.post.exception.LookBookPostNotFoundException;
import com.techeer.fashioncloud.domain.post.repository.LookBookRepository;
import com.techeer.fashioncloud.domain.post.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class LookBookService {

    private final BookRepository bookRepository;
    private final LookBookRepository lookBookRepository;

    public LookBook bookCreate(LookBookCreateRequestDto dto) {
        LookBook entity = bookRepository.save(LookBook.builder()
                .title(dto.getTitle()).build());
        return entity;
    }

    public LookBookPost lookBookCreate(LookBook lookBook, Post post) {
        LookBookPost entity = lookBookRepository.save(LookBookPost.builder()
                .lookBook(lookBook)
                .post(post).build());
        return entity;
    }

    public LookBook findBookById(UUID id) {
        return bookRepository.findById(id).orElseThrow(()-> new BookNotFoundException());
    }

    public LookBookPostDataDto findLookBookById(UUID id) {
        LookBookPost lookBookPost = lookBookRepository.findById(id).orElseThrow(LookBookPostNotFoundException::new);
        UUID postId = lookBookPost.getPost().getId();
        UUID lookBookId = lookBookPost.getLookBook().getId();

        return new LookBookPostDataDto(id, postId, lookBookId);
    }
}