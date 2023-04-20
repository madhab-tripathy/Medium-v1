package com.programmer.Blog1.Security.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(length = 50,nullable = false)
    private String  name;

    @Column(length = 50,nullable = false)
    private String email;

    @Column(length = 15,nullable = false)
    private String mobile;

    @Column(nullable = false)
    private String password;

}