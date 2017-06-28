package com.wosai.bright.model;

import javax.persistence.*;

@Table(name = "APP_DAILY_INSPECT_FAULT")
public class AppDailyInspectFault {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select SEQ_APP_DAILY_INSPECT_FAULT.nextval from dual")
    private Short id;

    @Column(name = "MENUID")
    private Short menuid;

    @Column(name = "CODE")
    private String code;

    @Column(name = "NAME")
    private String name;

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
     * @return MENUID
     */
    public Short getMenuid() {
        return menuid;
    }

    /**
     * @param menuid
     */
    public void setMenuid(Short menuid) {
        this.menuid = menuid;
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
}