package com.wosai.bright.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "LAYER")
public class Layer {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select SEQ_LAYER.nextval from dual")
    private Short id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "MENU_ID")
    private Short menuId;

    @Column(name = "BACKGROUND")
    private String background;

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
     * @return BACKGROUND
     */
    public String getBackground() {
        return background;
    }

    /**
     * @param background
     */
    public void setBackground(String background) {
        this.background = background;
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