package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="UserMember")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String loginId;
    private String password;
    private String nickname;
    private UserRole role;
}
