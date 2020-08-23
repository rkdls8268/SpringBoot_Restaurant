package kr.co.fastcampus.eatgo.interfaces;

// Restaurant 에러에 대한 처리를 담당

import kr.co.fastcampus.eatgo.domain.RestaurantNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestaurantErrorAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RestaurantNotFoundException.class)
    public String handleNotFound() {
        // 404, not found
        return "{}";
    }
}
