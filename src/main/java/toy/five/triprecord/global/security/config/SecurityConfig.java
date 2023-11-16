package toy.five.triprecord.global.security.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import toy.five.triprecord.domain.user.repository.UserRepository;
import toy.five.triprecord.global.exception.BaseException;
import toy.five.triprecord.global.exception.ErrorCode;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NO_EXIST));
    }

    // 비밀번호 인코딩 설정
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationProvider는 실제로 사용자 인증을 처리하는 컴포넌트
    // 사용자가 입력한 인증 정보(예: 사용자 이름과 비밀번호)를 받아 실제 사용자 정보와 비교하여 인증 여부를 결정
    @Bean
    public AuthenticationProvider authenticationProvider() {
        // AuthenticationProvider의 구현체인 DaoAuthenticationProvider를 생성
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        // DaoAuthenticationProvider에 UserDetailsService를 설정.
        // 이 UserDetailsService는 나중에 DaoAuthenticationProvider가 사용자 정보를 조회할 때 사용 됨.
        authenticationProvider.setUserDetailsService(userDetailsService());
        // DaoAuthenticationProvider에 PasswordEncoder를 설정.
        // 이 PasswordEncoder는 나중에 DaoAuthenticationProvider가 사용자가 입력한 비밀번호와 저장된 비밀번호를 비교할 때 사용 됨.
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }



}