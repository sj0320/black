package com.black.store.dto;


import com.black.store.entity.Role;
import com.black.store.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class UserDTO {
    @Getter
    @Setter
    public static class SaveRequest{

        @NotBlank(message = "닉네임을 입력해주세요.")
        private String nickName;

        @NotBlank(message = "이메일 주소를 입력해주세요.")
        @Email(message = "올바른 이메일 주소를 입력해주세요.")
        private String username;

        @NotBlank(message = "비밀번호를 입력해주세요")
        @Size(min = 8, max=20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요")
        private String password;

        @NotBlank(message = "주소를 입력해주세요")
        private String address;

        public User toEntity(){
            return User.builder()
                    .nickName(nickName)
                    .username(username)
                    .password(password)
                    .address(address)
                    .role(Role.USER)
                    .build();
        }

    }

}
