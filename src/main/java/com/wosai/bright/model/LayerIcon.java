package com.wosai.bright.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "LAYER_ICON")
public class LayerIcon {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select SEQ_LAYER_ICON.nextval from dual")
    private Short id;

    @Column(name = "LAYER_ID")
    private Short layerId;

    @Column(name = "POSITION_X")
    private String positionX;

    @Column(name = "POSITION_Y")
    private String positionY;

    @Column(name = "WIDTH")
    private Short width;

    @Column(name = "HEIGHT")
    private Short height;

    @Column(name = "DEVICE_ID")
    private Short deviceId;

    @Column(name = "DEVICE_PROPERTY_KEY")
    private String devicePropertyKey;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "ICON_SRC")
    private String iconSrc;

    @Column(name = "FUNCTION_TYPE")
    private Short functionType;

    @Column(name = "EXPIRE_TIME")
    private Date expireTime;

    @Transient
    private List iconStatus_target;

    @Transient
    private List iconStatus_limit;

    public List getIconStatus_target() {
        return iconStatus_target;
    }

    public void setIconStatus_target(List iconStatus_target) {
        this.iconStatus_target = iconStatus_target;
    }

    public List getIconStatus_limit() {
        return iconStatus_limit;
    }

    public void setIconStatus_limit(List iconStatus_limit) {
        this.iconStatus_limit = iconStatus_limit;
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
     * @return LAYER_ID
     */
    public Short getLayerId() {
        return layerId;
    }

    /**
     * @param layerId
     */
    public void setLayerId(Short layerId) {
        this.layerId = layerId;
    }

    /**
     * @return POSITION_X
     */
    public String getPositionX() {
        return positionX;
    }

    /**
     * @param positionX
     */
    public void setPositionX(String positionX) {
        this.positionX = positionX;
    }

    /**
     * @return POSITION_Y
     */
    public String getPositionY() {
        return positionY;
    }

    /**
     * @param positionY
     */
    public void setPositionY(String positionY) {
        this.positionY = positionY;
    }

    /**
     * @return WIDTH
     */
    public Short getWidth() {
        return width;
    }

    /**
     * @param width
     */
    public void setWidth(Short width) {
        this.width = width;
    }

    /**
     * @return HEIGHT
     */
    public Short getHeight() {
        return height;
    }

    /**
     * @param height
     */
    public void setHeight(Short height) {
        this.height = height;
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
     * @return ICON_SRC
     */
    public String getIconSrc() {
        return iconSrc;
    }

    /**
     * @param iconSrc
     */
    public void setIconSrc(String iconSrc) {
        this.iconSrc = iconSrc;
    }

    /**
     * @return FUNCTION_TYPE
     */
    public Short getFunctionType() {
        return functionType;
    }

    /**
     * @param functionType
     */
    public void setFunctionType(Short functionType) {
        this.functionType = functionType;
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