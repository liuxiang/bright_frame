package com.wosai.bright.controller.demo;

import com.github.pagehelper.PageInfo;
import com.wosai.bright.model.Country;
import com.wosai.bright.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author liuzh_3nofxnp
 * @since 2015-09-19 17:15
 */
@Controller
@RequestMapping("/demo")
public class CountryController {

    private static final transient Logger log = LoggerFactory.getLogger(CountryController.class);

    @Autowired
    private CountryService countryService;

//    private String page_list = "index";
//    private String redirect_list = "redirect:list";

    private String page_list = "index.jsp";
    private String page_view = "view.jsp";
    private String redirect_list = "redirect:list";

    @RequestMapping(value = {"list", "index", "index.html", ""})
    public ModelAndView getList(Country country,
                                @RequestParam(required = false, defaultValue = "1") int page,
                                @RequestParam(required = false, defaultValue = "10") int rows) {
        ModelAndView result = new ModelAndView(page_list);
        List<Country> countryList = countryService.selectByCountry(country, page, rows);
//        List<Country> countryList = countryService.selectByCountry_sql(country, page, rows);
        result.addObject("pageInfo", new PageInfo<Country>(countryList));
        result.addObject("queryParam", country);
        result.addObject("page", page);
        result.addObject("rows", rows);
        return result;
    }

    @RequestMapping(value = {"list_sql"})
    public ModelAndView getList_sql(Country country,
                                    @RequestParam(required = false, defaultValue = "1") int page,
                                    @RequestParam(required = false, defaultValue = "10") int rows) {
        ModelAndView result = new ModelAndView(page_list);
//        List<Country> countryList = countryService.selectByCountry(country, page, rows);
        List<Country> countryList = countryService.selectByCountry_sql(country, page, rows);
        result.addObject("pageInfo", new PageInfo<Country>(countryList));
        result.addObject("queryParam", country);
        result.addObject("page", page);
        result.addObject("rows", rows);
        return result;
    }

    @RequestMapping(value = "view", method = RequestMethod.GET)
    public ModelAndView view(Country country) {
        ModelAndView result = new ModelAndView(page_view);
        if (country.getId() != null) {
            country = countryService.selectByKey(country.getId());
        }
        result.addObject("country", country);
        return result;
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ModelAndView save(Country country) {
        ModelAndView result = new ModelAndView(redirect_list);
        if (country.getId() != null) {
            countryService.updateAll(country);
        } else {
            countryService.save(country);
        }
        return result;
    }

    @RequestMapping("delete")
    public String delete(Integer id) {
        countryService.delete(id);
        return redirect_list;
    }

}
