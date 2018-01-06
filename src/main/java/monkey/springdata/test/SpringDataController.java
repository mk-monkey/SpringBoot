package monkey.springdata.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Class Name: SpringDataController
 * @Description: TODO
 * @Company bgy:  MK monkey
 * @create: 2018-01-06 22:30
 **/
@RestController
@RequestMapping("/springdata")
public class SpringDataController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/test")
    public String index(){
        MkTest mkTest = new MkTest();
        mkTest.setId(2);
        mkTest.setName("my Name is smill monkey");
        //userRepository.save(mkTest);
        //select * from ay_test where id = '60' and name = "al"
       List<MkTest> mkTestList =  userRepository.findByIdAndName(1,"my Name is monkey");
        return "Hello Mkmonkey...";
    }

}
