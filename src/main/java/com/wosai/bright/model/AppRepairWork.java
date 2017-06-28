package com.wosai.bright.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "APP_REPAIR_WORK")
public class AppRepairWork {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select SEQ_APP_REPAIR_WORK.nextval from dual")
    private Short id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "DEVICE_ID")
    private Short deviceId;

    @Column(name = "SPONSOR_USER_ID")
    private Short sponsorUserId;

    @Column(name = "HANDLE_USER_ID")
    private Short handleUserId;

    @Column(name = "SOLVE_USER_ID")
    private Short solveUserId;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "IMG1")
    private String img1;

    @Column(name = "IMG2")
    private String img2;

    @Column(name = "IMG3")
    private String img3;

    @Column(name = "IMG4")
    private String img4;

    @Column(name = "STATUS")
    private Short status;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

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
     * @return CODE
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return DEVICE_ID
     */
    public Short getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId
     */
    public void setDeviceId(Short deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * @return SPONSOR_USER_ID
     */
    public Short getSponsorUserId() {
        return sponsorUserId;
    }

    /**
     * @param sponsorUserId
     */
    public void setSponsorUserId(Short sponsorUserId) {
        this.sponsorUserId = sponsorUserId;
    }

    public Short getHandleUserId() {
        return handleUserId;
    }

    public void setHandleUserId(Short handleUserId) {
        this.handleUserId = handleUserId;
    }

    /**
     * @return SOLVE_USER_ID
     */
    public Short getSolveUserId() {
        return solveUserId;
    }

    /**
     * @param solveUserId
     */
    public void setSolveUserId(Short solveUserId) {
        this.solveUserId = solveUserId;
    }

    /**
     * @return REMARK
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return IMG1
     */
    public String getImg1() {
        return img1;
    }

    /**
     * @param img1
     */
    public void setImg1(String img1) {
        this.img1 = img1;
    }

    /**
     * @return IMG2
     */
    public String getImg2() {
        return img2;
    }

    /**
     * @param img2
     */
    public void setImg2(String img2) {
        this.img2 = img2;
    }

    /**
     * @return IMG3
     */
    public String getImg3() {
        return img3;
    }

    /**
     * @param img3
     */
    public void setImg3(String img3) {
        this.img3 = img3;
    }

    /**
     * @return IMG4
     */
    public String getImg4() {
        return img4;
    }

    /**
     * @param img4
     */
    public void setImg4(String img4) {
        this.img4 = img4;
    }

    /**
     * @return STATUS
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}