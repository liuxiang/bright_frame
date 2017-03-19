package com.wosai.bright.controller.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2017/3/9 0009.
 */
@Controller
@RequestMapping("/app")
public class PageController {

    @RequestMapping("{url}.html")
    public String page_app(@PathVariable("url") String url) {
        return "app/" + url + ".html";
    }

}
