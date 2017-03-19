package com.wosai.bright.model;

import javax.persistence.*;

@Table(name = "LAYER_ICON_STATUS")
public class LayerIconStatus {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select SEQ_LAYER_ICON_STATUS.nextval from dual")
    private Short id;

    @Column(name = "ICON_ID")
    private Short iconId;

    @Column(name = "ICON_SRC")
    private String iconSrc;

    @Column(name = "TARGET_VALUE")
    private String targetValue;

    @Column(name = "UPPER_LIMIT")
    private Short upperLimit;

    @Column(name = "LOWER_LIMIT")
    private Short lowerLimit;

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
     * @return ICON_ID
     */
    public Short getIconId() {
        return iconId;
    }

    /**
     * @param iconId
     */
    public void setIconId(Short iconId) {
        this.iconId = iconId;
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
     * @return TARGET_VALUE
     */
    public String getTargetValue() {
        return targetValue;
    }

    /**
     * @param targetValue
     */
    public void setTargetValue(String targetValue) {
        this.targetValue = targetValue;
    }

    /**
     * @return UPPER_LIMIT
     */
    public Short getUpperLimit() {
        return upperLimit;
    }

    /**
     * @param upperLimit
     */
    public void setUpperLimit(Short upperLimit) {
        this.upperLimit = upperLimit;
    }

    /**
     * @return LOWER_LIMIT
     */
    public Short getLowerLimit() {
        return lowerLimit;
    }

    /**
     * @param lowerLimit
     */
    public void setLowerLimit(Short lowerLimit) {
        this.lowerLimit = lowerLimit;
    }
}