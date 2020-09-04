package kr.co.fastcampus.eatgo.domain;

import org.junit.jupiter.api.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserTests {

    @Test
    public void creation() {
        User user = User.builder()
                .email("tester@example.com")
                .name("tester")
                .level(300L)
                .build();

        assertThat(user.getName(), is("tester"));
        assertThat(user.isAdmin(), is(true));
        assertThat(user.isActive(), is(true));

        user.deactivate();
        assertThat(user.isActive(), is(false));

    }

    @Test
    public void restaurantOwner() {
        User user = User.builder()
                .level(1L).build();
        assertThat(user.isRestaurantOwner(), is(false));

        user.setRestaurantId(1004L);
        assertThat(user.isRestaurantOwner(), is(true));
        assertThat(user.getRestaurantId(), is(1004L));
    }

//    @Test
//    public void accessTokenWithPassword() {
//        User user = User.builder()
//                .password("ACCESSTOKEN")
//                .build();
//        assertThat(user.getAccessToken(), is("ACCESSTOKE"));
//    }
//
//    @Test
//    public void accessTokenWithoutPassword() {
//        User user = new User();
//
//        assertThat(user.getAccessToken(), is(""));
//    }
}