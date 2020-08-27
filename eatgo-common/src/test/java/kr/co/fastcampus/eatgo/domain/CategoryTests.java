package kr.co.fastcampus.eatgo.domain;

import org.junit.jupiter.api.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CategoryTests {
    @Test
    public void creation() {
        Category category = Category.builder().name("Korean").build();

        assertThat(category.getName(), is("Korean"));
    }

}