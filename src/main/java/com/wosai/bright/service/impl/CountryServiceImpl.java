package com.wosai.bright.service.impl;

import com.github.pagehelper.PageHelper;
import com.wosai.bright.mapper.CountryMapper;
import com.wosai.bright.model.Country;
import com.wosai.bright.service.CountryService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Map;

/**
 * @author liuzh_3nofxnp
 * @since 2015-09-19 17:17
 */

@Service("countryService")
public class CountryServiceImpl extends BaseService<Country> implements CountryService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private CountryMapper countryMapper;

    @Override
    public List<Country> selectByCountry(Country country, int page, int rows) {
        Example example = new Example(Country.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtil.isNotEmpty(country.getCountryname())) {
            criteria.andLike("countryname", "%" + country.getCountryname() + "%");
        }
        if (StringUtil.isNotEmpty(country.getCountrycode())) {
            criteria.andLike("countrycode", "%" + country.getCountrycode() + "%");
        }
        if (country.getId() != null) {
            criteria.andEqualTo("id", country.getId());
        }
        //分页查询
        PageHelper.startPage(page, rows);
        return selectByExample(example);
    }

    @Override
    public List<Country> selectByCountry_sql(Country country, final int page, final int rows) {
        Map<String, Object> map = new HashedMap();
        map.put("countryname", country.getCountryname());
        map.put("countrycode", country.getCountrycode());

        PageHelper.startPage(page, rows);// 辅助分页(TODO 机制待解)

        List<Country> countryList;
        countryList = countryMapper.selectByCountryQueryModel(map);// ok
//        countryList = countryMapper.selectByCountryQueryModel(country);// ok
//        countryList = countryMapper.selectByCountryQueryModel(map, page, rows);// error
//        countryList = countryMapper.selectByCountryQueryModel(country, page, rows);// error

        return countryList;
    }

}
