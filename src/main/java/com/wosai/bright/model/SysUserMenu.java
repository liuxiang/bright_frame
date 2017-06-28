package com.wosai.bright.model;

import javax.persistence.*;

@Table(name = "SYS_USER_MENU")
public class SysUserMenu {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select SEQ_SYS_USER_MENU.nextval from dual")
    private Short id;

    @Column(name = "USER_ID")
    private Short userId;

    @Column(name = "MENU_ID")
    private Short menuId;

    @Column(name = "STATUS")
    private Short status;

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
     * @return USER_ID
     */
    public Short getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Short userId) {
        this.userId = userId;
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

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }
}