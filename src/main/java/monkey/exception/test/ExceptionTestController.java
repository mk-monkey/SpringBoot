package monkey.exception.test;

import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 猴子哥
 * @date 2017/1/24.
 */
@RestController
@RequestMapping(value = "/mkmonkey")
@EnableAutoConfiguration
public class ExceptionTestController {

    @RequestMapping("/houzi")
    @ApiOperation(value = "houzi", httpMethod = "GET", response = String.class, notes = "index")
    public String index() {
        String testStr = null;
        testStr.split("1");
        return "Hello 猴子哥...";
    }

    @RequestMapping("/test")
    @ApiOperation(value = "test", httpMethod = "GET", response = String.class, notes = "index")
    public String test() {
        return "Hello 猴子哥...";
    }
}