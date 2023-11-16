package toy.five.triprecord.global.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import toy.five.triprecord.domain.user.dto.request.UserLoginRequest;
import toy.five.triprecord.global.exception.BaseException;
import toy.five.triprecord.global.exception.ErrorCode;
import toy.five.triprecord.global.security.repository.TokenRepository;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    @Value("${spring.jwt.secret}")
    private String jwtSecret;

    private int accessTokenExpMinutes = 10;
    private int refreshTokenExpMinutes = 100;



    public void authenticate(UserLoginRequest userLoginRequest) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userLoginRequest.getEmail());
            if (!passwordEncoder.matches(userLoginRequest.getPassword(), userDetails.getPassword())) {
                throw new BaseException(ErrorCode.USER_NO_APPROVE_ERROR);
            }
        } catch (UsernameNotFoundException e) {
            throw new BaseException(ErrorCode.USER_CAN_NOT_FIND_EMAIL);
        }

    }
    public List<String> generateToken(UserLoginRequest userLoginRequest) {
        // 로그인 요청에 대한 인증을 수행합니다.
        // 이 부분은 사용자의 아이디와 비밀번호를 확인하는 로직으로 구현해야 합니다.
        authenticate(userLoginRequest);

        String accessToken = generateAccessTokens(userLoginRequest);
        String refreshToken = generateRefreshTokens(userLoginRequest);

        return Arrays.asList(accessToken,refreshToken);
    }

    // Claim들 과 userdrtail로 토큰 생성하는 메서드
    public String generateAccessTokens(UserLoginRequest userLoginRequest) {
        // 액세스 토큰 생성
        String accessToken = Jwts.builder()
                .setSubject(userLoginRequest.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpMinutes * 60 * 1000))  // 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256, getSignInKey())
                .compact();

        // 리프레시 토큰 생성

        return accessToken;
    }

    public String generateRefreshTokens(UserLoginRequest userLoginRequest) {

        String refreshToken = Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpMinutes * 60 * 1000))  // 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256, getSignInKey())
                .compact();

        return refreshToken;

    }




    public String accessTokenCheck(String token) {
        Claims accessClaims = null;
        try {
            accessClaims = extractAllClaims(token);
        } catch (ExpiredJwtException e) {
            throw new BaseException(ErrorCode.JWT_TOKEN_EXPIRED);
        }
        return token;
    }



    // 토큰안의 Claims 추출 메서드
    private Claims extractAllClaims(String token) {
        //token payload 만료기간
        return Jwts
                // JWT 토큰을 파싱(담긴 정보 분석)하기 위한 JwtParserBuilder 객체를 생성
                .parserBuilder()
                // JWT 토큰 파싱에 사용할 서명 키를 설정
                .setSigningKey(getSignInKey())
                // JwtParser 객체를 생성
                .build()
                // JwtParser 객체와 매개변수로 넣은 토큰을 비교하여 실제 검증작업
                // 검증된다면 Jws<Claims> 객체 반환, 검증되지 않는다면 JwtException 발생
                .parseClaimsJws(token)
                // 객체에서 Claims를 추출, DB사용안하고 이 내용으로 다른 로직 구현
                .getBody();
    }

    private Key getSignInKey() {
        // 비밀키를 BASE64로 디코딩하여 바이트 배열로
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        // 디코딩된 바이트 배열을 이용해 HMAC-SHA 알고리즘에 사용될 Key 객체 생성
        // 실제 JWT토큰을 만드는데 사용되는 알고리즘에 키 값을 넣어 세팅하는 것이다.
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
