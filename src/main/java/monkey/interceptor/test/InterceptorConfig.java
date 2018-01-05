package monkey.interceptor.test;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @program: demo
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2018-01-05 17:55
 **/
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserSecurityInterceptor()).addPathPatterns("/springdata/*");//括号内是拦截路径
    }
}
