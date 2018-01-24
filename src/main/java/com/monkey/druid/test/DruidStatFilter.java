package com.monkey.druid.test;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * @Class Name: DruidStatFilter
 * @Description: TODO
 * @Company bgy:  MK monkey
 * @create: 2018-01-07 18:33
 **/
@WebFilter(filterName = "druidWebStatFilter", urlPatterns = "/*",
        initParams = {
                @WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")// 忽略资源
        }
)
public class DruidStatFilter extends WebStatFilter {
}
