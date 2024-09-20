package com.black.store.service;

import com.black.store.dto.UserDTO;
import com.black.store.entity.Role;
import com.black.store.entity.User;
import com.black.store.exception.user.DuplicatedNicknameException;
import com.black.store.exception.user.DuplicatedUsernameException;
import com.black.store.exception.user.InvalidTokenException;
import com.black.store.jwt.JwtUtil;
import com.black.store.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;

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

    public String getRefreshToken(Cookie[] cookies){
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("refresh")){
                return cookie.getValue();
            }
        }
        return null;
    }


    public boolean isValidRefreshToken(String refresh) throws InvalidTokenException {
        if(refresh == null){
            throw new InvalidTokenException("Refresh token null");
        }

        try{
            jwtUtil.isExpired(refresh);
        }
        catch (ExpiredJwtException e) {
            throw new InvalidTokenException("Refresh token expired");
        }

        if(!jwtUtil.getCategory(refresh).equals("refresh")){
            throw new InvalidTokenException("Invalid token category");
        }
        return true;
    }

    public String generateAccessToken(String refresh) {
        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);
        return jwtUtil.createJwt("access",username,role,600000L);
    }

    public String generateRefreshToken(String refresh) {
        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);
        return jwtUtil.createJwt("refresh",username,role,86400000L);
    }

//    public Cookie createCookie(String key, String value){
//        Cookie cookie = new Cookie(key,value);
//        cookie.setMaxAge(24*60*60);
//        //cookie.setSecure(true);
//        cookie.setPath("/");
//        cookie.setHttpOnly(true);
//        return cookie;
//    }
}
