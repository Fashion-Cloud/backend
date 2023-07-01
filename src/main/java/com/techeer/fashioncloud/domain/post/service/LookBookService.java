package com.techeer.fashioncloud.domain.post.service;

import com.techeer.fashioncloud.domain.post.dto.mapper.LookBookMapper;
import com.techeer.fashioncloud.domain.post.dto.request.LookBookCreateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.response.LookBookPostDataResponseDto;
import com.techeer.fashioncloud.domain.post.entity.LookBook;
import com.techeer.fashioncloud.domain.post.entity.LookBookPost;
import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.domain.post.exception.BookNotFoundException;
import com.techeer.fashioncloud.domain.post.exception.LookBookPostNotFoundException;
import com.techeer.fashioncloud.domain.post.repository.BookRepository;
import com.techeer.fashioncloud.domain.post.repository.LookBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LookBookService {

    private final BookRepository bookRepository;
    private final LookBookRepository lookBookRepository;
    private final LookBookMapper lookBookMapper;

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

    public List<LookBookPostDataResponseDto> findLookBookById(UUID id) {
        LookBook lookBook = bookRepository.findById(id).orElseThrow(LookBookPostNotFoundException::new);
        List<LookBookPost> lookbooklist = lookBookRepository.findByLookBook(lookBook);
        List<Post> postList = lookbooklist.stream().map(LookBookPost::getPost).collect(Collectors.toList());

        return postList.stream().map(lookBookMapper::toDataDto).collect(Collectors.toList());
    }
}