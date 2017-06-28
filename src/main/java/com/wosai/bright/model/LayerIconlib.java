package com.wosai.bright.model;

import javax.persistence.*;

@Table(name = "LAYER_ICONLIB")
public class LayerIconlib {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select SEQ_LAYER_ICONLIB.nextval from dual")
    private Short id;

    @Column(name = "CLASS_ID")
    private Short classId;

    @Column(name = "ICON_SRC")
    private String iconSrc;

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
     * @return CLASS_ID
     */
    public Short getClassId() {
        return classId;
    }

    /**
     * @param classId
     */
    public void setClassId(Short classId) {
        this.classId = classId;
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
}