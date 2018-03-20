package com.wang.blog.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.jboss.logging.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(indexes = {
        @Index(columnList = "userName",unique = true)
})
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User implements Serializable{
    @Id
    @GeneratedValue
    private Integer userId;

    @OneToMany(mappedBy ="user")
    private List<Post> posts;

    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST})
    @JoinTable(name = "board_manager", joinColumns = {@JoinColumn(name ="user" )}, inverseJoinColumns = {@JoinColumn(name = "board") })
    private List<Board> boards;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "author")
    private Set<Topic> topics;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    @NaturalId
    @Column(length = 100,unique = true)
    private String userName;

    private String password;

    private Integer userType;

    private boolean locked;

    private Integer credit;

    private String email;

    public boolean isTokenExpired() {
        return tokenExpired;
    }

    public void setTokenExpired(boolean tokenExpired) {
        this.tokenExpired = tokenExpired;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private boolean enabled;

    private boolean tokenExpired;


    public User() {
    }


    public Set<Topic> getTopics() {
        return topics;
    }

    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public synchronized boolean isLocked() {
        return locked;
    }

    public synchronized void  setLocked(boolean locked) {
        this.locked = locked;
    }

    public synchronized boolean isUnLocked() {return !locked;}

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public List<Board> getBoards() {
        return boards;
    }

    public void setBoards(List<Board> boards) {
        this.boards = boards;
    }



    public void addBoard(Board board){
        if(boards.contains(board)){
         return;
        }

        boards.add(board);

    }

    public void removeBoard(Board board){
        if(boards.contains(board)){
            boards.remove(board);

        }else
            return;

    }




}
