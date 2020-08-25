package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.ReviewService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
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
    public void create() throws Exception{
        mvc.perform(MockMvcRequestBuilders.post("/restaurants/1/reviews")
                .contentType(MediaType.APPLICATION_JSON) // 이 내용이 JSON 타입이라는 것을 알려줌.
                .content("{\"name\":\"Gain\", \"score\":3, \"description\":\"tastes good~\"}"))
                .andExpect(status().isCreated());

        verify(reviewService).addReview(ArgumentMatchers.any());
    }
}