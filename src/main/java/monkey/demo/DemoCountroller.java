package monkey.demo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/houzi")
public class DemoCountroller {
    /**
     *
     */
    @RequestMapping("/hello")
    public String index() {
        return "我是一个小小小小小鸟";
    }


}
