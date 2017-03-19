package com.wosai.bright.model;

import javax.persistence.*;

@Table(name = "SYS_MENU")
public class SysMenu {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select SEQ_SYS_MENU.nextval from dual")
    private Short id;

    @Column(name = "PARENT_ID")
    private Short parentId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "URL")
    private String url;

    @Column(name = "PERMS")
    private String perms;

    @Column(name = "TYPE")
    private Short type;

    @Column(name = "ICON")
    private String icon;

    @Column(name = "ORDER_NUM")
    private Short orderNum;

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
     * @return PARENT_ID
     */
    public Short getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     */
    public void setParentId(Short parentId) {
        this.parentId = parentId;
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
     * @return URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return PERMS
     */
    public String getPerms() {
        return perms;
    }

    /**
     * @param perms
     */
    public void setPerms(String perms) {
        this.perms = perms;
    }

    /**
     * @return TYPE
     */
    public Short getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(Short type) {
        this.type = type;
    }

    /**
     * @return ICON
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return ORDER_NUM
     */
    public Short getOrderNum() {
        return orderNum;
    }

    /**
     * @param orderNum
     */
    public void setOrderNum(Short orderNum) {
        this.orderNum = orderNum;
    }
}