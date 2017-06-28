package com.wosai.bright.controller_web.system;

import com.wosai.bright.common.Constants;
import com.wosai.bright.mapper.SysConfigMapper;
import com.wosai.bright.model.SysConfig;
import com.wosai.bright.service.SysConfigService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrator on 2017/3/18 0018.
 */
@Controller
@RequestMapping("/web")
public class SysConfigController {

    private static final transient Logger log = LoggerFactory.getLogger(SysConfigController.class);

    @Autowired
    private SysConfigService sysConfigService;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private SysConfigMapper sysConfigMapper;

    /**
     * 不建议,spring的默认注入会使用已经实现IService接口的实例类(如:CountryServiceImpl)
     * 或者发现有多个实现,将会报错:expected single matching bean but found 2: countryService,sysConfigService
     */
//    @SuppressWarnings("SpringJavaAutowiringInspection")
//    @Autowired
//    private IService iService;

    /**
     * 系统参数
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = {"sysConfig"})
    @ResponseBody
    public Object sysConfig(HttpServletRequest request, HttpServletResponse response) {
//        final List<SysConfig> sysConfigList = sysConfigService.selectByExample(new Example(SysConfig.class));
        final List<SysConfig> sysConfigList = sysConfigMapper.selectAll();

        return new HashedMap() {{
            put("object", sysConfigList);
            put("result", Constants.JSON_RESULT_SUCCESS);
            put("message", "success");
        }};
    }

}
