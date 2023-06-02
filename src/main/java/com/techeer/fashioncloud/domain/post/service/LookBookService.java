package com.techeer.fashioncloud.domain.post.service;

import com.techeer.fashioncloud.domain.post.dto.request.LookBookCreateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.response.LookBookPostDataDto;
import com.techeer.fashioncloud.domain.post.entity.LookBook;
import com.techeer.fashioncloud.domain.post.entity.LookBookPost;
import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.domain.post.exception.BookNotFoundException;
import com.techeer.fashioncloud.domain.post.exception.LookBookNotFoundException;
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

    public LookBookPost lookBookCreate(LookBook lookbook,Post post) {
        LookBookPost entity = bookPostRepository.save(LookBookPost.builder()
                .lookBook(lookbook)
                .post(post).build());
        return entity;
    }

    public LookBook findBookById(UUID id) {
        return bookRepository.findById(id).orElseThrow(()-> new BookNotFoundException());
    }

    public LookBookPostDataDto findLookBookById(UUID id) {
        LookBookPost lookBookPost = bookPostRepository.findById(id).orElseThrow(LookBookNotFoundException::new);
        UUID postId = lookBookPost.getPost().getId();
        UUID lookBookId = lookBookPost.getLookBook().getId();

        return new LookBookPostDataDto(id, postId, lookBookId);
    }
}