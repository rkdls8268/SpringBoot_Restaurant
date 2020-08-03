package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

// 일반적인 테스트에서는 의존 관계 주입 못함.
// 직접 이 레스토랑 서비스가 repository를 연결할 수 있도록 만들어줘야 함.

public class RestaurantServiceTest {
    private RestaurantService restaurantService;
    private RestaurantRepository restaurantRepository;
    private MenuItemRepository menuItemRepository;

    @BeforeEach // JUnit5에서 @Before 대신 사용: 모든 테스트를 사용하기 전에 한 번씩 사용해라!
    public void setUp() {
        restaurantRepository = new RestaurantRepositoryImpl();
        menuItemRepository = new MenuItemRepositoryImpl();
        restaurantService = new RestaurantService(restaurantRepository, menuItemRepository);
    }

    @Test
    public void getRestaurant() {
        Restaurant restaurant = restaurantService.getRestaurantById(1004L);
        assertThat(restaurant.getId(), is(1004L));
        MenuItem menuItem = restaurant.getMenuItems().get(0);
        assertThat(menuItem.getName(), is("Kimchi"));
    }

    @Test
    public void getRestaurants() {
        List<Restaurant> restaurants = restaurantService.getRestaurants();
        Restaurant restaurant = restaurants.get(0);
        assertThat(restaurant.getId(), is(1004L));
    }
}