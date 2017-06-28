package com.wosai.bright.model;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/4/18 0018.
 */
@Table(name = "DEVICE_WARNING_LEVER")
public class DeviceWarningLever {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select SEQ_DEVICE_WARNING_LEVER.nextval from dual")
    private Short id;

    @Column(name = "MENU_ID")
    private Short menuId;

    @Column(name = "LEVER")
    private Short lever;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public Short getMenuId() {
        return menuId;
    }

    public void setMenuId(Short menuId) {
        this.menuId = menuId;
    }

    public Short getLever() {
        return lever;
    }

    public void setLever(Short lever) {
        this.lever = lever;
    }
}
