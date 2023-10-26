package com.techeer.fashioncloud.domain.lookbook.service;

import com.techeer.fashioncloud.domain.post.dto.request.LookBookCreateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.request.LookBookPostCreateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.response.LookBookGetResponseDto;
import com.techeer.fashioncloud.domain.post.dto.response.LookBookPostDataResponseDto;
import com.techeer.fashioncloud.domain.post.dto.response.LookBookResponseDto;
import com.techeer.fashioncloud.domain.post.dto.response.PostInfoResponseDto;
import com.techeer.fashioncloud.domain.post.entity.LookBook;
import com.techeer.fashioncloud.domain.post.entity.LookBookPost;
import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.domain.post.repository.LookBookPostRepository;
import com.techeer.fashioncloud.domain.post.repository.LookBookRepository;
import com.techeer.fashioncloud.domain.post.repository.PostRepository;
import com.techeer.fashioncloud.domain.post.service.LookBookService;
import com.techeer.fashioncloud.fixture.PostFixtures;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.techeer.fashioncloud.fixture.PostFixtures.POST_FIXTURES_1;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.techeer.fashioncloud.fixture.PostFixtures.POST_FIXTURES;
import static com.techeer.fashioncloud.fixture.UserFixtures.USER_FIXTURES;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LookBookServiceTest {

    @Mock
    private LookBookRepository lookBookRepository;
    @InjectMocks
    private LookBookService lookBookService;

    @Mock
    private LookBookPostRepository bookRepository;

    @Mock
    private PostRepository postRepository;
    private LookBook lookBook;
    private LookBook lookBook2;


    @BeforeEach
    @Transactional
    void setup() {
        lookBook = LookBook.builder()
                .id(1L)
                .user(USER_FIXTURES)
                .title("title1")
                .image("image1")
                .build();


        lookBook2 = LookBook.builder()
                .id(2L)
                .user(USER_FIXTURES)
                .title("title1")
                .image("image1")
                .build();

    }


    @Test
    @DisplayName("룩북 생성")
    public void testLookBookCreate() {


        LookBookCreateRequestDto requestDto = LookBookCreateRequestDto.builder()
                .image(lookBook.getImage())
                .title(lookBook.getTitle())
                .build();


        when(lookBookRepository.save(any())).thenReturn(lookBook);//실제 repository를 주입받을 수 없으니 save가 동작하면 임의의 값을 리턴
        LookBookResponseDto look = lookBookService.lookBookCreate(USER_FIXTURES, requestDto);


        assertEquals(LookBookResponseDto.of(lookBook).getId(), look.getId());
    }


    @Test
    @DisplayName("유저 ID로 룩북 목록 조회 테스트")
    public void testFindBookByUserId() {


        List<LookBook> expectedLookBooks = new ArrayList<>();
        expectedLookBooks.add(lookBook);
        expectedLookBooks.add(lookBook2);
        when(lookBookRepository.findByUserId(lookBook.getUser().getId())).thenReturn(expectedLookBooks);

        List<LookBookResponseDto> result = lookBookService.findLookBooksByUserId(lookBook.getUser().getId());

        // 예상 결과와 실제 결과를 비교
        assertEquals(expectedLookBooks.size(), result.size());

        for (int i = 0; i < expectedLookBooks.size(); i++) {
            LookBookResponseDto expectedDto = LookBookResponseDto.of(expectedLookBooks.get(i));
            LookBookResponseDto actualDto = result.get(i);
            assertEquals(expectedDto.getId(), actualDto.getId());
            assertEquals(expectedDto.getUserId(), actualDto.getUserId());

        }

    }

    @Test
    @DisplayName("룩북 포스트 생성")
    public void testCreateLookBookPost() {
        Long lookBookId = 1L;
        Long userId = 1L;
        UUID postId = UUID.fromString("eea26f3f-2457-49e7-9f69-3376ccdd72ee");

        Post post = POST_FIXTURES;

        LookBookPost expectedEntity = LookBookPost.builder()
                .lookBook(lookBook)
                .post(post)
                .build();

        when(lookBookRepository.findById(lookBookId)).thenReturn(Optional.ofNullable(lookBook));
        when(postRepository.findById(postId)).thenReturn(Optional.ofNullable(post));

        assertDoesNotThrow(() -> lookBookService.addPostToLookBook(userId, 1L, postId));

        // 호출한 메서드가 예상대로 동작했는지 여부를 확인 (예를 들어, 레파지토리 또는 다른 메서드 호출 여부)
        verify(lookBookRepository, times(1)).findById(lookBookId);
        verify(postRepository, times(1)).findById(postId);
        // 결과가 예상한 엔티티와 일치하는지 확인
    }



    @Test
    @DisplayName("룩북 아이디로 룩북 포스트 목록 조회 테스트")
    public void testFindLookBookById() {

        Long lookBookId=1L;
        List<Post> posts= new ArrayList<>();
        posts.add(POST_FIXTURES);
        posts.add(POST_FIXTURES_1);

        when(lookBookRepository.findById(lookBookId)).thenReturn(Optional.ofNullable(lookBook));
        when(postRepository.getLookBookPosts(lookBook)).thenReturn(posts);

        LookBookGetResponseDto result = lookBookService.getLookBookById(lookBookId);

        assertNotNull(result); // 결과가 null이 아닌지 확인

        // 결과의 필드를 확인
        assertEquals(lookBook.getTitle(), result.getTitle());
        assertEquals(lookBook.getImage(), result.getImage());
        assertEquals(lookBook.getUser().getId(), result.getUserId());
        assertEquals(lookBook.getUser().getUsername(), result.getUsername());
        assertEquals(lookBook.getCreatedAt(), result.getCreatedAt());
        assertEquals(lookBook.getUpdatedAt(), result.getUpdatedAt());

        // 포스트 목록의 길이 확인
        assertEquals(posts.size(), result.getPosts().size());


        for (int i = 0; i < posts.size(); i++) {
            Post post = posts.get(i);
            PostInfoResponseDto postDto = result.getPosts().get(i);
            assertEquals(post.getId(), postDto.getId());

        }

    }


}