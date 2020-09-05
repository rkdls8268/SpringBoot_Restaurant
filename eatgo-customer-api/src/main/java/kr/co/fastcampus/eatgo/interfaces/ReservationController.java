package kr.co.fastcampus.eatgo.interfaces;

import io.jsonwebtoken.Claims;
import kr.co.fastcampus.eatgo.application.ReservationService;
import kr.co.fastcampus.eatgo.domain.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/restaurants/{restaurantId}/reservations")
    public ResponseEntity<?> create(
            Authentication authentication,
            @PathVariable Long restaurantId,
            @RequestBody Reservation resource
    ) throws URISyntaxException {
        Claims claims = (Claims) authentication.getPrincipal();

        // userId 와 name 은 토큰값에 포함되어 있으므로 토큰값에서 가져오기
        Long userId = claims.get("userId", Long.class);
        String name = claims.get("name", String.class);

        String date = resource.getDate();
        String time = resource.getTime();
        Integer partySize = resource.getPartySize();

        reservationService.addReservation(restaurantId, userId, name, date, time, partySize);
        String url = "/restaurants/" + restaurantId + "/reservations/1";
        return ResponseEntity.created(new URI(url)).body("{}");
    }
}
