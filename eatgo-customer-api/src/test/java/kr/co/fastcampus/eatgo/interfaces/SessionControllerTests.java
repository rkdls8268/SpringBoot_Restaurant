package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.EmailNotExistedException;
import kr.co.fastcampus.eatgo.application.PasswordWrongException;
import kr.co.fastcampus.eatgo.application.ReviewService;
import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.Review;
import kr.co.fastcampus.eatgo.domain.User;
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

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SessionController.class)
public class SessionControllerTests {

    @Autowired
    MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void createWithValidAttributes() throws Exception{
        User mockUser = User.builder()
                .password("ACCESSTOKEN")
                .build();
        given(userService.authenticate("tester@example.com", "test"))
                .willReturn(mockUser);
        mvc.perform(MockMvcRequestBuilders.post("/session")
                .contentType(MediaType.APPLICATION_JSON) // 이 내용이 JSON 타입이라는 것을 알려줌.
                .content("{\"email\":\"tester@example.com\", \"password\":\"test\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/session"))
                .andExpect(content().string("{\"accessToken\":\"ACCESSTOKE\"}"));

        verify(userService).authenticate(eq("tester@example.com"), eq("test"));
    }

    @Test
    public void createWithNotExistedEmail() throws Exception{
        // 실패하는 모양 만들어주기; given() 안해주면 badRequest() 안 나옴
        given(userService.authenticate("x@example.com", "test"))
                .willThrow(EmailNotExistedException.class);
        mvc.perform(MockMvcRequestBuilders.post("/session")
                .contentType(MediaType.APPLICATION_JSON) // 이 내용이 JSON 타입이라는 것을 알려줌.
                .content("{\"email\":\"x@example.com\", \"password\":\"test\"}"))
                .andExpect(status().isBadRequest());

        verify(userService).authenticate(eq("x@example.com"), eq("test"));
    }

    @Test
    public void createWithWrongPassword() throws Exception{
        // 실패하는 모양 만들어주기; given() 안해주면 badRequest() 안 나옴
        given(userService.authenticate("tester@example.com", "x"))
                .willThrow(PasswordWrongException.class);
        mvc.perform(MockMvcRequestBuilders.post("/session")
                .contentType(MediaType.APPLICATION_JSON) // 이 내용이 JSON 타입이라는 것을 알려줌.
                .content("{\"email\":\"tester@example.com\", \"password\":\"x\"}"))
                .andExpect(status().isBadRequest());

        verify(userService).authenticate(eq("tester@example.com"), eq("x"));
    }
}