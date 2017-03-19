package com.wosai.bright.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "LAYER_FUNCTION")
public class LayerFunction {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select SEQ_LAYER_FUNCTION.nextval from dual")
    private Short id;

    @Column(name = "LAYER_ID")
    private Short layerId;

    @Column(name = "POSITION_X")
    private Short positionX;

    @Column(name = "POSITION_Y")
    private Short positionY;

    @Column(name = "WIDTH")
    private Short width;

    @Column(name = "HEIGHT")
    private Short height;

    @Column(name = "DEVICE_ID")
    private Short deviceId;

    @Column(name = "DEVICE_PROPERTY_KEY")
    private String devicePropertyKey;

    @Column(name = "FUNCTION_CODE")
    private String functionCode;

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
    public Short getPositionX() {
        return positionX;
    }

    /**
     * @param positionX
     */
    public void setPositionX(Short positionX) {
        this.positionX = positionX;
    }

    /**
     * @return POSITION_Y
     */
    public Short getPositionY() {
        return positionY;
    }

    /**
     * @param positionY
     */
    public void setPositionY(Short positionY) {
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
     * @return FUNCTION_CODE
     */
    public String getFunctionCode() {
        return functionCode;
    }

    /**
     * @param functionCode
     */
    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
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