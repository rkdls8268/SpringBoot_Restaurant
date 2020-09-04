package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.ReviewService;
import kr.co.fastcampus.eatgo.domain.Review;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// JUnit5 에서는 RunWith 대신 ExtendWith 사용
@ExtendWith(SpringExtension.class)
@WebMvcTest(ReviewController.class)
public class ReviewControllerTests {

    @Autowired
    MockMvc mvc;

    @MockBean
    private ReviewService reviewService;

    @Test
    public void createWithValidAttributes() throws Exception{
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjE2LCJuYW1lIjoidGVzdGVyIn0.T45eNQRk5f6ymbaMZBiym-QgsdizureCm7-rliY6Y2s";

        given(reviewService.addReview(1004L,"John", 3, "tastes good~")).willReturn(
            Review.builder()
                    .id(1004L)
                    .build()
        );
        mvc.perform(MockMvcRequestBuilders.post("/restaurants/1/reviews")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON) // 이 내용이 JSON 타입이라는 것을 알려줌.
                .content("{\"score\":3, \"description\":\"tastes good~\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/restaurants/1/reviews/1004"));
                // location 에서 데이터 값이 제대로 잘 들어갔는지 url 확인

        verify(reviewService).addReview(1004L,"John", 3, "tastes good");
    }

    @Test
    public void createWithInvalidAttributes() throws Exception{
        mvc.perform(MockMvcRequestBuilders.post("/restaurants/1/reviews")
                .contentType(MediaType.APPLICATION_JSON) // 이 내용이 JSON 타입이라는 것을 알려줌.
                .content("{}"))
                .andExpect(status().isBadRequest());

        // 한번도 호출이 안되게끔 하기 위해 never() 사용
        verify(reviewService, never()).addReview(any(), any(), any(), any());
    }
}