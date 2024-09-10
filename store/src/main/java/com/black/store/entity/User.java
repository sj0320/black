package com.black.store.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nickName;

    private String username;    // email

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(Long id,String nickName, String username, String password, String address, Role role){
        this.id = id;
        this.nickName = nickName;
        this.username = username;
        this.password = password;
        this.address = address;
        this.role = role;
    }
}
