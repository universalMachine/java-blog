package com.wang.blog.repository;

import com.wang.blog.domain.User;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;


@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
    @Transactional
    @Query("select u from User as u where u.userName = :user_name")
    User getUserByUserName(@Param("user_name") String userName);
}
