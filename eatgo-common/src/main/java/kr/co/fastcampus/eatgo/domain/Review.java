package kr.co.fastcampus.eatgo.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    @Setter
    private Long restaurantId;

//    @NotEmpty
    private String name;

    // max 는 이 변수의 최댓값, min 은 이 변수의 최솟값
//    @Max(5)
//    @Min(0)
    @NotNull
    private Integer score;

    @NotEmpty
    private String description;
}
