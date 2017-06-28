package com.wosai.bright.controller_web.app_dailyInspect;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wosai.bright.common.Constants;
import com.wosai.bright.common.utils.DateUtils;
import com.wosai.bright.mapper.AppDailyInspectFaultMapper;
import com.wosai.bright.mapper.AppDailyInspectMapper;
import com.wosai.bright.mapper.DeviceMapper;
import com.wosai.bright.model.AppDailyInspect;
import com.wosai.bright.model.AppDailyInspectFault;
import com.wosai.bright.model.Device;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 巡检
 * Created by Administrator on 2017/5/4 0004.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Controller
@RequestMapping("/web/dailyInspect")
public class AppDailyInspectController {

    @Autowired
    AppDailyInspectMapper appDailyInspectMapper;

    @Autowired
    AppDailyInspectFaultMapper appDailyInspectFaultMapper;

    @Autowired
    DeviceMapper deviceMapper;

    /**
     * 巡检数据_查询
     *
     * @param appDailyInspect
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "")
    public Object dailyInspect(HttpServletRequest request, AppDailyInspect appDailyInspect
            , @RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "1000") Integer rows) {

        //  final List<AppDailyInspect> appDailyInspects = appDailyInspectMapper.select(appDailyInspect);
        Map<String, Object> condition = new HashedMap();
        condition.put("menuId", request.getParameter("menuId"));
        condition.put("date", request.getParameter("date"));

        condition.put("menuId", request.getParameter("menuId"));
        condition.put("deviceName", request.getParameter("deviceName"));
        condition.put("modelCode", request.getParameter("modelCode"));
        condition.put("beginTime", request.getParameter("beginTime"));
        condition.put("endTime", request.getParameter("endTime"));

        PageHelper.startPage(page, rows);
        final List<Map> appDailyInspectMaps = appDailyInspectMapper.selectDeviceDailyInspect(condition);
        final PageInfo pageInfo = new PageInfo(appDailyInspectMaps);

        return new HashedMap() {{
            put("object", appDailyInspectMaps);
            put("pageInfo", pageInfo);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /**
     * 巡检数据_新增/修改
     *
     * @param appDailyInspect
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "save")
    public Object dailyInspect_save(AppDailyInspect appDailyInspect) {
        if (appDailyInspect.getId() != null) {
            appDailyInspectMapper.updateByPrimaryKey(appDailyInspect);
        } else {
            Example example = new Example(AppDailyInspect.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("deviceId", appDailyInspect.getDeviceId());
            criteria.andBetween("createTime", DateUtils.getDateStart(new Date()), DateUtils.getDateEnd(new Date()));
            List<AppDailyInspect> appDailyInspects = appDailyInspectMapper.selectByExample(example);
            if (appDailyInspects.size() > 0) {
                // 此设备今日已巡检(或考虑更新巡检时间)
                AppDailyInspect appDailyInspect_ = appDailyInspects.get(0);
                appDailyInspect_.setFaultCode(appDailyInspect.getFaultCode());
                appDailyInspect_.setRemark(appDailyInspect.getRemark());
                appDailyInspect_.setCreateTime(new Date());// 更新时间
                appDailyInspectMapper.updateByPrimaryKey(appDailyInspect_);
            } else {
                appDailyInspect.setCreateTime(new Date());
                Device device = deviceMapper.selectByPrimaryKey(appDailyInspect.getDeviceId());
                appDailyInspect.setMenuId(device.getMenuId());
                appDailyInspectMapper.insert(appDailyInspect);
            }
        }

        return new HashedMap() {{
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /**
     * 巡检数据_删除
     *
     * @param appDailyInspect
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "remove")
    public Object dailyInspect_remove(AppDailyInspect appDailyInspect) {
        appDailyInspectMapper.deleteByPrimaryKey(appDailyInspect);// 删除数据

        return new HashedMap() {{
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /**
     * 巡检统计
     *
     * @param menuId
     * @param date
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "count")
    public Object dailyInspect_count(@RequestParam(required = false) String menuId,
                                     @RequestParam(required = false) String date) throws ParseException {

        Example example = new Example(Device.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("menuId", menuId);
        final int num_all = deviceMapper.selectCountByExample(example);

        Example _example = new Example(AppDailyInspect.class);
        Example.Criteria _criteria = _example.createCriteria();
        _criteria.andEqualTo("menuId", menuId);
        if (!StringUtils.isEmpty(date)) {
            Date _date = DateFormat.getDateInstance().parse(date);
            _criteria.andBetween("createTime", DateUtils.getDateStart(_date), DateUtils.getDateEnd(_date));
        }
        final int num_ok = appDailyInspectMapper.selectCountByExample(_example);

        return new HashedMap() {{
            put("object", new HashedMap() {{
                put("num_ok", num_ok);
                put("num_all", num_all);
            }});
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

    /**
     * 巡检故障类别_查询
     *
     * @param appDailyInspectFault
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "fault")
    public Object dailyInspect_fault(AppDailyInspectFault appDailyInspectFault) {
        final List<AppDailyInspectFault> appDailyInspectFaults = appDailyInspectFaultMapper.select(appDailyInspectFault);

        return new HashedMap() {{
            put("object", appDailyInspectFaults);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

}
