package com.black.store.controller;

import com.black.store.dto.UserDTO;
import com.black.store.service.UserService;
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

}
