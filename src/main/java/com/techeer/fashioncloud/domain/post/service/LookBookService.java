package com.techeer.fashioncloud.domain.post.service;

import com.techeer.fashioncloud.domain.post.dto.request.BookCreateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.response.LookBookDataDto;
import com.techeer.fashioncloud.domain.post.entity.Book;
import com.techeer.fashioncloud.domain.post.entity.LookBook;
import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.domain.post.exception.BookNotFoundException;
import com.techeer.fashioncloud.domain.post.exception.LookBookNotFoundException;
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

    public Book bookCreate(BookCreateRequestDto dto) {
        Book entity = bookRepository.save(Book.builder()
                .title(dto.getTitle()).build());
        return entity;
    }

    public LookBook lookBookCreate(Book book, Post post) {
        LookBook entity = lookBookRepository.save(LookBook.builder()
                .book(book)
                .post(post).build());
        return entity;
    }

    public Book findBookById(UUID id) {
        return bookRepository.findById(id).orElseThrow(()-> new BookNotFoundException());
    }

    public LookBookDataDto findLookBookById(UUID id) {
        LookBook lookBook = lookBookRepository.findById(id).orElseThrow(LookBookNotFoundException::new);
        UUID postId = lookBook.getPost().getId();
        UUID lookBookId = lookBook.getBook().getId();

        return new LookBookDataDto(id, postId, lookBookId);
    }
}