package kr.co.fastcampus.eatgo.filters;

import io.jsonwebtoken.Claims;
import kr.co.fastcampus.eatgo.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private JwtUtil jwtUtil;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
    }

    // 필터가 실제로 요청이 있을때마다 다음 메서드를 실행하여 jwt 분석을 처리한다.
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        Authentication authentication = getAuthentication(request);
        if (authentication != null) {
            // authentication 을 실제로 잡아주는 것이 필요함
            SecurityContext context = SecurityContextHolder.getContext();
            // 여기서 authentication 을 세팅했기 때문에 이것을 그대로 얻어서 ReviewController 에서 사용
            context.setAuthentication(authentication);
        }
        // 있냐 없냐랑 상관없이 doFilter 는 항상 실행
        chain.doFilter(request, response); // 기본적인 형태
    }

    // 사용자가 사용하는 것이 아니고 스프링 내부에서 사용
    private Authentication getAuthentication(HttpServletRequest request) {
        // 헤더에서 정보 얻기
        String token = request.getHeader("Authorization");
        if (token == null) {
            return null;
        }

        // Todo: jwt 분석
        // TODO: JwtUtil 에서 claims 얻기

        // header 에서 가져오는 token 은 Authorization: Bearer token.lktdf.dlfk 이런 형태이기 때문에 앞에 Bearer 부분 빼주기
        Claims claims = jwtUtil.getClaims(token.substring("Bearer ".length()));
        Authentication authentication = new UsernamePasswordAuthenticationToken(claims, null);
        return authentication;
    }
}
