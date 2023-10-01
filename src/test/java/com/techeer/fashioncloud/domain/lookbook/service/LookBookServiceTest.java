package com.techeer.fashioncloud.domain.lookbook.service;

import com.techeer.fashioncloud.domain.post.dto.request.LookBookCreateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.request.LookBookPostCreateRequestDto;
import com.techeer.fashioncloud.domain.post.dto.response.LookBookPostDataResponseDto;
import com.techeer.fashioncloud.domain.post.entity.LookBook;
import com.techeer.fashioncloud.domain.post.entity.LookBookPost;
import com.techeer.fashioncloud.domain.post.entity.Post;
import com.techeer.fashioncloud.domain.post.repository.BookRepository;
import com.techeer.fashioncloud.domain.post.repository.LookBookRepository;
import com.techeer.fashioncloud.domain.post.repository.PostRepository;
import com.techeer.fashioncloud.domain.post.service.LookBookService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static com.techeer.fashioncloud.fixture.PostFixtures.POST_FIXTURES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LookBookServiceTest {

    @Mock
    private LookBookRepository lookBookRepository;
    @InjectMocks
    private LookBookService lookBookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PostRepository postRepository;
    private LookBook lookBook;

    @Mock
    private LookBookMapper lookBookMapper;

    @BeforeEach
    @Transactional
    void setup() {
        lookBook = LookBook.builder()
                .id(UUID.fromString("0fb5a4a7-4743-4dc3-8a24-41cf9aef15ab"))
                .userId(UUID.fromString("eea26f3f-2457-49e7-9f69-3376ccdd72ee"))
                .title("title")
                .image("image")
                .build();

    }


    @Test
    @DisplayName("룩북 생성")
    public void testLookBookCreate() {
        LookBookCreateRequestDto requestDto=LookBookCreateRequestDto.builder()
                .image(lookBook.getImage())
                .title(lookBook.getTitle())
                .userId(lookBook.getUserId())
                .build();


        when(bookRepository.save(any())).thenReturn(lookBook);//실제 repository를 주입받을 수 없으니 save가 동작하면 임의의 값을 리턴
        LookBook look = lookBookService.lookBookCreate(requestDto);
        assertEquals(look,lookBook);


    }


    @Test
    @DisplayName("유저 ID로 룩북 목록 조회 테스트")
    public void testFindBookByUserId() {
        List<LookBook> expectedLookBooks = new ArrayList<>(); // 여기에 예상되는 LookBook 목록을 설정
        when(bookRepository.findByUserId(lookBook.getUserId())).thenReturn(expectedLookBooks);
        assertEquals(expectedLookBooks, lookBookService.findBookByUserId(lookBook.getUserId()));
    }


    @Test
    @DisplayName("룩북 포스트 생성")
    public void testCreateLookBookPost() {
        UUID lookBookId = UUID.randomUUID(); // 임의의 룩북 ID 생성
        UUID postId = UUID.fromString("eea26f3f-2457-49e7-9f69-3376ccdd72ee"); // 임의의 포스트 ID 생성
        LookBookPostCreateRequestDto requestDto = LookBookPostCreateRequestDto.builder()
                .lookBookId(lookBookId)
                .postId(postId)
                .build();

        Post post = POST_FIXTURES;

        LookBookPost expectedEntity = LookBookPost.builder()
                .lookBook(lookBook)
                .post(post)
                .build();

        when(bookRepository.findById(lookBookId)).thenReturn(Optional.ofNullable(lookBook));
        when(postRepository.findById(postId)).thenReturn(Optional.ofNullable(post));
        when(lookBookRepository.save(any())).thenReturn(expectedEntity);

        LookBookPost createdEntity = lookBookService.lookBookPostCreate(requestDto);

        assertEquals(expectedEntity, createdEntity);
    }

    @Test
    @DisplayName("룩북 아이디로 룩북 포스트 목록 조회 테스트")
    public void testFindLookBookById() {
        // Arrange
        UUID lookBookId = UUID.randomUUID();


        Post post = POST_FIXTURES;

        LookBookPost expectedLookBookPost1 = LookBookPost.builder()
                .lookBook(lookBook)
                .post(post)
                .build();

        LookBookPost expectedLookBookPost2 = LookBookPost.builder()
                .lookBook(lookBook)
                .post(post)
                .build();

        List<LookBookPost> expectedLookBookPosts = new ArrayList<>();
        expectedLookBookPosts.add(expectedLookBookPost1);
        expectedLookBookPosts.add(expectedLookBookPost2);

        when(bookRepository.findById(lookBookId)).thenReturn(Optional.ofNullable(lookBook));
        when(lookBookRepository.findByLookBook(lookBook)).thenReturn(expectedLookBookPosts);

        List<LookBookPostDataResponseDto> result = lookBookService.findLookBookById(lookBookId);


        assertEquals(expectedLookBookPosts.size(), result.size());

    }


}