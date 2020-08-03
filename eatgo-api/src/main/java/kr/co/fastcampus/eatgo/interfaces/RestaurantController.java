package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.MenuItemRepository;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import kr.co.fastcampus.eatgo.domain.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // RestController도 Component의 일종 // 우리가 RestaurantController 인스턴스를 만들어준 적은 없지만 작동했던 이유는 Spring이 이를 직접 관리헀기 때문
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public List<Restaurant> list() {
        List<Restaurant> restaurants = restaurantService.getRestaurants();

        return restaurants;
    }

    @GetMapping("/restaurants/{id}") // 바뀔 수 있는 요소 {id} 로 활용
    public Restaurant detail(@PathVariable("id") Long id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        // 기본 정보 + 메뉴 정보
        // 아래의 복잡한 처리들이 한꺼번에 일어나는 새로운 객체 개념

        return restaurant;
    }
}
