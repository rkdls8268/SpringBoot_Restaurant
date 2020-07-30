package kr.co.fastcampus.eatgo.domain;

import org.junit.jupiter.api.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantTests { // 여기는 또 public 지정 안해줘도 되네..?
    @Test // Test annotation 추가 시 테스트 사용 가능
    public void creation() {
        Restaurant restaurant = new Restaurant(1004L, "Bob zip", "Seoul");
        assertThat(restaurant.getId(), is(1004L));
        assertThat(restaurant.getName(), is("Bob zip")); // is.is or CoreMatcher.is 사용
        assertThat(restaurant.getAddress(), is("Seoul"));
    }

    @Test
    public void information() {
        Restaurant restaurant = new Restaurant(1004L, "Bob zip", "Seoul");
        assertThat(restaurant.getInfo(), is("Bob zip in Seoul"));
    }
}