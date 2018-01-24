package com.monkey.exception.test;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


//如果返回的为json数据或其它对象，添加该注解
//@ResponseBody
@ControllerAdvice(basePackages = {"monkey.demo"}) //可以单独出来某个包所有的controller
public class GlobalDefaultExceptionHandler {


    @ExceptionHandler({NullPointerException.class, NumberFormatException.class})
    public ModelAndView nullPointErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        //异常捕获到需要处理的业务
        System.out.println("捕获成功");
        return null;
    }
}
