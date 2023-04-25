package com.programmer.Blog1.Security.Repository;

import com.programmer.Blog1.Security.Model.UserEntity;
import com.programmer.Blog1.Security.RequestDto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByUsername(String username);
    boolean existsByEmail(String email);
}
