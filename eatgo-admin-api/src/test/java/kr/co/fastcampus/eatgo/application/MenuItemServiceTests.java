package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.MenuItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MenuItemServiceTests {

    private MenuItemService menuItemService;

    @Mock
    private MenuItemRepository menuItemRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        menuItemService = new MenuItemService(menuItemRepository); // before 에서 해주기
    }

    // 실제로 목록만 얻는 작업도 처리해주기
    @Test
    public void getMenuItems() {

        // setUp() 에서 처리하는 방법이 있고 가짜객체로 넣어주게끔 이름을 짓는 방법이 있다. 아래는 후자
        List<MenuItem> mockMenuItems = new ArrayList<>();
        mockMenuItems.add(MenuItem.builder()
                .name("Kimchi").build());

        given(menuItemRepository.findAllByRestaurantId(1004L))
                .willReturn(mockMenuItems);

        List<MenuItem> menuItems = menuItemService.getMenuItems(1004L);

        MenuItem menuItem = menuItems.get(0);
        assertThat(menuItem.getName(), is("Kimchi"));
    }

    @Test
    public void bulkUpdate(){
        List<MenuItem> menuItems = new ArrayList<MenuItem>();

        // 차례대로 추가, 수정, 삭제
        menuItems.add(MenuItem.builder()
                .name("Kimchi")
                .build());
        menuItems.add(MenuItem.builder()
                .id(12L)
                .name("Gukbob")
                .build());
        menuItems.add(MenuItem.builder()
                .id(1004L)
                .destroy(true)
                .build());
        menuItemService.bulkUpdate(1L, menuItems);

        // times() 는 몇 번 verify() 할 것인지를 처리
        verify(menuItemRepository, times(2)).save(any()); // 두 번의 추가 및 수정
        verify(menuItemRepository, times(1)).deleteById(eq(1004L)); // 한 번의 삭제
    }
}