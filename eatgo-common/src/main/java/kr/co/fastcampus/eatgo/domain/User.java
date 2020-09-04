package kr.co.fastcampus.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    @NotNull
    private Long level;

    private String password;

    // 가게 정보 넣어주기
    private Long restaurantId;

    public boolean isAdmin() {
        return level >= 300;
    }

    public boolean isActive() {
        return level > 0;
    }

    public void deactivate() {
        level = 0L;
    }

    // setRestaurantId 를 따로 만들어주었다.
    public void setRestaurantId(Long restaurantId) {
        this.level = 50L;
        this.restaurantId = restaurantId;
    }

    public boolean isRestaurantOwner() {
        return level == 50L;
    }

    // jwt를 사용하면 getAccessToken() 은 필요 없음.
//    @JsonIgnore
//    public String getAccessToken() {
//        if (password == null) {
//            return "";
//        }
//        return password.substring(0,10);
//    }
}
