package com.techeer.fashioncloud.domain.post.service;

import com.techeer.fashioncloud.domain.post.dto.request.LookBookCreateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.response.LookBookGetResponseDto;
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
import com.techeer.fashioncloud.global.error.ErrorCode;
import com.techeer.fashioncloud.global.error.exception.BusinessException;
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
        if (lookBookRepository.existsByTitle(dto.getTitle())) {
            throw new BusinessException(ErrorCode.LOOK_BOOK_TITLE_DUPLICATED);
        }
        LookBook lookBook = LookBook.builder()
                .user(loginUser)
                .title(dto.getTitle())
                .image(dto.getImage())
                .build();
        return LookBookResponseDto.of(lookBookRepository.save(lookBook));
    }

    @Transactional(readOnly = true)
    public List<LookBookResponseDto> findLookBooksByUserId(Long userId) {
        return lookBookRepository.findByUserId(userId)
                .stream()
                .map(LookBookResponseDto::of)
                .toList();
    }

    @Transactional
    public void addPostToLookBook(Long userId, Long lookBookId, UUID postId) {
        LookBook lookBook = lookBookRepository.findById(lookBookId).orElseThrow(() -> new LookBookNotFoundException());
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);

        // TODO 이미 추가했는지 체크 (민지님)
        if (!hasAccess(userId, lookBook.getUser().getId())) {
            throw new BusinessException(ErrorCode.PERMISSION_DENIED);
        }

        lookBookPostRepository.save(
                LookBookPost.builder()
                        .lookBook(lookBook)
                        .post(post).build());
    }

    public LookBookGetResponseDto getLookBookById(Long id) {
        LookBook lookBook = lookBookRepository.findById(id).orElseThrow(LookBookNotFoundException::new);
        List<Post> posts = postRepository.getLookBookPosts(lookBook);

        return LookBookGetResponseDto.of(lookBook, posts);
    }

    private boolean hasAccess(Long loginUserId, Long ownerId) {
        return loginUserId.equals(ownerId);
    }

}