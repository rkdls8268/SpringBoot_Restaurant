package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class SessionController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/session")
    public ResponseEntity<SessionResponseDto> create(
            @RequestBody SessionRequestDto resource
        ) throws URISyntaxException {
        String email = resource.getEmail();
        String password = resource.getPassword();

        User user = userService.authenticate(email, password);
        // 임의로 토큰값을 만들어서 가져오게 했다.
//        String accessToken = user.getAccessToken();

        // JwtUtil 의 createToken() 사용
        String accessToken = jwtUtil.createToken(user.getId(), user.getName(),
                user.isRestaurantOwner() ? user.getRestaurantId() : null);

        String url = "/session";
        SessionResponseDto sessionResponseDto = SessionResponseDto.builder()
                .accessToken(accessToken).build();
        return ResponseEntity.created(new URI(url)).body(sessionResponseDto);
    }
}
