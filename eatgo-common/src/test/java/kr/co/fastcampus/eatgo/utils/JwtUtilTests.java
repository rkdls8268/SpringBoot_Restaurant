package kr.co.fastcampus.eatgo.utils;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

class JwtUtilTests {

    @Test
    public void createToken() {
        JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.createToken(1004L, "John"); // 사용자 id와 이름을 토큰에 넣어줄 것이므로 인자값 주기.

        assertThat(token, containsString(".")); // token에 마침표 포함되므로 . 포함하는지 확인
    }

}