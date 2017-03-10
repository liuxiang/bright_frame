/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.wosai.bright.mapper;


import com.wosai.bright.common.MyMapper;
import com.wosai.bright.model.Country;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CountryMapper extends MyMapper<Country> {

    /**
     * SQL查询
     *
     * @param map
     * @return
     */
    List<Country> selectByCountryQueryModel(Map<String, Object> map);// ok

    List<Country> selectByCountryQueryModel(Country country);// ok

    /**
     * 下文两个方法调用会异常:
     * org.mybatis.spring.MyBatisSystemException: nested exception is org.apache.ibatis.binding.BindingException:
     * Parameter 'countryname' not found. Available parameters are [0, pageSize, param3, pageNum, param1, param2]
     *
     * 参考:
     * 调用方式,第四种 https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/HowToUse.md
     */

    List<Country> selectByCountryQueryModel(Map<String, Object> map, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize);// no

    List<Country> selectByCountryQueryModel(Country country, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize);// no


}