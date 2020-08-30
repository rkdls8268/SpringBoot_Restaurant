package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class UserServiceTests {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    public void getUsers() {
        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(User.builder()
                .email("tester@example.com")
                .name("tester")
                .level(300L).build());
        given(userRepository.findAll()).willReturn(mockUsers);

        List<User> users = userService.getUsers();

        User user = users.get(0);
        assertThat(user.getName(), is("tester"));
    }

    @Test
    public void addUser() {
        String email = "admin@example.com";
        String name = "admin";

        User mockUser = User.builder()
                .email(email)
                .name(name)
                .build();

        given(userRepository.save(any())).willReturn(mockUser);

        User user = userService.addUser(email, name);

        assertThat(user.getName(), is(name));
    }

    @Test
    public void updateUser() {
        Long id = 1004L;
        String email = "admin@example.com";
        String name = "Superman"; // 원래는 admin 이었다가 변경 시킬 name
        Long level = 300L;

        User mockUser = User.builder()
                .id(id)
                .email(email)
                .name("admin")
                .level(1L).build();
        given(userRepository.findById(id)).willReturn(Optional.of(mockUser));
        // RestaurantServiceTests 에서도 이렇게 작성하고 repository 에 메서드 추가해주었는데 오류 안남
        // 왜 여기서는 repository 에 메서드 지워줘야 오류가 안날까?

        User user = userService.updateUser(id, email, name, level);

        verify(userRepository).findById(eq(id));

        assertThat(user.getName(), is("Superman"));
        assertThat(user.isAdmin(), is(true));
    }

    @Test
    public void deleteUser() {
        Long id = 1004L;
        String email = "admin@example.com";
        String name = "admin"; // 원래는 admin 이었다가 변경 시킬 name
        Long level = 300L;

        User mockUser = User.builder()
                .id(id)
                .email(email)
                .name("admin")
                .level(1L).build();
        given(userRepository.findById(id)).willReturn(Optional.of(mockUser));

        User user = userService.deleteUser(1004L);

        verify(userRepository).findById(1004L);

        assertThat(user.isAdmin(), is(false));
        assertThat(user.isActive(), is(false));
    }
}