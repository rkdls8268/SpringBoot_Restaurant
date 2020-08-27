package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

// 일반적인 테스트에서는 의존 관계 주입 못함.
// 직접 이 레스토랑 서비스가 repository를 연결할 수 있도록 만들어줘야 함.

public class RestaurantServiceTest {
    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @BeforeEach // JUnit5에서 @Before 대신 사용: 모든 테스트를 사용하기 전에 한 번씩 사용해라!
    public void setUp() {
//        restaurantRepository = new RestaurantRepositoryImpl();
//        menuItemRepository = new MenuItemRepositoryImpl();
        MockitoAnnotations.initMocks(this);
        // Mock 어노테이션이 붙어있는 것에 올바르게 객체를 선언해 줌.

        MockRestaurantRepository();
        MockMenuItemRepository();
        MockReviewRepository();
        restaurantService = new RestaurantService(restaurantRepository, menuItemRepository, reviewRepository);
    }

    private void MockMenuItemRepository() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(MenuItem.builder()
                .name("Kimchi")
                .build());
        given(menuItemRepository.findAllByRestaurantId(1004L)).willReturn(menuItems);
    }

    private void MockRestaurantRepository() {
        List<Restaurant> restaurants = new ArrayList<>();
        // builder 패턴
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .address("Seoul")
                .name("Bob zip")
//                .menuItems(new ArrayList<>())
                .build();
//        Restaurant restaurant = new Restaurant(1004L, "Bob zip", "Seoul");
        restaurants.add(restaurant);
        given(restaurantRepository.findAllByAddressContaining("Seoul")).willReturn(restaurants);

        given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));

        given(restaurantRepository.save(any())).willReturn(restaurant);
    }

    private void MockReviewRepository() {
        List<Review> reviews = new ArrayList<>();
        reviews.add(Review.builder()
            .name("BeRyong")
            .score(1)
            .description("Bad")
            .build());

        given(reviewRepository.findAllByRestaurantId(1004L))
                .willReturn(reviews);
    }

    @Test
    public void getRestaurantWithExisted() {
        Restaurant restaurant = restaurantService.getRestaurantById(1004L);

        // 상세 페이지에서 실행 
        verify(menuItemRepository).findAllByRestaurantId(eq(1004L));
        verify(reviewRepository).findAllByRestaurantId(eq(1004L));

        assertThat(restaurant.getId(), is(1004L));
        MenuItem menuItem = restaurant.getMenuItems().get(0);
        assertThat(menuItem.getName(), is("Kimchi"));

        Review review = restaurant.getReviews().get(0);
        assertThat(review.getDescription(), is("Bad"));
    }

    @Test
    public void getRestaurantWithNotExisted() {
        assertThrows(RestaurantNotFoundException.class,
                () -> restaurantService.getRestaurantById(404L));
//        restaurantService.getRestaurantById(404L);
    }

    @Test
    public void getRestaurants() {
        String region = "Seoul";
        List<Restaurant> restaurants = restaurantService.getRestaurants(region);
        Restaurant restaurant = restaurants.get(0);
        assertThat(restaurant.getId(), is(1004L));
    }

    @Test
    public void addRestaurants() { // 강의에는 addRestaurant라고 되어 있음.
        given(restaurantRepository.save(any())).will(invocation -> {
            Restaurant restaurant = invocation.getArgument(0);
            restaurant.setId(1234L);
            return restaurant;
        });

        // 임의로 데이터를 넣고 있지만 id 같은 경우 적어놓지 않아도 알아서 들어갔으면 좋겠다!
        // id 값 삭제 -> 2개의 인자값만 가지는 생성자 만들어주기
        Restaurant restaurant = Restaurant.builder()
                .name("BeRyong")
                .address("Busan")
                .build();

        // saved 는 개선 여지가 보임,,! will(invocaton => {}) 으로 바꿈 아래 코드는 삭제해도 됨.
//        Restaurant saved = Restaurant.builder()
//                .id(1234L)
//                .name("BeRyong")
//                .address("Busan")
//                .build();

        Restaurant created = restaurantService.addRestaurant(restaurant);
        assertThat(created.getId(), is(1234L));
    }

    @Test
    public void updateRestaurant() {
        Restaurant restaurant = Restaurant.builder()
                .id(1004L)
                .name("Bob zip")
                .address("Seoul")
                .build();

        given(restaurantRepository.findById(1004L))
                .willReturn(Optional.of(restaurant));

        restaurantService.updateRestaurant(1004L, "sul zip", "Busan");

        assertThat(restaurant.getName(), is("sul zip"));
        assertThat(restaurant.getAddress(), is("Busan"));

    }
}