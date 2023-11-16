package toy.five.triprecord.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.five.triprecord.domain.user.dto.request.UserCreateRequest;
import toy.five.triprecord.domain.user.dto.request.UserPatchRequest;
import toy.five.triprecord.domain.user.dto.request.UserUpdateReqeust;
import toy.five.triprecord.domain.user.dto.response.*;
import toy.five.triprecord.domain.user.entity.User;
import toy.five.triprecord.domain.user.repository.UserRepository;
import toy.five.triprecord.global.exception.BaseException;
import toy.five.triprecord.global.exception.ErrorCode;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional(readOnly = true)
    public UserGetResponse getUserInfo(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_CAN_NOT_FIND_EMAIL));


        return UserGetResponse.fromEntity(user);

    }


    @Transactional
    public UserCreateResponse createUser(UserCreateRequest userCreateRequest) {

        if (userRepository.existsByEmail(userCreateRequest.getEmail())) {
            throw new BaseException(ErrorCode.USER_EMAIL_DUPULICATE_ERROR);
        }

        System.out.println(passwordEncoder.encode(userCreateRequest.getPassword()));

        User newUser = User.builder()
                .email(userCreateRequest.getEmail())
                .password(passwordEncoder.encode(userCreateRequest.getPassword()))
                .name(userCreateRequest.getName())
                .build();

        return UserCreateResponse.fromEntity(userRepository.save(newUser));
    }

    @Transactional
    public UserUpdateResponse updateUser(UserUpdateReqeust userUpdateReqeust) {

        User existedUser = userRepository.findByEmail(userUpdateReqeust.getEmail())
                .orElseThrow(() -> new BaseException(ErrorCode.USER_CAN_NOT_FIND_EMAIL));

        userUpdateReqeust.builder().password(passwordEncoder.encode(userUpdateReqeust.getPassword()));


        existedUser.setUpdateColumns(userUpdateReqeust);

        return UserUpdateResponse.fromEntity(existedUser);

    }

    @Transactional
    public UserPatchResponse patchUser(UserPatchRequest userPatchRequest) {
        User existedUser = userRepository.findByEmail(userPatchRequest.getEmail())
                .orElseThrow(() -> new BaseException((ErrorCode.USER_CAN_NOT_FIND_EMAIL)));

        if (!StringUtils.isBlank(userPatchRequest.getPassword())) {
            userPatchRequest.builder().password(passwordEncoder.encode(userPatchRequest.getPassword()));
        }
        existedUser.setPatchColumns(userPatchRequest);

        return UserPatchResponse.fromEntity(existedUser);
    }
}
