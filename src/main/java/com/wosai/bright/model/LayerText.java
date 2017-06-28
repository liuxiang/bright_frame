package com.wosai.bright.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "LAYER_TEXT")
public class LayerText {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select SEQ_LAYER_TEXT.nextval from dual")
    private Short id;

    @Column(name = "LAYER_ID")
    private Short layerId;

    @Column(name = "POSITION_X")
    private String positionX;

    @Column(name = "POSITION_Y")
    private String positionY;

    @Column(name = "TEXT")
    private String text;

    @Column(name = "BACK_COLOR")
    private String backColor;

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
     * @return TEXT
     */
    public String getText() {
        return text;
    }

    /**
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return BACK_COLOR
     */
    public String getBackColor() {
        return backColor;
    }

    /**
     * @param backColor
     */
    public void setBackColor(String backColor) {
        this.backColor = backColor;
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