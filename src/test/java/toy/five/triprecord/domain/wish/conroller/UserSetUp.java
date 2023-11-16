package toy.five.triprecord.domain.wish.conroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import toy.five.triprecord.domain.user.dto.request.UserCreateRequest;
import toy.five.triprecord.domain.user.dto.response.UserCreateResponse;
import toy.five.triprecord.domain.user.repository.UserRepository;
import toy.five.triprecord.domain.user.service.UserService;

@Component
public class UserSetUp {

    @Autowired
    private UserService userService;

    private final UserCreateRequest userCreateRequest =
            UserCreateRequest.builder()
                    .email("test12345@test.com")
                    .password("@@Test123")
                    .name("test")
                    .build();

    public UserCreateResponse saveUser() {
        return userService.createUser(userCreateRequest);
    }

}
