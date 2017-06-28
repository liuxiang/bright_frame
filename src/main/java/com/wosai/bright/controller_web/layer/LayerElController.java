package com.wosai.bright.controller_web.layer;

import com.wosai.bright.common.Constants;
import com.wosai.bright.mapper.LayerDataMapper;
import com.wosai.bright.mapper.LayerIconMapper;
import com.wosai.bright.mapper.LayerIconStatusMapper;
import com.wosai.bright.mapper.LayerTextMapper;
import com.wosai.bright.model.LayerData;
import com.wosai.bright.model.LayerIcon;
import com.wosai.bright.model.LayerIconStatus;
import com.wosai.bright.model.LayerText;
import com.wosai.bright.service.LayerElService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/19 0019.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Controller
@RequestMapping("/web/layer")
public class LayerElController {

    @Autowired
    LayerTextMapper layerTextMapper;

    @Autowired
    LayerDataMapper layerDataMapper;

    @Autowired
    LayerIconMapper layerIconMapper;

    @Autowired
    LayerIconStatusMapper layerIconStatusMapper;

    @Autowired
    LayerElService layerElService;

    /**
     * 图层元素(文字,数据框,状态图标)
     *
     * @param layerId
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "layerEl")
    public Object layerEl(@RequestParam(required = true) Integer type, @RequestParam(required = true) Integer layerId) {
        switch (type) {
            case Constants.LAYER_TYPE_TEXT:
                return layerText(layerId);
            case Constants.LAYER_TYPE_DATA:
                return layerData(layerId);
            case Constants.LAYER_TYPE_ICON:
                return layerIcon(layerId);
            default:
                return new HashedMap() {{
                    put("result", Constants.JSON_RESULT_FAIL);
                    put("message", "not type");
                }};
        }
    }

    /**
     * 图层元素：新增,修改
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "layerEls/save")
    public Object layerEls_save(@RequestParam(required = true) Integer type, @RequestParam(required = true) Integer layerId
            , @RequestParam(required = true) String content) {

        /* 图层元素批量变更 */
        return layerElService.layerEls_save(type, layerId, content);
    }

    /**
     * 图层元素：新增,修改
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "layerEl/save")
    public Object layerEl_save(@RequestParam(required = true) Integer type
            , LayerText layerText, LayerData layerData, LayerIcon layerIcon) {
        switch (type) {
            case Constants.LAYER_TYPE_TEXT:
                return layerText_save(layerText);
            case Constants.LAYER_TYPE_DATA:
                return layerData_save(layerData);
            case Constants.LAYER_TYPE_ICON:
                return layerIcon_save(layerIcon);
            default:
                return new HashedMap() {{
                    put("result", Constants.JSON_RESULT_FAIL);
                    put("message", "not type");
                }};
        }
    }

    /**
     * 图层元素：删除
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "layerEl/remove")
    public Object layerEl_remove(@RequestParam(required = true) Integer type,
                                 LayerText layerText, LayerData layerData, LayerIcon layerIcon) {
        switch (type) {
            case Constants.LAYER_TYPE_TEXT:
                return layerText_remove(layerText);
            case Constants.LAYER_TYPE_DATA:
                return layerData_remove(layerData);
            case Constants.LAYER_TYPE_ICON:
                return layerIcon_remove(layerIcon);
            default:
                return new HashedMap() {{
                    put("result", Constants.JSON_RESULT_FAIL);
                    put("message", "not type");
                }};
        }
    }

    /*******************************************************************************************/

    /**
     * 图层元素_文字:文字(文字,数据框,状态图标)
     *
     * @param layerId
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "layerText")
    public Object layerText(@RequestParam(required = false) Integer layerId) {
        Example example = new Example(LayerText.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("layerId", layerId);
        criteria.andIsNull("expireTime");
        final List<LayerText> layerTexts = layerTextMapper.selectByExample(example);

        return new HashedMap() {{
            put("object", layerTexts);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /**
     * 图层元素_文字:新增,修改
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "layerText/save")
    public Object layerText_save(LayerText layerText) {
//        LayerText layerText = new LayerText();
        if (layerText.getId() != null) {
            layerTextMapper.updateByPrimaryKey(layerText);
        } else {
            layerTextMapper.insert(layerText);
        }
        return new HashedMap() {{
            put("object", null);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /**
     * 图层元素_文字:删除
     *
     * @param layerText
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "layerText/remove")
    public Object layerText_remove(LayerText layerText) {

        layerTextMapper.deleteByPrimaryKey(layerText);// 删除数据

        return new HashedMap() {{
            put("object", null);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /*******************************************************************************************/

    /**
     * 图层元素_数据框:(文字,数据框,状态图标)
     *
     * @param layerId
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "layerData")
    public Object layerData(@RequestParam(required = false) Integer layerId) {
        Example example = new Example(LayerData.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("layerId", layerId);
        criteria.andIsNull("expireTime");
        final List<LayerData> layerDataList = layerDataMapper.selectByExample(example);

        return new HashedMap() {{
            put("object", layerDataList);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /**
     * 图层元素_数据框:新增,修改
     *
     * @param layerData
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "layerData/save")
    public Object layerData_save(LayerData layerData) {
        if (layerData.getId() != null) {
            layerDataMapper.updateByPrimaryKey(layerData);
        } else {
            layerDataMapper.insert(layerData);
        }
        return new HashedMap() {{
            put("object", null);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }


    /**
     * 图层元素_数据框:删除
     *
     * @param layerData
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "layerData/remove")
    public Object layerData_remove(LayerData layerData) {

        layerDataMapper.deleteByPrimaryKey(layerData);// 删除数据

        return new HashedMap() {{
            put("object", null);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /*******************************************************************************************/

    /**
     * 图层元素_图标:(文字,数据框,状态图标)
     *
     * @param layerId
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "layerIcon")
    public Object layerIcon(@RequestParam(required = false) Integer layerId) {
        Example example = new Example(LayerIcon.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("layerId", layerId);
        criteria.andIsNull("expireTime");
        final List<LayerIcon> layerIconList = layerIconMapper.selectByExample(example);

        // 初始化状态环境
        for (LayerIcon layerIcon : layerIconList) {
            Example _example = new Example(LayerIconStatus.class);
            Example.Criteria _criteria = _example.createCriteria();
            _criteria.andEqualTo("iconId", layerIcon.getId());
            _criteria.andIsNull("expireTime");
//            _example.createCriteria().andIsNotNull("expireTime");
            List<LayerIconStatus> layerIconStatusList = layerIconStatusMapper.selectByExample(_example);

            List iconStatus_target = layerIcon.getIconStatus_target() != null ? layerIcon.getIconStatus_target() : new ArrayList();
            List iconStatus_limit = layerIcon.getIconStatus_limit() != null ? layerIcon.getIconStatus_limit() : new ArrayList();

            for (LayerIconStatus layerIconStatus : layerIconStatusList) {
                if (layerIconStatus.getTargetValue() != null) {
                    iconStatus_target.add(layerIconStatus);
                }
                if (layerIconStatus.getLowerLimit() != null && layerIconStatus.getUpperLimit() != null) {
                    iconStatus_limit.add(layerIconStatus);
                }
            }

            layerIcon.setIconStatus_target(iconStatus_target);
            layerIcon.setIconStatus_limit(iconStatus_limit);
        }

        return new HashedMap() {{
            put("object", layerIconList);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /**
     * 图层元素_图标:新增,修改
     *
     * @param layerIcon
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "layerIcon/save")
    public Object layerIcon_save(LayerIcon layerIcon) {
        if (layerIcon.getId() != null) {
            layerIconMapper.updateByPrimaryKey(layerIcon);
        } else {
            layerIconMapper.insert(layerIcon);
        }
        return new HashedMap() {{
            put("object", null);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }


    /**
     * 图层元素_图标:删除
     *
     * @param layerIcon
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "layerIcon/remove")
    public Object layerIcon_remove(LayerIcon layerIcon) {

        layerIconMapper.deleteByPrimaryKey(layerIcon);// 删除数据

        return new HashedMap() {{
            put("object", null);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /*******************************************************************************************/

    /**
     * 图层元素_图标状态:(文字,数据框,状态图标)
     *
     * @param layerId
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "layerIconStatus")
    public Object layerIconStatus(@RequestParam(required = false) Integer layerId) {
        Example example = new Example(LayerIconStatus.class);
        example.createCriteria().andEqualTo("layerId", layerId);
        final List<LayerIconStatus> layerIconStatusList = layerIconStatusMapper.selectByExample(example);

        return new HashedMap() {{
            put("object", layerIconStatusList);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /**
     * 图层元素_图标状态:新增,修改
     *
     * @param layerIconStatus
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "layerIconStatus/save")
    public Object layerIconStatus_save(LayerIconStatus layerIconStatus) {
        if (layerIconStatus.getId() != null) {
            layerIconStatusMapper.updateByPrimaryKey(layerIconStatus);
        } else {
            layerIconStatusMapper.insert(layerIconStatus);
        }
        return new HashedMap() {{
            put("object", null);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }


    /**
     * 图层元素_图标状态:删除
     *
     * @param layerIconStatus
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "layerIconStatus/remove")
    public Object layerIconStatus_remove(LayerIconStatus layerIconStatus) {

        layerIconStatusMapper.deleteByPrimaryKey(layerIconStatus);// 删除数据

        return new HashedMap() {{
            put("object", null);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }
}
