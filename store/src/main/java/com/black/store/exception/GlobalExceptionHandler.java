package com.black.store.exception;

import com.black.store.exception.user.DuplicatedNicknameException;
import com.black.store.exception.user.DuplicatedUsernameException;
import com.black.store.exception.user.InvalidTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatedUsernameException.class)
    public final ResponseEntity<String>handleDuplicatedUsernameException(DuplicatedUsernameException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e + "중복된 이메일입니다.");
    }

    @ExceptionHandler(DuplicatedNicknameException.class)
    public final ResponseEntity<String>handleDuplicatedNicknameException(DuplicatedNicknameException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e + " 중복된 닉네임입니다.");
    }

    @ExceptionHandler(InvalidTokenException.class)
    public final ResponseEntity<String>handleInvalidTokenException(InvalidTokenException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
