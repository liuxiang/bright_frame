package com.wosai.bright.controller_web.device;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wosai.bright.common.Constants;
import com.wosai.bright.mapper.DevicePropertyMapper;
import com.wosai.bright.mapper.DeviceRepairMapper;
import com.wosai.bright.model.Device;
import com.wosai.bright.model.DeviceProperty;
import com.wosai.bright.model.DeviceRepair;
import com.wosai.bright.model.Layer;
import com.wosai.bright.service.DeviceService;
import com.wosai.bright.service.SysUserService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/23 0023.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Controller
@RequestMapping("/web")
public class DeviceController {

    private static final transient Logger log = LoggerFactory.getLogger(DeviceController.class);

    @Autowired
    private DeviceService deviceService;

    @Autowired
    DevicePropertyMapper devicePropertyMapper;

    @Autowired
    private DeviceRepairMapper deviceRepairMapper;

    @Autowired
    private SysUserService userService;

    /**
     * 查看设备列表
     *
     * @param request
     * @param response
     * @param page     页码
     * @param rows     每页个数
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "device")
    public Object findDevice(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer rows) {
        Map<String, Object> condition = (Map<String, Object>) JSON.parse(content);
        PageHelper.startPage(page, rows);
        final List<Map<String, Object>> list = deviceService.findDevice(condition);

        final PageInfo pageInfo = new PageInfo(list);

        return new HashedMap() {{
            put("object", list);
            put("totalRec", pageInfo.getTotal());
            put("curRec", pageInfo.getSize());
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /**
     * 添加新设备
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "device/create")
    public Object createDevice(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam String modelCode,
            @RequestParam String name,
            @RequestParam String position,
            @RequestParam String ip,
            @RequestParam Integer menuId,
            @RequestParam String ucode) {
        Device device = deviceService.getDeviceByUcode(ucode);
        if(device != null){
            return new HashedMap() {{
                put("result", Constants.JSON_RESULT_FAIL);
                put("message", "编码已存在");
            }};
        }
        device = new Device();
        device.setCreateTime(new Date());
        device.setName(name);
        device.setPosition(position);
        device.setModelCode(modelCode);
        device.setIp(ip);
        device.setUcode(ucode);
        if (menuId != null){
            device.setMenuId(menuId.shortValue());
        }else {
            device.setMenuId(null);
        }
        deviceService.save(device);

        return new HashedMap() {{
//            put("object", device);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /**
     * 查看维修信息
     *
     * @param request
     * @param response
     * @param page
     * @param rows
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "device/repairList")
    public Object findDeviceRepair(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer rows) {
//        final List<DeviceRepair> list = deviceRepairMapper.selectAll();

        Map<String, Object> condition = (Map<String, Object>) JSON.parse(content);
        PageHelper.startPage(page, rows);
        final List<DeviceRepair> list = deviceRepairMapper.findDeviceRepair(condition);
        final PageInfo pageInfo = new PageInfo(list);

//        for (DeviceRepair obj:list) {
//            Device device = deviceService.getDevice(obj.getDeviceId());
//            obj.setDevice(device);
//            SysUser user = userService.getUser(obj.getUserId());
//            user.setPassword(null);
//            obj.setUser(user);
//        }

        return new HashedMap() {{
            put("object", list);
            put("totalRec", pageInfo.getTotal());
            put("curRec", pageInfo.getSize());
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /**
     *添加需维修的设备
     * @param request
     * @param response
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST,value = {"device/repair"})
    public Object addDeviceRepair(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(required = false) String content)
    {
        Map<String,Object> condition = JSON.parseObject(content);
        Integer deviceId = (Integer)condition.get("deviceId");
        Integer userId = (Integer)condition.get("userId");

        /*检测该设备有无报修信息*/
//        Map<String,Object> noRepairCondition = new HashMap<String,Object>();
//        noRepairCondition.put("deviceId",deviceId);
//        noRepairCondition.put("status",1);
//        DeviceRepair deviceRepair = deviceRepairMapper.getDeviceRepair(noRepairCondition);

        Example _example = new Example(DeviceRepair.class);
        Example.Criteria _criteria = _example.createCriteria();
        _criteria.andEqualTo("deviceId", deviceId);
        _criteria.andEqualTo("status", 1);
        List<DeviceRepair> deviceRepairs = deviceRepairMapper.selectByExample(_example);
//        DeviceRepair deviceRepair = deviceRepairs.get(0);

//        DeviceRepair deviceRepair_ = new DeviceRepair();
//        deviceRepair_.setDeviceId(deviceId.shortValue());
//        deviceRepair_.setStatus((short)1);
//        DeviceRepair deviceRepair = deviceRepairMapper.selectOne(deviceRepair_);

        if(deviceRepairs.size() > 0){                                                               //已报修过，返回...
            return new HashedMap() {{
                put("result", Constants.JSON_RESULT_FAIL);
                put("message", "已报修过，等待处理中...");
            }};
        }
        /*创建*/
        DeviceRepair deviceRepair = new DeviceRepair();                                                      //未报修过，创建维修新对象
        if(deviceId != null){
            deviceRepair.setDeviceId(deviceId.shortValue());
        }
        if(userId != null){
            deviceRepair.setUserId(userId.shortValue());
        }
        deviceRepair.setStatus((short)1);                                   //状态码（1：未处理；2：待处理；3：已处理）
        deviceRepair.setCreateTime(new Date());
        /*保存*/
        deviceRepairMapper.insert(deviceRepair);

        return new HashedMap() {{
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    @ResponseBody
    @RequestMapping(method=RequestMethod.POST,value={"device/repair/update/{deviceId}"})
    public Object updateDeviceRepair(HttpServletRequest request,
                                     HttpServletResponse rsponse,
                                     @PathVariable Integer deviceId,
                                     @RequestParam(required = false) String content)
    {
        Map<String,Object> condition = JSON.parseObject(content);
        DeviceRepair deviceRepair = deviceRepairMapper.selectByPrimaryKey(deviceId.shortValue());
        Integer status = (Integer) condition.get("status");
        if(status != null){
            deviceRepair.setStatus(status.shortValue());
        }
        deviceRepairMapper.updateByPrimaryKey(deviceRepair);
        return new HashedMap() {{
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /**
     * 获取设备型号
     *
     * @param request
     * @param response
     * @param page
     * @param rows
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = {"device/model"})
    public Object findDeviceModelCode(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer rows) {
        PageHelper.startPage(page, rows);
        final List<String> list = deviceService.findDeviceModelCode();
        final PageInfo pageInfo = new PageInfo(list);

        return new HashedMap() {{
            put("object", list);
            put("totalRec", pageInfo.getTotal());
            put("curRec", pageInfo.getSize());
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /**
     * 更新设备信息
     * @param request
     * @param response
     * @param id
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "device/update/{id}")
    public Object updateDevice(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable Integer id,
            @RequestParam(required = false) String content
    ) {
        Map<String , Object> objectMap = JSON.parseObject(content);
        Device device = deviceService.selectByKey(id.shortValue());
        device.setModelCode((String) objectMap.get("modelCode"));
        device.setName((String) objectMap.get("name"));
        device.setPosition((String) objectMap.get("position"));
        device.setUcode((String) objectMap.get("ucode"));
        Integer cameraDeviceId = (Integer) objectMap.get("cameraDeviceId");
        if (cameraDeviceId != null ){
            device.setCameraDeviceId(cameraDeviceId.shortValue());
        }else {
            device.setCameraDeviceId(null);
        }
        device.setIp((String) objectMap.get("ip"));
        Integer menuId = (Integer) objectMap.get("menuId");
        if (menuId != null){
            device.setMenuId(menuId.shortValue());
        }else {
            device.setMenuId(null);
        }
        deviceService.updateAll(device);

        return new HashedMap() {{
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /**
     * 删除设备
     * @param request
     * @param response
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST , value = "device/delete/{id}")
    public Object deleteDevice(HttpServletRequest request , HttpServletResponse response,@PathVariable Integer id){
        Device device = deviceService.selectByKey(id.shortValue());
        device.setExpireTime(new Date());
        deviceService.updateAll(device);

        return new HashedMap() {{
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }


    /***********************************************************/

    /**
     * 获取设备属性
     * @param deviceId
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "deviceProperty")
    public Object deviceProperty(@RequestParam(required = false) Integer deviceId){

        Example example = new Example(DeviceProperty.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("deviceId", deviceId);
        final List<DeviceProperty> deviceProperties = devicePropertyMapper.selectByExample(example);

        return new HashedMap() {{
            put("object", deviceProperties);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

//    /**
//     * 设备属性更新
//     * @param deviceProperty
//     * @return
//     * @throws IOException
//     */
//    @ResponseBody
//    @RequestMapping(method = RequestMethod.POST, value = "devicePropety/save")
//    public Object deviceProperty_save(DeviceProperty deviceProperty) throws IOException {
//
//        if (deviceProperty.getId() != null) {
//            devicePropertyMapper.updateByPrimaryKey(deviceProperty);
//        } else {
//            devicePropertyMapper.insert(deviceProperty);
//        }
//
//        return new HashedMap() {{
//            put("result", Constants.JSON_RESULT_SUCCESS);
//            put("message", "success");
//        }};
//    }
//
//    /**
//     * 图层管理：删除
//     *
//     * @param deviceProperty
//     * @return
//     */
//    @ResponseBody
//    @RequestMapping(method = RequestMethod.POST, value = "devicePropety/remove")
//    public Object deviceProperty_remove(DeviceProperty deviceProperty) {
//
//        devicePropertyMapper.deleteByPrimaryKey(layer);// 删除数据
//
//        return new HashedMap() {{
//            put("object", null);
//            put("result", Constants.JSON_RESULT_SUCCESS);
//            put("message", "success");
//        }};
//    }


}
