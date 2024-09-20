package com.black.store.controller;

import com.black.store.dto.UserDTO;
import com.black.store.jwt.JwtUtil;
import com.black.store.service.UserService;
import com.black.store.util.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<String>join(@Valid @RequestBody UserDTO.SaveRequest userDTO){
        userService.join(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");
    }

    @PostMapping("/reissue")
    public ResponseEntity<?>reissue(HttpServletRequest request, HttpServletResponse response){
        String refresh = userService.getRefreshToken(request.getCookies());
        if(userService.isValidRefreshToken(refresh)){
            String accessToken = userService.generateAccessToken(refresh);
            String refreshToken = userService.generateRefreshToken(refresh);
            response.setHeader("access",accessToken);
            response.addCookie(CookieUtil.createCookie("refresh",refreshToken));
            //response.addCookie(userService.createCookie("refresh",refreshToken));
        }
        return ResponseEntity.status(HttpStatus.OK).body("access token 재발급 완료");
    }

}
