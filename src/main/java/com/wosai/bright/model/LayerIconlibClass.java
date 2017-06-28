package com.wosai.bright.model;

import javax.persistence.*;

@Table(name = "LAYER_ICONLIB_CLASS")
public class LayerIconlibClass {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select SEQ_LAYER_ICONLIB_CLASS.nextval from dual")
    private Short id;

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