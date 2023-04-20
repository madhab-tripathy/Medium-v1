package com.programmer.Blog1.Security.Repository;

import com.programmer.Blog1.Security.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    public UserEntity findUserByEmail(String email);
}
