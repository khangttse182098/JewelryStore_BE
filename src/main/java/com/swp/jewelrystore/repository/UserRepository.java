package com.swp.jewelrystore.repository;

import com.swp.jewelrystore.entity.UserEntity;
import com.swp.jewelrystore.model.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsernameAndPasswordAndStatus(String username, String password, Long status);


}
