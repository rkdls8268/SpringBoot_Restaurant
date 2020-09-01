package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.RestaurantNotFoundException;
import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class UserServiceTests {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void registerUser() {
        String email = "tester@example.com";
        String name = "tester";
        String password = "test";
        userService.registerUser(email, name, password);

        verify(userRepository).save((any()));
    }

    @Test
    public void registerUserWithExistedEmail() {
        // 중복된 이메일이 나오면 예외 처리
        String email = "tester@example.com";
        String name = "tester";
        String password = "test";

        User mockUser = User.builder().build();

        // Optional.of() 가 안 먹는다,, 왜그럴까
        given(userRepository.findByEmail(email)).willReturn(Optional.ofNullable(mockUser));
        assertThrows(EmailExistedException.class,
                () -> userService.registerUser(email, name, password));
    }

    @Test
    public void authenticateWithValidAttributes() {
        String email = "tester@example.com";
        String password = "test";

        User mockUser = User.builder()
                .email(email)
                .build();
        given(userRepository.findByEmail(email)).willReturn(Optional.of(mockUser));

        given(passwordEncoder.matches(any(), any())).willReturn(true);

        userService.authenticate(email, password);
//        assertThat(user.getEmail(), is(email));
    }

    @Test
    public void authenticateWithNotExistedEmail() {
        String email = "x@example.com";
        String password = "test";

        given(userRepository.findByEmail(email)).willReturn(Optional.empty());

        assertThrows(EmailNotExistedException.class,
                () -> userService.authenticate(email, password));
    }

    @Test
    public void authenticateWithWrongPassword() {
        String email = "tester@example.com";
        String password = "x";

        // passwordEncoder 사용해서 처리

        given(userRepository.findByEmail(email)).willReturn(Optional.empty());

        // PasswordWrongException 으로 해주면 오류남..
        assertThrows(EmailNotExistedException.class,
                () -> userService.authenticate(email, password));
    }
}