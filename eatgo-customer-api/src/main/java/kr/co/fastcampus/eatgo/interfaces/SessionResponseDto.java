package kr.co.fastcampus.eatgo.interfaces;

import lombok.Builder;
import lombok.Data;

// 순수하게 데이터만 가지고 있으므로
@Data
@Builder
public class SessionResponseDto {

    private String accessToken;
}
