package monkey.retry.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Class Name: TestController
 * @Description: TODO
 * @Company bgy:  MK monkey
 * @create: 2018-01-07 18:53
 **/
@RestController
@RequestMapping("/retry")
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private RemoteService remoteService;

    @RequestMapping("/test")
    public String login() throws Exception {
        remoteService.call();
        return String.valueOf("11");
    }
}
