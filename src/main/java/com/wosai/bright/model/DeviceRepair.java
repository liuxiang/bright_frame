package com.wosai.bright.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "DEVICE_REPAIR")
public class DeviceRepair {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select SEQ_DEVICE_REPAIR.nextval from dual")
    private Short id;

    @Column(name = "DEVICE_ID")
    private Short deviceId;

    @Column(name = "USERI_ID")
    private Short useriId;

    @Column(name = "STATUS")
    private Short status;

    @Column(name = "REMARK")
    private String remark;

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
     * @return USERI_ID
     */
    public Short getUseriId() {
        return useriId;
    }

    /**
     * @param useriId
     */
    public void setUseriId(Short useriId) {
        this.useriId = useriId;
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