package toy.five.triprecord.domain.user.controller;


import org.junit.jupiter.api.DisplayName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import toy.five.triprecord.domain.user.dto.request.UserLoginRequest;
import toy.five.triprecord.domain.user.service.UserService;
import toy.five.triprecord.global.security.repository.TokenRepository;
import toy.five.triprecord.global.security.service.JwtTokenService;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private JwtTokenService jwtTokenService;

    @MockBean
    private TokenRepository tokenRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private PasswordEncoder passwordEncoder;





    @Test
    @WithMockUser
    @DisplayName("로그인 Test")
    public void loginTest() throws Exception {

        // Given
        String email = "pgw111117@naver.com";
        String password = "a12345";

        // 이메일과 비밀번호를 가진 UserLoginRequest 객체 생성
        //When
        UserLoginRequest userLoginRequest = new UserLoginRequest().builder()
                .email(email)
                .password(password)
                .build();



        given(jwtTokenService.generateToken(any(UserLoginRequest.class))).willReturn(Arrays.asList("access_token", "refresh_token"));


        // JSON 형태로 요청 본문 만들기
        String requestBody = "{" +
                "\"email\":\"" + email + "\"," +
                "\"password\":\"" + password + "\"" +
                "}";

        // Then
        mvc.perform(MockMvcRequestBuilders.post("/users/login-user")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("SUCCESS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value("access토큰:access_token\r\nrefresh 토큰 :refresh_token"));

       logger.info("!!!!!!!!!로그인 Test 성공!!!!!!!!!!!!!");

    }
}
