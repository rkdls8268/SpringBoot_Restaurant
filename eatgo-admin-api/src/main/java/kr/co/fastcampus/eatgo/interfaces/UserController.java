package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.UserService;
import kr.co.fastcampus.eatgo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> list() {
        List<User> users = userService.getUsers();
        return users;
    }
    // 1. user list
    // 2. user create -> 나중에 회원 가입으로 처리
    // 3. user update
    // 4. user delete -> level 0 => 아무것도 못함
    // (1: customer, 2: restaurant owner, 3: admin)
}
