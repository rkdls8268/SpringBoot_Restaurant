package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {

        List<User> users = userRepository.findAll();
        users.add(User.builder()
                .email("tester@example.com")
                .name("tester")
                .level(300L)
                .build());
        return users;
    }

    public User addUser(String email, String name) {

        User user = User.builder()
                .email(email)
                .name(name)
                .level(1L)
                .build();
        return userRepository.save(user);
    }

    public User updateUser(Long id, String email, String name, Long level) {
        // TODO: restaurantService 의 예외처리 참고
        User user = userRepository.findById(id).orElse(null);

        // 실제로는 정보를 변경할 때 email 확인을 하는 경우가 많기 때문에 이런식으로 처리를 하지는 않음.
        // 우선은 이메일도 변경 가능하게 해줌
        user.setEmail(email);
        user.setName(name);
        user.setLevel(level);
        // 실제로 user 로 활용이 되려면 orElse() 사용
        return user;
    }

    public User deleteUser(Long id) {
        // TODO: 실제로 작업 처리
        User user = userRepository.findById(id).orElse(null);
        user.deactivate();
        return user;
    }
}
