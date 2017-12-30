package monkey.demo;

import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/houzi")
@EnableAutoConfiguration
public class DemoCountroller {
    /**
     *
     */
    @RequestMapping("/mk")
    //@ApiOperation(value = “接口说明”, httpMethod = “接口请求方式”, response = “接口返回参数类型”, notes = “接口发布说明”；其他参数可参考源码；
    @ApiOperation(value = "mk",httpMethod ="GET", response = String.class,notes = "index")
    public String index() {
        return "我是一个小小小小小鸟";
    }

@RequestMapping("/monkey")
//@ApiOperation(value = “接口说明”, httpMethod = “接口请求方式”, response = “接口返回参数类型”, notes = “接口发布说明”；其他参数可参考源码；
@ApiOperation(value = "monkey",httpMethod ="GET", response = String.class,notes = "index")
    public String index2() {
        return "我是一个小小小小小鸟2";
    }


}
