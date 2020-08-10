package kr.co.fastcampus.eatgo.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.core.Is.is;

public class RestaurantRepositoryImplTest {

    // JPA 를 쓰면 없앨 작업이라고 한다.
    @Test
    public void save() {
        RestaurantRepository repository = new RestaurantRepositoryImpl();
        int oldCount = repository.findAll().size();

        Restaurant restaurant = new Restaurant("BeRyong", "Seoul");
        repository.save(restaurant);

        assertEquals(1234L, restaurant.getId());
        
        int newCount = repository.findAll().size();

        int result = newCount - oldCount;

        assertEquals(1, result);
//        assertThat(result, is(1));
    }
}