package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// JUnit5 에서는 RunWith 대신 ExtendWith 사용
@ExtendWith(SpringExtension.class)
@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {

    @Autowired // 우리가 직접 만들지 않아도 스프링에서 알아서 넣어 줌.
    protected MockMvc mvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void list() throws Exception { // perform 에 대한 예외처리
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(Restaurant.builder()
                .id(1004L)
                .categoryId(1L)
                .name("Bob zip")
                .address("Seoul")
                .build());
        given(restaurantService.getRestaurants()).willReturn(restaurants);
        // JUnit5 에서는 이렇게 해줘야함...?
//        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/restaurants");
        mvc.perform(MockMvcRequestBuilders.get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"Bob zip\"")
                ));
    }

    @Test
    public void detailWithExisted() throws Exception {
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .categoryId(1L)
                .name("Bob zip")
                .address("Seoul")
                .build();

        // menuItem 과 review 는 따로 처리해줄 것

        given(restaurantService.getRestaurantById(1004L)).willReturn(restaurant);

        mvc.perform(MockMvcRequestBuilders.get("/restaurants/1004")) // 요청하는 api
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"Bob zip\"")
                ));
    }

    @Test
    public void detailWithNotExisted() throws Exception {
        given(restaurantService.getRestaurantById(404L))
                .willThrow(new RestaurantNotFoundException(404L));
        mvc.perform(MockMvcRequestBuilders.get("/restaurants/404"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("{}"));
    }

    @Test
    public void createWithValidData() throws Exception {
//        given(restaurantService.addRestaurant(ArgumentMatchers.any())).will(invocation -> {
//            Restaurant restaurant = invocation.getArgument(0);
//            return new Restaurant(1234L, restaurant.getName(), restaurant.getAddress());
//        });

        Restaurant restaurant = Restaurant.builder()
                .id(1234L)
                .categoryId(1L)
                .name("BeRyong")
                .address("Busan")
                .build();
        mvc.perform(MockMvcRequestBuilders.post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON) // 이 내용이 JSON 타입이라는 것을 알려줌.
                .content("{\"categoryId\": 1, \"name\":\"BeRyong\", \"address\":\"Busan\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "/restaurants/1234"))
                .andExpect(content().string("{}"));

        verify(restaurantService).addRestaurant(ArgumentMatchers.any());
        // 제대로 된 객체만 넣으면 실행되게 처리
    }

    @Test
    public void createWithInvalidData() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON) // 이 내용이 JSON 타입이라는 것을 알려줌.
                .content("{\"categoryId\": 1, \"name\":\"\", \"address\":\"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateWithValidData() throws Exception {
        mvc.perform(MockMvcRequestBuilders.patch("/restaurants/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"categoryId\": 1, \"name\": \"JOKER Bar\", \"address\": \"Busan\"}"))
                .andExpect(status().isOk());

        verify(restaurantService).updateRestaurant(1004L, "JOKER Bar", "Busan");
    }

    @Test
    public void updateWithInvalidData() throws Exception {
        mvc.perform(MockMvcRequestBuilders.patch("/restaurants/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"categoryId\": 1, \"name\": \"\", \"address\": \"\"}"))
                .andExpect(status().isBadRequest());
    }

//    @Test
//    public void updateWithoutName() throws Exception {
//        mvc.perform(MockMvcRequestBuilders.patch("/restaurants/1004")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"name\": \"\", \"address\": \"Busan\"}"))
//                .andExpect(status().isBadRequest());
//    }
}