package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.MenuItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// JUnit5 에서는 RunWith 대신 ExtendWith 사용
@ExtendWith(SpringExtension.class)
@WebMvcTest(MenuItemController.class)
public class MenuItemControllerTests {

    @Autowired // 우리가 직접 만들지 않아도 스프링에서 알아서 넣어 줌.
    protected MockMvc mvc;

    @MockBean
    private MenuItemService menuItemService;

    @Test
    public void bulkUpdate() throws Exception {
        mvc.perform(MockMvcRequestBuilders.patch("/restaurants/1/menuitems")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[]"))
                .andExpect(status().isOk());

        verify(menuItemService).bulkUpdate(eq(1L), any());
    }
}