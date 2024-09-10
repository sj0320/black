package com.black.store.service;

import com.black.store.dto.UserDTO;
import com.black.store.entity.Role;
import com.black.store.entity.User;
import com.black.store.exception.user.DuplicatedNicknameException;
import com.black.store.exception.user.DuplicatedUsernameException;
import com.black.store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean isDuplicatedUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public boolean isDuplicatedNickname(String nickname){
        return userRepository.existsByNickName(nickname);
    }

    public void join(UserDTO.SaveRequest userDTO){
        if(isDuplicatedUsername(userDTO.getUsername())){
            throw new DuplicatedUsernameException();
        }
        if(isDuplicatedNickname(userDTO.getNickName())){
            throw new DuplicatedNicknameException();
        }
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        User user = userDTO.toEntity();
        userRepository.save(user);
    }


}
