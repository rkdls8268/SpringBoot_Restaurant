package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.MenuItemRepository;
import kr.co.fastcampus.eatgo.domain.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<MenuItem> getMenuItems(Long restaurantId) {
//        List<MenuItem> menuItems = new ArrayList<>();
//        menuItems.add(MenuItem.builder()
//                .name("Kimchi").build());
        // 위 내용이 가짜 객체를 만들어주는 곳에 넣어주면 됨.

        return menuItemRepository.findAllByRestaurantId(restaurantId);
    }

    public void bulkUpdate(Long restaurantId, List<MenuItem> menuItems) {
        // 실제 데이터를 넣어준다.
        for (MenuItem menuItem : menuItems) {
            update(restaurantId, menuItem);
        }
//        MenuItem menuItem = MenuItem.builder().build();
    }

    // bulkUpdate() 와 분리
    public void update(Long restaurantId, MenuItem menuItem) {
        // 삭제 여부 확인
        if (menuItem.isDestroy()) {
            menuItemRepository.deleteById(menuItem.getId());
            return;
        }
        // 아닌 경우에는 추가
        menuItem.setRestaurantId(restaurantId);
        menuItemRepository.save(menuItem);
    }
}
