package com.techeer.fashioncloud.domain.post.service;

import com.techeer.fashioncloud.domain.post.dto.request.PostCreateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.request.PostUpdateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.response.PostCreateResponseDto;
import com.techeer.fashioncloud.domain.post.dto.response.PostInfoResponseDto;
import com.techeer.fashioncloud.domain.post.dto.response.WeatherPostResponse;
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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostCreateResponseDto create(User loginUser, PostCreateRequestDto dto) {

        Post post = postRepository.save(dto.toEntity(loginUser));
        savePostImage(post, dto.getImage());

        return PostCreateResponseDto.of(post, loginUser);
    }

    // TODO 페이지네이션
    @Transactional(readOnly = true)
    public List<WeatherPostResponse> getPostsByWeather(SkyStatus skyStatus, RainfallType rainfallType, Double windChill) {
        List<Post> postList = postRepository.findPostsByWeather(
                SkyStatus.getGroup(skyStatus),
                RainfallType.getGroup(rainfallType),
                windChill);

        return postList.stream().map(WeatherPostResponse::of).toList();
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

    public PostInfoResponseDto findPostById(UUID id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);

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

    private void savePostImage(Post post, String url) {
        PostImage postImage = PostImage.builder()
                .post(post)
                .url(url)
                .build();

        postImageRepository.save(postImage);
    }
}