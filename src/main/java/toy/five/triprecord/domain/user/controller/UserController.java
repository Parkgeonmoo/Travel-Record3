package toy.five.triprecord.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toy.five.triprecord.domain.user.dto.request.*;
import toy.five.triprecord.domain.user.dto.response.*;
import toy.five.triprecord.domain.user.service.UserService;
import toy.five.triprecord.global.common.StatusCode;
import toy.five.triprecord.global.exception.ApiResponse;
import toy.five.triprecord.global.security.repository.TokenRepository;
import toy.five.triprecord.global.security.entity.RefreshToken;
import toy.five.triprecord.global.security.service.JwtTokenService;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final TokenRepository tokenRepository;


    @GetMapping("/find-user")
    public ResponseEntity<ApiResponse> getUser(@Valid @RequestBody UserGetRequest userGetRequest) {

        UserGetResponse userGetResponse = userService.getUserInfo(userGetRequest.getEmail());

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .status(String.valueOf(StatusCode.SUCCESS))
                        .code(HttpStatus.OK.value())
                        .data(userGetResponse)
                        .build()
        );
    }

    @PostMapping("entry-user")
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        UserCreateResponse savedUser = userService.createUser(userCreateRequest);
        return ResponseEntity.ok(ApiResponse.builder()
                .status(String.valueOf(StatusCode.SUCCESS))
                .code(HttpStatus.OK.value())
                .data(savedUser)
                .build());
    }

    @PostMapping("login-user")
    public ResponseEntity<ApiResponse> loginUser(@RequestBody UserLoginRequest userLoginRequest) {
        List<String> tokens = jwtTokenService.generateToken(userLoginRequest);
        tokenRepository.save(new RefreshToken(userLoginRequest.getEmail(),tokens.get(1)));

        return ResponseEntity.ok(ApiResponse.builder()
                .status(String.valueOf(StatusCode.SUCCESS))
                .code(HttpStatus.OK.value())
                .data("access토큰:"+ tokens.get(0)+System.lineSeparator()+"refresh 토큰 :"+tokens.get(1))
                .build());
    }

    @PostMapping("entry-accesstoken")
    public ResponseEntity<ApiResponse> entryAccessToken(@RequestBody UserLoginRequest userLoginRequest) {
        String accessToken = jwtTokenService.generateAccessTokens(userLoginRequest);

        return ResponseEntity.ok(ApiResponse.builder()
                .status(String.valueOf(StatusCode.SUCCESS))
                .code(HttpStatus.OK.value())
                .data(accessToken)
                .build());
    }






    @PutMapping("/all-update-user")
    public ResponseEntity<ApiResponse> updateUser(@Valid @RequestBody UserUpdateReqeust userUpdateReqeust) {
        UserUpdateResponse updatedUser = userService.updateUser(userUpdateReqeust);
        return ResponseEntity.ok(ApiResponse.builder()
                .status(String.valueOf(StatusCode.SUCCESS))
                .code(HttpStatus.OK.value())
                .data(updatedUser)
                .build());

    }

    @PatchMapping("update-user")
    public ResponseEntity<ApiResponse> patchUser(@Valid @RequestBody UserPatchRequest userPatchRequest) {

        UserPatchResponse userPatchResponse = userService.patchUser(userPatchRequest);

        return ResponseEntity.ok(ApiResponse.builder()
                .status(String.valueOf(StatusCode.SUCCESS))
                .code(HttpStatus.OK.value())
                .data(userPatchResponse)
                .build());
    }





}
