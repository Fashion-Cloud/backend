package com.techeer.fashioncloud.domain.post.service;

import com.techeer.fashioncloud.domain.post.dto.request.PostCreateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.response.PostCreateResponseDto;
import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.domain.post.entity.PostImage;
import com.techeer.fashioncloud.domain.post.repository.PostImageRepository;
import com.techeer.fashioncloud.domain.post.repository.PostRepository;
import com.techeer.fashioncloud.domain.user.entity.User;
import com.techeer.fashioncloud.domain.user.repository.UserRepository;
import com.techeer.fashioncloud.domain.weather.util.WindChillCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    @Autowired
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostCreateResponseDto create(User loginUser, PostCreateRequestDto reqDto) {

        Double windChill = WindChillCalculator.getWindChill(reqDto.getTemperature(), reqDto.getWindSpeed());

        Post post = postRepository.save(reqDto.toEntity(loginUser, windChill));

        // Save Images
        String postImage = reqDto.getImage();
        PostImage imageEntity = PostImage.builder()
                .post(post)
                .url(postImage)
                .build();

        postImageRepository.save(imageEntity);

        return PostCreateResponseDto.of(post, loginUser);
    }
//
//    public List<WeatherPostResponse> findPostsByWeather(Integer skyCode, Integer rainfallCode, Double windChill) {
//        //TODO: 분기처리 개선
//        List<Post> postEntityList = new ArrayList<>();
//
//        //맑음
//        if (skyCode == SkyStatus.CLEAR
//                & rainfallCode == RainfallType.CLEAR) {
//            postEntityList = postRepository.findNoRainfallPosts(windChill, SkyStatus.clearCodeList, RainfallType.clearCodeList);
//        }
//        //흐림
//        else if (SkyStatus.cloudyCodeList.contains(skyCode)
//                & rainfallCode == RainfallType.CLEAR) {
//            postEntityList = postRepository.findNoRainfallPosts(windChill, SkyStatus.cloudyCodeList, RainfallType.clearCodeList);
//        }
//        //비
//        else if (RainfallType.RainyCodeList.contains(rainfallCode)) {
//            postEntityList = postRepository.findRainfallPosts(windChill, RainfallType.RainyCodeList);
//        }
//        //눈
//        else if (RainfallType.SnowyCodeList.contains(rainfallCode)) {
//            postEntityList = postRepository.findRainfallPosts(windChill, RainfallType.SnowyCodeList);
//        } else {
//            throw new RuntimeException("날씨 정보 오류");
//        }
//
//        return postMapper.toPostDtoList(postEntityList);
//    }
//
//    public Post update(UUID id, PostUpdateRequestDto dto) {
//        Post entity = postRepository.findById(id).get();
//        entity.setName(dto.getName());
//        entity.setImage(dto.getImage());
//        entity.setReview(dto.getReview());
//        return entity;
//    }
//
//
//    public List<PostCreateResponseDto> pageList(Pageable pageable) {
//        Page<Post> postList = postRepository.findAll(pageable);
//        return postMapper.toDtoPageList(postList).getContent();
//    }
//
//
////     public List<PostResponseDto> findAllPosts() {
////     return postRepository.findAll().stream()
////              .map(PostResponseDto::fromEntity)
////              .collect(Collectors.toList());
////    }
//
//    public Post findPostById(UUID id) {
//        return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException());
//    }
//
//    public List<Post> findPostByUserId(UUID id) {
//        return postRepository.findByUserId(id);
//    }
//
//    public void deleteRequestById(UUID id) {
//        if (!postRepository.existsById(id)) {
//            throw new PostNotFoundException();
//        }
//        postRepository.deleteById(id);
//    }
}