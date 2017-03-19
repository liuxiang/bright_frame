package com.wosai.bright.model;

import javax.persistence.*;

@Table(name = "DEVICE_WARNING_RULE")
public class DeviceWarningRule {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select SEQ_DEVICE_WARNING_RULE.nextval from dual")
    private Short id;

    @Column(name = "MODEL_CODE")
    private String modelCode;

    @Column(name = "MODEL_PROPERTY_KEY")
    private String modelPropertyKey;

    @Column(name = "TARGET_VALUE")
    private String targetValue;

    @Column(name = "UPPER_LIMIT")
    private Short upperLimit;

    @Column(name = "LOWER_LIMIT")
    private Short lowerLimit;

    @Column(name = "FREQUENCY")
    private Short frequency;

    @Column(name = "ISACCUM")
    private Short isaccum;

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
     * @return MODEL_PROPERTY_KEY
     */
    public String getModelPropertyKey() {
        return modelPropertyKey;
    }

    /**
     * @param modelPropertyKey
     */
    public void setModelPropertyKey(String modelPropertyKey) {
        this.modelPropertyKey = modelPropertyKey;
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

    /**
     * @return FREQUENCY
     */
    public Short getFrequency() {
        return frequency;
    }

    /**
     * @param frequency
     */
    public void setFrequency(Short frequency) {
        this.frequency = frequency;
    }

    /**
     * @return ISACCUM
     */
    public Short getIsaccum() {
        return isaccum;
    }

    /**
     * @param isaccum
     */
    public void setIsaccum(Short isaccum) {
        this.isaccum = isaccum;
    }
}