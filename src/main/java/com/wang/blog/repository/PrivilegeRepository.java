package com.wang.blog.repository;

import com.wang.blog.domain.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PrivilegeRepository extends JpaRepository<Privilege,Long> {
    @Query("select p from Privilege as p where p.name = :privilege_name")
    public Privilege findByName(@Param("privilege_name") String name);
}
