package com.wosai.bright.model;

import javax.persistence.*;

@Table(name = "SYS_MENU_DATA")
public class SysMenuData {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select SEQ_SYS_MENU_DATA.nextval from dual")
    private Short id;

    @Column(name = "MENU_ID")
    private Short menuId;

    @Column(name = "KEY")
    private String key;

    @Column(name = "VALUE")
    private String value;

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

    /**
     * @return KEY
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return VALUE
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }
}