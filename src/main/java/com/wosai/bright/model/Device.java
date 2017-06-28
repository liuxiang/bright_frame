package com.wosai.bright.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "DEVICE")
public class Device {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select SEQ_DEVICE.nextval from dual")
    private Short id;

    @Column(name = "MODEL_CODE")
    private String modelCode;

    @Column(name = "NAME")
    private String name;

    @Column(name = "POSITION")
    private String position;

    @Column(name = "CAMERA_DEVICE_ID")
    private Short cameraDeviceId;

    @Column(name = "IP")
    private String ip;

    @Column(name = "MENU_ID")
    private Short menuId;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "EXPIRE_TIME")
    private Date expireTime;

    @Column(name = "UCODE")
    private String ucode;

    @Transient
    private String menuName;

    @Transient
    List<DeviceProperty> devicePropertyList;

    public List<DeviceProperty> getDevicePropertyList() {
        return devicePropertyList;
    }

    public void setDevicePropertyList(List<DeviceProperty> devicePropertyList) {
        this.devicePropertyList = devicePropertyList;
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
     * @return MODEL_CODE
     */
    public String getModelCode() {
        return modelCode;
    }

    /**
     * @param modelCode
     */
    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
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
     * @return POSITION
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * @return CAMERA_DEVICE_ID
     */
    public Short getCameraDeviceId() {
        return cameraDeviceId;
    }

    /**
     * @param cameraDeviceId
     */
    public void setCameraDeviceId(Short cameraDeviceId) {
        this.cameraDeviceId = cameraDeviceId;
    }

    /**
     * @return IP
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip
     */
    public void setIp(String ip) {
        this.ip = ip;
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

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getUcode() {
        return ucode;
    }

    public void setUcode(String ucode) {
        this.ucode = ucode;
    }
}