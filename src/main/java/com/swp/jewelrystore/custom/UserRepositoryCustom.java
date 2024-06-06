package com.swp.jewelrystore.custom;

import com.swp.jewelrystore.entity.UserEntity;

import java.util.List;
import java.util.Map;

public interface UserRepositoryCustom  {
    List<UserEntity> getAllUsers(Map<String, String> params);
}
