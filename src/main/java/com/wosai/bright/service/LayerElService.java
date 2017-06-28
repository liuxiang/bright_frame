package com.wosai.bright.service;

import com.wosai.bright.model.Layer;

/**
 * Created by Administrator on 2017/4/19 0019.
 */
public interface LayerElService extends IService<Layer> {

    /**
     * 图层元素批量变更
     * @param type
     * @param layerId
     * @param content
     * @return
     */
    Object layerEls_save(Integer type, Integer layerId, String content);

}
