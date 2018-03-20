package com.wang.blog.domain;

import javax.persistence.*;
import javax.websocket.ClientEndpoint;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity

public class LoginLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer loginLogId;


    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Column(length = 30,nullable = false)
    private String ip;

    @Column(nullable = false)
    private LocalDate loginDateTime;


    public LoginLog() {
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
