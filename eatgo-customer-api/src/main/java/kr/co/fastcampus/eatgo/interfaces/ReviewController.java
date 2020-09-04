package kr.co.fastcampus.eatgo.interfaces;

import io.jsonwebtoken.Claims;
import kr.co.fastcampus.eatgo.application.ReviewService;
import kr.co.fastcampus.eatgo.domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@CrossOrigin
@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/restaurants/{restaurantId}/reviews")
    public ResponseEntity<?> create(
            Authentication authentication,
            @PathVariable("restaurantId") Long restaurantId,
            @Valid @RequestBody Review resource
    ) throws URISyntaxException {
        Claims claims = (Claims) authentication.getPrincipal();

        // 이름을 주지 않았기 때문에 resource.getName() 해서 가져올 게 없음.
        String name = claims.get("name", String.class);
        System.out.println(name);
        Integer score = resource.getScore();
        String description = resource.getDescription();

        Review review = reviewService.addReview(
                restaurantId, name, score, description);

        // 주소를 만들어주고 받는 것 까지 실행
        // 이 때 @RequestBody 에서 받은 review.getId() 를 하면 받는 게 없기 때문에 null 값이 넘어옴
        // 그러므로 이를 resource 로 바꿔주고 addReview는 따로 할당을 해주자.
        String url = "/restaurants/" + restaurantId + "/reviews/" + review.getId();
        return ResponseEntity.created(new URI(url))
                .body("{}");
    }
}
