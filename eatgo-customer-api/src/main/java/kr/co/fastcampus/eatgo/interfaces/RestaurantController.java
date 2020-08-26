package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin
@RestController
// RestController도 Component의 일종 // 우리가 RestaurantController 인스턴스를 만들어준 적은 없지만 작동했던 이유는 Spring이 이를 직접 관리헀기 때문
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

    /*
    * 입력 시 content로 넘겨준 내용을 @RequestBody 이용하여 받을 수 있음.
    * */

    @PostMapping("/restaurants")
    public ResponseEntity<?> create(@Valid @RequestBody Restaurant resource) throws URISyntaxException {
        // 이 둘은 외부에서 얻어올 것.
        Restaurant restaurant = Restaurant.builder()
                .name(resource.getName())
                .address(resource.getAddress())
                .build();
        restaurantService.addRestaurant(restaurant);

//        URI location = new URI("/restaurants/" + restaurant.getId());
        URI location = new URI("/restaurants/1234");

        return ResponseEntity.created(location).body("{}");
    }

    @PatchMapping("/restaurants/{id}")
    public String update(@PathVariable("id") Long id,
                         @Valid @RequestBody Restaurant resource) {
        String name = resource.getName();
        String address = resource.getAddress();
        restaurantService.updateRestaurant(id, name, address);
        return "{}";
    }
}
