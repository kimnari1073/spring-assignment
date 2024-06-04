package com.example.firstproject.dto;

import com.example.firstproject.entity.User;
import com.example.firstproject.entity.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JoinRequest {

    private String joinId;
    private String password;
    private String passwordCheck;
    private String nickname;


    public User toEntity() {
        return User.builder()
                .loginId(this.joinId)
                .password(this.password)
                .nickname(this.nickname)
                .role(UserRole.USER)
                .build();
    }

}