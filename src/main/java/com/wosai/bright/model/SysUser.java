package com.wosai.bright.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "SYS_USER")
public class SysUser {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select SEQ_SYS_USER.nextval from dual")
    private Short id;

    @Column(name = "ACCOUNT")
    private String account;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "TYPE")
    private Short type;

    @Column(name = "NAME")
    private String name;

    @Column(name = "NICKNAME")
    private String nickname;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "STATUS")
    private Short status;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "EXPIRE_TIME")
    private Date expireTime;

    @Column(name = "PLATFORM")
    private String platform;

    @Column(name = "PHOTO")
    private String photo;


    @Transient
    private List<SysMenu> menuList;

    public List<SysMenu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<SysMenu> menuList) {
        this.menuList = menuList;
    }

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
     * @return ACCOUNT
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return PASSWORD
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return TYPE
     */
    public Short getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(Short type) {
        this.type = type;
    }

    /**
     * @return NAME
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return NICKNAME
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return MOBILE
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return EMAIL
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return status
     */
    public Short getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Short status) {
        this.status = status;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}