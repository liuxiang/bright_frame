package com.wosai.bright.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "DEVICE_WARNING")
public class DeviceWarning {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select SEQ_DEVICE_WARNING.nextval from dual")
    private Short id;

    @Column(name = "RULE_ID")
    private Short ruleId;

    @Column(name = "RULE_SCRIPT_ID")
    private Short ruleScriptId;

    @Column(name = "DEVICE_ID")
    private Short deviceId;

    @Column(name = "DEVICE_PROPERTY_KEY")
    private String devicePropertyKey;

    @Column(name = "VALUE")
    private String value;

    @Column(name = "USER_ID")
    private Short userId;

    @Column(name = "STATUS")
    private Short status;

    @Column(name = "MENU_ID")
    private Short menuId;

    @Column(name = "LEVER")
    private Short lever;

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
     * @return RULE_ID
     */
    public Short getRuleId() {
        return ruleId;
    }

    /**
     * @param ruleId
     */
    public void setRuleId(Short ruleId) {
        this.ruleId = ruleId;
    }

    /**
     * @return RULE_SCRIPT_ID
     */
    public Short getRuleScriptId() {
        return ruleScriptId;
    }

    /**
     * @param ruleScriptId
     */
    public void setRuleScriptId(Short ruleScriptId) {
        this.ruleScriptId = ruleScriptId;
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
     * @return DEVICE_PROPERTY_KEY
     */
    public String getDevicePropertyKey() {
        return devicePropertyKey;
    }

    /**
     * @param devicePropertyKey
     */
    public void setDevicePropertyKey(String devicePropertyKey) {
        this.devicePropertyKey = devicePropertyKey;
    }

    /**
     * @return VALUE
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
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
     * @return MENU_ID
     */
    public Short getMenuId() {
        return menuId;
    }

    /**
     * @param menuId
     */
    public void setMenuId(Short menuId) {
        this.menuId = menuId;
    }

    /**
     * @return LEVER
     */
    public Short getLever() {
        return lever;
    }

    /**
     * @param lever
     */
    public void setLever(Short lever) {
        this.lever = lever;
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