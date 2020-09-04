package kr.co.fastcampus.eatgo;

import kr.co.fastcampus.eatgo.filters.JwtAuthenticationFilter;
import kr.co.fastcampus.eatgo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {

    // 괄호안에 있는 값을 value로 사용하겠다는 뜻
    @Value("${jwt.secret}")
    private String secret;
    // 이 값을 application.yml 에서 넣어주자

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 필터를 만들어준다. 사용할 기반 클래스: BasicAuthenticationFilter
        Filter filter = new JwtAuthenticationFilter(authenticationManager(), jwtUtil());
        http
                .cors().disable()
                .csrf().disable()
                .formLogin().disable()
                .headers().frameOptions().disable()
                .and()
                .addFilter(filter)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                // 세션에 대한 정책을 stateless 로 정함 -> 세션에 대해 따로 관리해주지 않음~
        // and() 를 통해 다시 초기화
        // HttpSecurity 등 지금 어떤 객체를 사용하고 있는지를 명시해줌.
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil(secret);
    }
}
