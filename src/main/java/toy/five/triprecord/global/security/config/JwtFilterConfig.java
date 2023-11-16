package toy.five.triprecord.global.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import toy.five.triprecord.global.exception.ApiResponse;
import toy.five.triprecord.global.exception.BaseException;
import toy.five.triprecord.global.exception.ErrorCode;
import toy.five.triprecord.global.security.service.JwtTokenService;

import java.io.IOException;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtFilterConfig extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final UserDetailsService userDetailsService;
    @Value("${spring.jwt.secret}")
    private String jwtSecret;

    private final ObjectMapper objectMapper;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // http 헤더에 있는 JWT 토큰 추출
        final String authHeader = request.getHeader("Authorization");

        // 두 조건이 없으면 request 헤더에 JWT 토큰이 없는 상황, 일단 그냥 다음 필터로 넘긴다. (Bearer 는 토큰 앞에 붙는 접두사)
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 이제 토큰이 있다면 추출 ("Bearer " 이 부분 제외하고 7번 인덱스 부터 진짜 토큰 추출)
        String jwt = authHeader.substring(7);


        String accessToken = null;
        try {
            // access토큰의 만료일 확인 및 유효한 accessToken 반환
            accessToken = jwtTokenService.accessTokenCheck(jwt);
        } catch (BaseException e) {
            ErrorCode errorCode = e.getErrorCode();
            String errorMessage = objectMapper.writeValueAsString(errorCode.getMessage());

            // ApiResponse 객체 생성
            ApiResponse<String> errorResponse = ApiResponse.fail(errorCode.getStatusCode(), errorCode.getMessage());

            // ApiResponse 객체를 JSON 문자열로 변환
            String errorResponseJson = objectMapper.writeValueAsString(errorResponse);

            response.setStatus(errorCode.getStatusCode());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(errorResponseJson);

            return;
        }


        // access토큰에서 Email뽑아 userDetail 가져오기
        String userEmail = Jwts.parser()
                .setSigningKey(jwtSecret)  // 서명 키 지정
                .parseClaimsJws(accessToken)
                .getBody()
                .getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

        // 인증객체 만들기
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                // 인증된 사용자 정보
                userDetails,
                // 인증된 사용자 비밀번호, 하지만 이미 앞서 토큰 유효성 검사를 완료했으니 불필요
                null,
                // 사용자 권한 정보
                userDetails.getAuthorities()
        );

        // setDetails 메서드는 Authentication 객체에 추가적인 정보를 설정하는 메서드 (추가사항)
        authenticationToken.setDetails(
                // WebAuthenticationDetails 객체는 HTTP 요청에 대한 세부 정보를 담는 객체로, 클라이언트의 IP 주소나 세션 ID 등의 정보를 포함한다.
                // WebAuthenticationDetailsSource는 HttpServletRequest로부터 필요한 정보만을 추출(바디 내용 말고 헤더나 IP정보같은)
                // 여기서는 요청 IP주소나 세션ID 정도가 추가된다.
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        // 이제 SecurityContextHolder 에 인증객체 삽입함으로서 현재 요청의 주체는 인증된 것
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 다음 필터로
        filterChain.doFilter(request, response);
    }



}
