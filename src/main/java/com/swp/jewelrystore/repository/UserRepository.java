package com.swp.jewelrystore.repository;

import com.swp.jewelrystore.custom.UserRepositoryCustom;
import com.swp.jewelrystore.entity.SellOrderEntity;
import com.swp.jewelrystore.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;


public interface UserRepository extends JpaRepository<UserEntity, Long> , UserRepositoryCustom, CrudRepository<UserEntity, Long> {
    UserEntity findByUserNameAndPasswordAndStatus(String username, String password, Long status);
    UserEntity findByIdIs(Long id);
    List<UserEntity> getAllUsers(Map<String, String> params);
    UserEntity findByUserName(String username);
    List<UserEntity> findAllByIdIn(List<Long> ids);
    UserEntity findByPhone(String phoneNumber);
    @Query(value = "SELECT user.* FROM user JOIN role ON user.role_id = role.role_id WHERE role.code = :code ", nativeQuery = true)
    List<UserEntity> findByRoleCode(@Param("code") String code);
}
