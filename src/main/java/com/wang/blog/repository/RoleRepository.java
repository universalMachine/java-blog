package com.wang.blog.repository;

import com.wang.blog.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role,Long>{
    @Query("select r from Role as r where r.name = :role_name")
    public Role findByName(@Param("role_name") String name);
}
