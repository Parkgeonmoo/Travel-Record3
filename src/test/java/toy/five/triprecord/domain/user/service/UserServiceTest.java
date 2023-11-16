package toy.five.triprecord.domain.user.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import toy.five.triprecord.domain.user.dto.request.UserUpdateReqeust;
import toy.five.triprecord.domain.user.dto.response.UserUpdateResponse;
import toy.five.triprecord.domain.user.entity.User;
import toy.five.triprecord.domain.user.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("유저 정보 update 테스트")
    public void updateUserTest() throws Exception {
        // Given
        String email = "test@naver.com";
        String password = "password";
        String name = "name";

        UserUpdateReqeust userUpdateRequest = UserUpdateReqeust.builder()
                .email(email)
                .password(password)
                .name(name)
                .build();

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .build();

        given(userRepository.findByEmail(any(String.class))).willReturn(Optional.of(user));
        given(passwordEncoder.encode(any(String.class))).willReturn("encodedPassword");

        // When
        UserUpdateResponse result = userService.updateUser(userUpdateRequest);

        // Then
        assertEquals(result.getEmail(), email);
        assertEquals(result.getName(), name);

        logger.info("!!!!!!!!!유저 정보 update 성공!!!!!!!!!!!!!");
    }
}
