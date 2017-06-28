package com.wosai.bright.controller_web.layer;

import com.wosai.bright.common.Constants;
import com.wosai.bright.mapper.*;
import com.wosai.bright.model.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/4/8 0008.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Controller
@RequestMapping("/web/layer")
public class LayerController {

    @Autowired
    LayerMapper layerMapper;

    @Autowired
    LayerTextMapper layerTextMapper;

    @Autowired
    LayerDataMapper layerDataMapper;

    @Autowired
    LayerIconMapper layerIconMapper;

    @Autowired
    LayerIconStatusMapper layerIconStatusMapper;

    @Autowired
    DeviceMapper deviceMapper;

    @Autowired
    DevicePropertyMapper devicePropertyMapper;

    @Autowired
    LayerIconlibMapper layerIconlibMapper;

    @Autowired
    LayerIconlibClassMapper layerIconlibClassMapper;

    /**
     * 获取菜单下图层
     *
     * @param menuId
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "")
    public Object layer(@RequestParam(required = false) Integer menuId) {
        Example example = new Example(Layer.class);
        example.createCriteria().andEqualTo("menuId", menuId);
        final List<Layer> layers = layerMapper.selectByExample(example);

        return new HashedMap() {{
            put("object", layers);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /**
     * 获取菜单下设备
     *
     * @param menuId
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "device")
    public Object layer_device(@RequestParam(required = false) Integer menuId) {
        Example example = new Example(Device.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("menuId", menuId);
        criteria.andIsNull("expireTime");
        final List<Device> devices = deviceMapper.selectByExample(example);

        for (Device device : devices) {
            Example _example = new Example(DeviceProperty.class);
            Example.Criteria _criteria = _example.createCriteria();
            _criteria.andEqualTo("deviceId", device.getId());
            List<DeviceProperty> deviceProperties = devicePropertyMapper.selectByExample(_example);
            device.setDevicePropertyList(deviceProperties);// 添加属性
        }

        return new HashedMap() {{
            put("object", devices);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /**
     * 图层管理：新增,修改
     *
     * @param layer
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "save")
    public Object layer_save(
            HttpServletRequest request,
            HttpServletResponse response,
            Layer layer,
            @RequestParam(required = false, value = "file") MultipartFile file) throws IOException {
        if (file != null) {
            String aa = file.getContentType();
            String[] fileType = aa.split("/");
            if (!"image".equals(fileType[0])) {
                return new HashedMap() {{
                    put("result", Constants.JSON_RESULT_FAIL);
                    put("message", "上传的文件类型必须为图片");
                }};
            }
            String picUrl = uploadFileLocal(request, file, "image/layer");
            layer.setBackground(picUrl);
        }
        if (layer.getId() != null) {
            layerMapper.updateByPrimaryKey(layer);
        } else {
            layerMapper.insert(layer);
        }

        return new HashedMap() {{
            put("object", null);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /**
     * 本地上传文件
     *
     * @param request
     * @param file
     * @param fileDir
     * @return
     * @throws IOException
     */
    public String uploadFileLocal(HttpServletRequest request, MultipartFile file, String fileDir) throws IOException {
        String realPath = request.getSession().getServletContext().getRealPath(fileDir);
        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        int rannum = (int) (new Random().nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
        String imageFile = time + "_" + rannum + "_" + file.getOriginalFilename();

        File saveFile = new File(realPath, imageFile);
        FileUtils.copyInputStreamToFile(file.getInputStream(), saveFile);

        return fileDir + "/" + imageFile;
    }

    /**
     * 图层管理：删除
     *
     * @param layer
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "remove")
    public Object layer_remove(Layer layer) {

        layerMapper.deleteByPrimaryKey(layer);// 删除数据

        return new HashedMap() {{
            put("object", null);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /**
     * 图层拷贝
     *
     * @param layer
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "copy")
    public Object layer_copy(Layer layer) {
        Layer layer1 = layerMapper.selectOne(layer);
        layer1.setId(null);
        layer1.setName(layer1.getName() + "_copy");
        layerMapper.insert(layer1);

        // EL同步:Text
        LayerText layerTextCondition = new LayerText();
        layerTextCondition.setLayerId(layer.getId());
        List<LayerText> layerTexts = layerTextMapper.select(layerTextCondition);
        for (LayerText layerText : layerTexts) {
            layerText.setId(null);
            layerText.setLayerId(layer1.getId());
            layerTextMapper.insert(layerText);
        }

        // EL同步:data
        LayerData layerDataCondition = new LayerData();
        layerDataCondition.setLayerId(layer.getId());
        List<LayerData> layerDatas = layerDataMapper.select(layerDataCondition);
        for (LayerData layerData : layerDatas) {
            layerData.setId(null);
            layerData.setLayerId(layer1.getId());
            layerDataMapper.insert(layerData);
        }

        // EL同步:icon
        LayerIcon layerIconCondition = new LayerIcon();
        layerIconCondition.setLayerId(layer.getId());
        List<LayerIcon> layerIcons = layerIconMapper.select(layerIconCondition);
        for (LayerIcon layerIcon : layerIcons) {
            short iconId = layerIcon.getId();
            layerIcon.setId(null);
            layerIcon.setLayerId(layer1.getId());
            layerIconMapper.insert(layerIcon);

            // EL同步:icon_status
            LayerIconStatus layerIconStatusCondition = new LayerIconStatus();
            layerIconStatusCondition.setIconId(iconId);
            List<LayerIconStatus> layerIconStatuses = layerIconStatusMapper.select(layerIconStatusCondition);
            for (LayerIconStatus layerIconStatus : layerIconStatuses) {
                layerIconStatus.setId(null);
                layerIconStatus.setIconId(layerIcon.getId());
                layerIconStatusMapper.insert(layerIconStatus);
            }
        }

        return new HashedMap() {{
//            put("object", layer1);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    // 设备统改


    /*******************************************************************************************/

    // 图层元素管理,转移至LayerElConroller

    /*******************************************************************************************/

    /**
     * 查询图标库
     *
     * @param classId
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "iconlib")
    public Object layer_iconlib(@RequestParam(required = false) Integer classId) {

        Example example = new Example(LayerIconlib.class);
        example.createCriteria().andEqualTo("classId", classId);
        final List<LayerIconlib> layerIconlibs = layerIconlibMapper.selectByExample(example);

        return new HashedMap() {{
            put("object", layerIconlibs);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /**
     * 变更图标库图标
     * (前端请求需要注意,文件字段名必须为file,与此声明对应/如不声明则需与字段名一致-否则无法获取到文件对象)
     *
     * @param layerIconlib
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "iconlib/save")
    public Object layer_iconlib_save(
            HttpServletRequest request,
            HttpServletResponse response,
            LayerIconlib layerIconlib,
            @RequestParam(required = false, value = "file") MultipartFile file) throws IOException {
        if (file != null) {
            String aa = file.getContentType();
            String[] fileType = aa.split("/");
            if (!"image".equals(fileType[0])) {
                return new HashedMap() {{
                    put("result", Constants.JSON_RESULT_FAIL);
                    put("message", "上传的文件类型必须为图片");
                }};
            }
            String picUrl = uploadFileLocal(request, file, "image/layerIcon");
            layerIconlib.setIconSrc(picUrl);
        }
        if (layerIconlib.getId() != null) {
            layerIconlibMapper.updateByPrimaryKey(layerIconlib);
        } else {
            layerIconlibMapper.insert(layerIconlib);
        }
        return new HashedMap() {{
            put("object", null);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /**
     * 图标库图标管理：删除
     *
     * @param layerIconlib
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "iconlib/remove")
    public Object layer_iconlib_remove(LayerIconlib layerIconlib) {

        layerIconlibMapper.deleteByPrimaryKey(layerIconlib);// 删除数据

        return new HashedMap() {{
            put("object", null);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /*******************************************************************************************/

    /***
     * 查询图标库分类
     * @param classId
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "iconlib_class")
    public Object layer_iconlib_class(@RequestParam(required = false) Integer classId) {

        final List<LayerIconlibClass> layerIconlibClasss = layerIconlibClassMapper.selectAll();
        return new HashedMap() {{
            put("object", layerIconlibClasss);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /**
     * 变更图标库分类
     *
     * @param layerIconlibClass
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "iconlib_class/save")
    public Object layer_iconlib_class_save(LayerIconlibClass layerIconlibClass) {

        if (layerIconlibClass.getId() != null) {
            layerIconlibClassMapper.updateByPrimaryKey(layerIconlibClass);
        } else {
            layerIconlibClassMapper.insert(layerIconlibClass);
        }

        return new HashedMap() {{
            put("object", null);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /**
     * 变更图标库分类
     *
     * @param layerIconlibClass
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "iconlib_class/remove")
    public Object layer_iconlib_class_remove(LayerIconlibClass layerIconlibClass) {

        layerIconlibClassMapper.deleteByPrimaryKey(layerIconlibClass);// 删除数据

        return new HashedMap() {{
            put("object", null);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }
}
