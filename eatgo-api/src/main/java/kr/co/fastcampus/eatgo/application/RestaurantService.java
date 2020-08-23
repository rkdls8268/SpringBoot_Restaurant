package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    public RestaurantService(RestaurantRepository restaurantRepository,
                             MenuItemRepository menuItemRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
    }

    public Restaurant getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
        // 여기 들어올 때마다 예외처리 해줘야 하기 때문에 람다로 처리

        List<MenuItem> menuItems = menuItemRepository.findAllByRestaurantId(id);
        // 메뉴 아이템 추가
        restaurant.setMenuItems(menuItems);

        return restaurant;
    }

    public List<Restaurant> getRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants;
    }

    public Restaurant addRestaurant(Restaurant restaurant) {
        Restaurant saved = restaurantRepository.save(restaurant);
        return saved;
        // 위 두 줄 return restaurantRepository.save(restaurant); 와 같이 한 줄로도 가능
//        restaurant.setId(1234L);
//        return new Restaurant(1234L, restaurant.getName(), restaurant.getAddress());
    }

    @Transactional
    public Restaurant updateRestaurant(long id, String name, String address) {
        // TODO: update Restaurant...

        Restaurant restaurant = restaurantRepository.findById(id).orElse(null);

        restaurant.updateInformation(name, address);

        return restaurant;
    }
}

