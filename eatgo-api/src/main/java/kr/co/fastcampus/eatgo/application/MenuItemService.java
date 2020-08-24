package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.MenuItemRepository;
import kr.co.fastcampus.eatgo.domain.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

//    @Autowired
    private MenuItemRepository menuItemRepository;
    // RestaurantService 처럼 생성자로 넣어주기!

    @Autowired // 위에 해줘도 되고 밑에 해줘도 됨! 두 가지 방식이 있음
    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public void bulkUpdate(Long restaurantId, List<MenuItem> menuItems) {
        // 실제 데이터를 넣어준다.
        for (MenuItem menuItem : menuItems) {
            menuItem.setRestaurantId(restaurantId);
            menuItemRepository.save(menuItem);
        }
//        MenuItem menuItem = MenuItem.builder().build();
    }
}
