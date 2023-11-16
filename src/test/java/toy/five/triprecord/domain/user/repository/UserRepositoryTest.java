package toy.five.triprecord.domain.user.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import toy.five.triprecord.domain.user.entity.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestConfig.class)
public class UserRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    @Autowired
    private UserRepository userRepository;


    @Test
    @DisplayName("이름을 통하여 찾기 테스트")
    public void findByEmailTest() {
        // Given
        String email = "test@naver.com";
        User user = User.builder()
                .email(email)
                .password("password")
                .name("name")
                .build();
        userRepository.save(user);

        // When
        Optional<User> result = userRepository.findByEmail(email);

        // Then
        assertThat(result).isNotEmpty();
        assertThat(result.get().getEmail()).isEqualTo(email);

        logger.info("!!!!!!!!!이름을 통하여 찾기 Test 성공!!!!!!!!!!!!!");
    }
}

