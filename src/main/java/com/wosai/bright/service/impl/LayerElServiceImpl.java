package com.wosai.bright.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wosai.bright.common.Constants;
import com.wosai.bright.mapper.LayerDataMapper;
import com.wosai.bright.mapper.LayerIconMapper;
import com.wosai.bright.mapper.LayerIconStatusMapper;
import com.wosai.bright.mapper.LayerTextMapper;
import com.wosai.bright.model.*;
import com.wosai.bright.service.LayerElService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Administrator on 2017/4/19 0019.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service("layerElService")
public class LayerElServiceImpl extends BaseService<Layer> implements LayerElService {

    @Autowired
    LayerTextMapper layerTextMapper;

    @Autowired
    LayerDataMapper layerDataMapper;

    @Autowired
    LayerIconMapper layerIconMapper;

    @Autowired
    LayerIconStatusMapper layerIconStatusMapper;

    /**
     * 图层元素批量变更
     * @param type
     * @param layerId
     * @param content
     * @return
     */
    @Override
    public Object layerEls_save(Integer type, Integer layerId, String content) {
        JSONArray jsonArray = JSON.parseArray(content);
        switch (type) {
            case Constants.LAYER_TYPE_TEXT:
                for (Object jsonObject : jsonArray.toArray()) {
                    LayerText layerText = JSONObject.toJavaObject((JSONObject) jsonObject, LayerText.class);
                    if (layerText.getId() != null) {
                        layerTextMapper.updateByPrimaryKey(layerText);
                    } else {
                        layerTextMapper.insert(layerText);
                    }
                }
                return new HashedMap() {{
                    put("result", Constants.JSON_RESULT_SUCCESS);
                    put("message", "success");
                }};
            case Constants.LAYER_TYPE_DATA:
                for (Object jsonObject : jsonArray.toArray()) {
                    LayerData layerData = JSONObject.toJavaObject((JSONObject) jsonObject, LayerData.class);
                    if (layerData.getId() != null) {
                        layerDataMapper.updateByPrimaryKey(layerData);
                    } else {
                        layerDataMapper.insert(layerData);
                    }
                }
                return new HashedMap() {{
                    put("result", Constants.JSON_RESULT_SUCCESS);
                    put("message", "success");
                }};
            case Constants.LAYER_TYPE_ICON:
                for (Object jsonObject : jsonArray.toArray()) {
                    LayerIcon layerIcon = JSONObject.toJavaObject((JSONObject) jsonObject, LayerIcon.class);
                    if (layerIcon.getId() != null) {
                        layerIconMapper.updateByPrimaryKey(layerIcon);
                    } else {
                        layerIconMapper.insert(layerIcon);
                    }

                    // 直接量
                    for (Object target : layerIcon.getIconStatus_target().toArray()) {
                        LayerIconStatus layerIconStatus = JSONObject.toJavaObject((JSONObject) target, LayerIconStatus.class);
                        if (layerIconStatus.getExpireTime() != null) {
                            layerIconStatus.setExpireTime(new Date());
                        }

                        if (layerIconStatus.getId() != null) {
                            layerIconStatusMapper.updateByPrimaryKey(layerIconStatus);
                        } else {
                            layerIconStatus.setIconId(layerIcon.getId());
                            layerIconStatus.setCreateTime(new Date());
                            layerIconStatusMapper.insert(layerIconStatus);
                        }
                    }

                    // 区间量
                    for (Object limit : layerIcon.getIconStatus_limit().toArray()) {
                        LayerIconStatus layerIconStatus = JSONObject.toJavaObject((JSONObject) limit, LayerIconStatus.class);
                        if (layerIconStatus.getId() != null) {
                            layerIconStatusMapper.updateByPrimaryKey(layerIconStatus);
                        } else {
                            layerIconStatus.setIconId(layerIcon.getId());
                            layerIconStatus.setCreateTime(new Date());
                            layerIconStatusMapper.insert(layerIconStatus);
                        }
                    }
                }
                return new HashedMap() {{
                    put("result", Constants.JSON_RESULT_SUCCESS);
                    put("message", "success");
                }};
            default:
                return new HashedMap() {{
                    put("result", Constants.JSON_RESULT_FAIL);
                    put("message", "not type");
                }};
        }
    }
}
