package com.swp.jewelrystore.repository;

import com.swp.jewelrystore.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUserNameAndPasswordAndStatus(String username, String password, Long status);
    UserEntity findByIdIs(Long id );
}
