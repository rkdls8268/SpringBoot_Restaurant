package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;
import org.apache.catalina.Group;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void list() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(User.builder()
                .email("tester@exmaple.com")
                .name("tester")
                .level(1L).build());
        given(userService.getUsers()).willReturn(users);

        mvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("tester")));
    }

    @Test
    public void create() throws Exception {
        // 회원가입과는 다름. 주로 가게 업소 사장님들의 계정을 만들어주거나
        // 관리자를 만들어 줄 때 주로 사용
        String email = "admin@example.com";
        String name = "admin";

        User user = User.builder()
                .email(email)
                .name(name)
                .build();
        given(userService.addUser(email, name)).willReturn(user);

        mvc.perform(MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"admin@example.com\", \"name\":\"admin\"}"))
                .andExpect(status().isCreated());

        verify(userService).addUser(email, name);
    }

    @Test
    public void update() throws Exception {
        mvc.perform(MockMvcRequestBuilders.patch("/users/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"admin@example.com\"," +
                        " \"name\":\"admin\", \"level\":300}"))
                .andExpect(status().isOk());

        Long id = 1004L;
        String email = "admin@example.com";
        String name = "admin";
        Long level = 300L;

//        User user = User.builder()
//                .id(id)
//                .email(email)
//                .name(name)
//                .build();
//        given(userService.updateUser(id, email, name, level)).willReturn(user);

        verify(userService).updateUser(eq(id), eq(email), eq(name), eq(level));
    }

}