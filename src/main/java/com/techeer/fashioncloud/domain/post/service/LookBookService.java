package com.techeer.fashioncloud.domain.post.service;

import com.techeer.fashioncloud.domain.post.dto.request.LookBookCreateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.response.LookBookResponseDto;
import com.techeer.fashioncloud.domain.post.entity.LookBook;
import com.techeer.fashioncloud.domain.post.entity.LookBookPost;
import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.domain.post.exception.LookBookNotFoundException;
import com.techeer.fashioncloud.domain.post.exception.PostNotFoundException;
import com.techeer.fashioncloud.domain.post.repository.LookBookPostRepository;
import com.techeer.fashioncloud.domain.post.repository.LookBookRepository;
import com.techeer.fashioncloud.domain.post.repository.PostRepository;
import com.techeer.fashioncloud.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LookBookService {

    private final LookBookRepository lookBookRepository;
    private final LookBookPostRepository lookBookPostRepository;
    private final PostRepository postRepository;

    @Transactional
    public LookBookResponseDto lookBookCreate(User loginUser, LookBookCreateRequestDto dto) {
        LookBook lookBook = LookBook.builder()
                .user(loginUser)
                .title(dto.getTitle())
                .image(dto.getImage())
                .build();
        return LookBookResponseDto.of(lookBookRepository.save(lookBook));
    }

    @Transactional(readOnly = true)
    public List<LookBookResponseDto> findBookByUserId(Long userId) {
        List<LookBookResponseDto> lookbooklist = lookBookRepository.findByUserId(userId)
                .stream()
                .map(LookBookResponseDto::of)
                .toList();

        return lookbooklist;
    }

    @Transactional
    public void addPostToLookBook(Long userId, Long lookBookId, UUID postId) {
        LookBook lookBook = lookBookRepository.findById(lookBookId).orElseThrow(() -> new LookBookNotFoundException());
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);

        // TODO 사용자의 게시글인지 체크, 이미 추가했는지 체크
        LookBookPost entity = lookBookPostRepository.save(
                LookBookPost.builder()
                        .lookBook(lookBook)
                        .post(post).build());
    }


// TODO 삭제
//
//    public List<LookBookPostDataResponseDto> findLookBookById(UUID id) {
//        LookBook lookBook = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException());
//        List<LookBookPost> lookbooklist = lookBookPostRepository.findByLookBook(lookBook);
//        List<Post> postList = lookbooklist.stream().map(LookBookPost::getPost).collect(Collectors.toList());
//
//        return postList.stream().map(lookBookMapper::toDataDto).collect(Collectors.toList());
//    }
}