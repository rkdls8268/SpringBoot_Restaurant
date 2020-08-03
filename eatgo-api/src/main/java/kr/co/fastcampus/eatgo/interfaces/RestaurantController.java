package kr.co.fastcampus.eatgo.interfaces;

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

    @Autowired // 컨트롤러를 만들어줄 때 Spring이 알아서 RestaurantRepository 생성해줌.
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @GetMapping("/restaurants")
    public List<Restaurant> list() {
        List<Restaurant> restaurants = restaurantRepository.findAll();

        return restaurants;
    }

    @GetMapping("/restaurants/{id}") // 바뀔 수 있는 요소 {id} 로 활용
    public Restaurant detail(@PathVariable("id") Long id) {
//        List<Restaurant> restaurants = repository.findAll();
        Restaurant restaurant = restaurantRepository.findById(id);

        List<MenuItem> menuItems = menuItemRepository.findAllByRestaurantId(id);
        // 메뉴 아이템 추가
        restaurant.setMenuItems(menuItems);

        return restaurant;
    }
}
