package com.wosai.bright.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "APP_REPAIR_WORK_LOG")
public class AppRepairWorkLog {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select SEQ_APP_REPAIR_WORK_LOG.nextval from dual")
    private Short id;

    @Column(name = "WORK_CODE")
    private String workCode;

    @Column(name = "WORK_STATUS")
    private Short workStatus;

    @Column(name = "WORK_HANDLE_USER_ID")
    private Short workHandleUserId;

    @Column(name = "OPERATOR_ID")
    private Short operatorId;

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
     * @return WORK_CODE
     */
    public String getWorkCode() {
        return workCode;
    }

    /**
     * @param workCode
     */
    public void setWorkCode(String workCode) {
        this.workCode = workCode;
    }

    /**
     * @return WORK_STATUS
     */
    public Short getWorkStatus() {
        return workStatus;
    }

    /**
     * @param workStatus
     */
    public void setWorkStatus(Short workStatus) {
        this.workStatus = workStatus;
    }

    public Short getWorkHandleUserId() {
        return workHandleUserId;
    }

    public void setWorkHandleUserId(Short workHandleUserId) {
        this.workHandleUserId = workHandleUserId;
    }

    /**
     * @return OPERATOR_ID
     */
    public Short getOperatorId() {
        return operatorId;
    }

    /**
     * @param operatorId
     */
    public void setOperatorId(Short operatorId) {
        this.operatorId = operatorId;
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