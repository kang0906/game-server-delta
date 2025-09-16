package com.example.game.user.service;

import com.example.game.common.exception.ErrorCode;
import com.example.game.common.exception.GlobalException;
import com.example.game.system.value.service.GameSystemValueService;
import com.example.game.user.dto.UserInfoResponseDto;
import com.example.game.user.dto.RequestLogin;
import com.example.game.user.entity.User;
import com.example.game.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.game.common.exception.ErrorCode.DATA_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final GameSystemValueService gameSystemValueService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void signup(RequestLogin requestLogin) {
        Optional<User> findUser = userRepository.findByEmail(requestLogin.getEmail());
        if (!findUser.isPresent()) {
            User user = new User(
                    requestLogin.getEmail(),
                    null,
                    "NOOB",
                    bCryptPasswordEncoder.encode(requestLogin.getPassword())
            );

            userRepository.save(user);
        } else {
            log.warn("존재하는 아이디 입니다.");
            throw new GlobalException(ErrorCode.EXIST_EMAIL);
        }
    }

    public UserInfoResponseDto getMyInfo(User user) {
        return new UserInfoResponseDto(user);
    }

    @Transactional
    public void changeUsername(User user, String newUsername) {
        User findUser = userRepository.findById(user.getUserId()).orElseThrow(() -> new GlobalException(DATA_NOT_FOUND));
        findUser.changeUsername(newUsername);
    }
    public UserInfoResponseDto findUserInfo(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(DATA_NOT_FOUND));
        return new UserInfoResponseDto(user);
    }
}
