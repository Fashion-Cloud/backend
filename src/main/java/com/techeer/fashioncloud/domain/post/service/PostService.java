package com.techeer.fashioncloud.domain.post.service;

import com.techeer.fashioncloud.domain.post.constants.PostKeyPrefix;
import com.techeer.fashioncloud.domain.post.dto.request.PostCreateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.request.PostGetRequestDto;
import com.techeer.fashioncloud.domain.post.dto.request.PostUpdateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.response.PostCreateResponseDto;
import com.techeer.fashioncloud.domain.post.dto.response.PostInfoResponseDto;
import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.domain.post.entity.PostImage;
import com.techeer.fashioncloud.domain.post.exception.PostNotFoundException;
import com.techeer.fashioncloud.domain.post.repository.PostImageRepository;
import com.techeer.fashioncloud.domain.post.repository.PostRepository;
import com.techeer.fashioncloud.domain.user.entity.User;
import com.techeer.fashioncloud.domain.user.repository.UserRepository;
import com.techeer.fashioncloud.domain.weather.enums.RainfallType;
import com.techeer.fashioncloud.domain.weather.enums.SkyStatus;
import com.techeer.fashioncloud.global.dto.PaginatedResponse;
import com.techeer.fashioncloud.global.util.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final UserRepository userRepository;
    private final CacheService cacheService;

    @Transactional
    public PostCreateResponseDto create(User loginUser, PostCreateRequestDto dto) {

        Post post = postRepository.save(dto.toEntity(loginUser));
        savePostImage(post, dto.getImage());

        return PostCreateResponseDto.of(post, loginUser);
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<PostInfoResponseDto> getPostsByWeather(Pageable pageReqDto, PostGetRequestDto reqDto) {
        Page<Post> postPage = postRepository.findPostsByWeather(
                SkyStatus.getGroup(reqDto.getSkyStatus()),
                RainfallType.getGroup(reqDto.getRainfallType()),
                reqDto.getMinWindChill(),
                reqDto.getMaxWindChill(),
                pageReqDto);

        return PaginatedResponse.of(postPage, PostInfoResponseDto::of);
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<PostInfoResponseDto> getFollowTimeline(User loginUser, Pageable pageReqDto) {
        Page<Post> postPage = postRepository.getFollowTimeline(loginUser, pageReqDto);

        return PaginatedResponse.of(postPage, PostInfoResponseDto::of);
    }

    @Transactional
    public Post update(UUID id, PostUpdateRequestDto dto) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);

        return post.update(dto.getTitle(), dto.getImage(), dto.getReview());
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<PostInfoResponseDto> getPosts(Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);

        return PaginatedResponse.of(postPage, PostInfoResponseDto::of);
    }

    public PostInfoResponseDto getPostInfoById(UUID id) {

        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        if (cacheService.hasKey(PostKeyPrefix.VIEW + id)) {
            Integer viewCount = Integer.parseInt(cacheService.getRawValueByKey(PostKeyPrefix.VIEW + id));
            return PostInfoResponseDto.getDtoWithViewCount(post, viewCount);
        }

        return PostInfoResponseDto.of(post);
    }

    public List<PostInfoResponseDto> findPostByUserId(Long id) {
        List<Post> posts = postRepository.findByUserId(id);
        return posts.stream().map(PostInfoResponseDto::of).toList();
    }

    public void deletePostById(UUID id) {
        if (!postRepository.existsById(id)) {
            throw new PostNotFoundException();
        }
        postRepository.deleteById(id);
    }

    public void updateViewCount(User loginUser, UUID postId) {
        String key = PostKeyPrefix.VIEW + postId;
        if (isLoginUserPost(loginUser, postId)) {
            return;
        }
        if (cacheService.hasKey(key)) {
            cacheService.increaseByKey(key);
            return;
        }
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        cacheService.setValueByKey(key, String.valueOf(post.getViewCount() + 1));
    }

    @Cacheable(key = PostKeyPrefix.VIEW + "#postId", value = "postViewCount")
    public Integer getViewCount(UUID postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        return post.getViewCount();
    }

    private void savePostImage(Post post, String url) {
        PostImage postImage = PostImage.builder()
                .post(post)
                .url(url)
                .build();

        postImageRepository.save(postImage);
    }

    private boolean isLoginUserPost(User loginUser, UUID postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        Long authorId = post.getUser().getId();
        return authorId.equals(loginUser.getId());
    }
}
