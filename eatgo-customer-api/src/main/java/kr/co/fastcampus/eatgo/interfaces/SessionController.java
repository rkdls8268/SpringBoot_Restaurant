package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.SessionDto;
import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;
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
    private UserService userService;

    @PostMapping("/session")
    public ResponseEntity<SessionDto> create(
            @RequestBody User resource
        ) throws URISyntaxException {
        String url = "/session";
        String accessToken = "ACCESSTOKEN";

        String email = resource.getEmail();
        String password = resource.getPassword();
        userService.authenticate(email, password);

        SessionDto sessionDto = SessionDto.builder()
                .accessToken(accessToken)
                .build();
        return ResponseEntity.created(new URI(url)).body(sessionDto);
    }
}
