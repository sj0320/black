package com.black.store.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;

@Entity
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;    // email

    private String nickName;

    private String password;

    private String address;

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
