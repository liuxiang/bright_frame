package com.wosai.bright.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "SYS_OPERATING_LOG")
public class SysOperatingLog {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select SEQ_SYS_OPERATING_LOG.nextval from dual")
    private Short id;

    @Column(name = "OPERATING_CODE")
    private String operatingCode;

    @Column(name = "USER_ID")
    private Short userId;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "CREATE_TIME")
    private Date createTime;

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
     * @return OPERATING_CODE
     */
    public String getOperatingCode() {
        return operatingCode;
    }

    /**
     * @param operatingCode
     */
    public void setOperatingCode(String operatingCode) {
        this.operatingCode = operatingCode;
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
     * @return CONTENT
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
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
}