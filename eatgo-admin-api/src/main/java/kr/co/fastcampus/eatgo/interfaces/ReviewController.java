package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.ReviewService;
import kr.co.fastcampus.eatgo.domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

//    @PostMapping("/restaurants/{restaurantId}/reviews")
//    public ResponseEntity<?> create(
//            @PathVariable("restaurantId") Long restaurantId,
//            @Valid @RequestBody Review resource
//    ) throws URISyntaxException {
//        Review review = reviewService.addReview(restaurantId, resource);
//
//        // 주소를 만들어주고 받는 것 까지 실행
//        // 이 때 @RequestBody 에서 받은 review.getId() 를 하면 받는 게 없기 때문에 null 값이 넘어옴
//        // 그러므로 이를 resource 로 바꿔주고 addReview는 따로 할당을 해주자.
//        String url = "/restaurants/" + restaurantId + "/reviews/" + review.getId();
//        return ResponseEntity.created(new URI(url))
//                .body("{}");
//    }

    // 전체 리뷰 조회 - 매핑
    @GetMapping("/reviews")
    public List<Review> list() {
        return reviewService.getReviews();
    }
}
