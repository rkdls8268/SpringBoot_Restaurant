package kr.co.fastcampus.eatgo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

//@Component
public class JwtUtil {

    private Key key;

    public JwtUtil(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String createToken(long userId, String name, Long restaurantId) {
//        String secret = "12345678901234567890123456789012";
//        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        JwtBuilder builder = Jwts.builder()
                .claim("userId", userId)
                .claim("name", name);
        if (restaurantId != null) {
            builder = builder.claim("restaurantId", restaurantId);
            // claim 은 내부에서 자기 자신을 고침..?
        }
        return builder
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims getClaims(String token) {
        // 위에서도 sign key 를 잡아준것처럼 아래도 잡아준다.
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token) // jws: 사인이 포함된 jwt 를 의미
                .getBody();
    }
}
