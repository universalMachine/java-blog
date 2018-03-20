package com.wang.blog.repository;

import com.wang.blog.domain.LoginLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginLogRepository extends JpaRepository<LoginLog,Integer>{
}
