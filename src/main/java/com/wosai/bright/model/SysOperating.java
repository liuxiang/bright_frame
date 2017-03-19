package com.wosai.bright.model;

import javax.persistence.*;

@Table(name = "SYS_OPERATING")
public class SysOperating {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select SEQ_SYS_OPERATING.nextval from dual")
    private Short id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "REMARK")
    private String remark;

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
}