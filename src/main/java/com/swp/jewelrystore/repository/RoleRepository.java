package com.swp.jewelrystore.repository;

import com.swp.jewelrystore.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<RoleEntity, Long> {
}
