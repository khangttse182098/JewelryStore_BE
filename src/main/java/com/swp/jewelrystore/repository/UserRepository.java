package com.swp.jewelrystore.repository;

import com.swp.jewelrystore.custom.UserRepositoryCustom;
import com.swp.jewelrystore.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;


public interface UserRepository extends JpaRepository<UserEntity, Long> , UserRepositoryCustom {
    UserEntity findByUserNameAndPasswordAndStatus(String username, String password, Long status);
    UserEntity findByIdIs(Long id );
    List<UserEntity> getAllUsers(Map<String, String> params);
    UserEntity findByUserName(String username);
}
