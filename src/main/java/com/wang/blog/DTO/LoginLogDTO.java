package com.wang.blog.DTO;

import com.wang.blog.domain.User;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class LoginLogDTO {
    private Integer loginLogId;

    @Column(nullable = false)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 30,nullable = false)
    private String ip;

    @Column(nullable = false)
    private LocalDate loginDateTime;


    public LoginLogDTO() {
    }

    public Integer getLoginLogId() {
        return loginLogId;
    }

    public void setLoginLogId(Integer loginLogId) {
        this.loginLogId = loginLogId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public LocalDate getLoginDateTime() {
        return loginDateTime;
    }

    public void setLoginDateTime(LocalDate loginDateTime) {
        this.loginDateTime = loginDateTime;
    }
}
