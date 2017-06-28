package com.wosai.bright.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "APP_TOKEN")
public class AppToken {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select SEQ_APP_TOKEN.nextval from dual")
    private Short id;

    @Column(name = "USER_ID")
    private Short userId;

    @Column(name = "TOKEN")
    private String token;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "EXPIRE_TIME")
    private Date expireTime;

    /**
     * @return ID
     */
    public Short getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Short id) {
        this.id = id;
    }

    /**
     * @return USER_ID
     */
    public Short getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Short userId) {
        this.userId = userId;
    }

    /**
     * @return TOKEN
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return CREATE_TIME
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return EXPIRE_TIME
     */
    public Date getExpireTime() {
        return expireTime;
    }

    /**
     * @param expireTime
     */
    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}