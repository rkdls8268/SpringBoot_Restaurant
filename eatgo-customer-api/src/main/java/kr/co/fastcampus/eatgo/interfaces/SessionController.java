package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.UserService;
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
    public ResponseEntity<SessionResponseDto> create(
            @RequestBody SessionRequestDto resource
        ) throws URISyntaxException {
        String url = "/session";
        String accessToken = "ACCESSTOKEN";

        String email = resource.getEmail();
        String password = resource.getPassword();
        userService.authenticate(email, password);

        SessionResponseDto sessionResponseDto = SessionResponseDto.builder()
                .accessToken(accessToken).build();
        return ResponseEntity.created(new URI(url)).body(sessionResponseDto);
    }
}
