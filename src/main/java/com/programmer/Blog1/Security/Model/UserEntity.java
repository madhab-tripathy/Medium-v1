package com.programmer.Blog1.Security.Model;

import com.programmer.Blog1.Blogger.Model.BlogEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;
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

    @Column(length = 20, nullable = false, unique = true)
    private String username;

    @Column(length = 50,nullable = false, unique = true)
    private String email;

    @Column(length = 15,nullable = false)
    private String mobile;

    @Column(nullable = false)
    private String password;

    private String role;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    List<BlogEntity> blogList = new ArrayList<>();
}
