package monkey.mybatis.test;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appmessage")
public class APPMessageController {

    private AppMessageService appMessageService;

    @RequestMapping("/getThree")
    public List<AppMessage> getThreeForMessage() {
        List<AppMessage> list = appMessageService.getMessage();
        return list;
    }

    @RequestMapping("/getAll")
    public List<AppMessage> getAllMessage() {
        List<AppMessage> list = appMessageService.getAllMessage();
        int num = list.size();
        if (null != list && num > 3) {
            for (int i = 0; i < num - 3; i++) {
                list.remove(0);
            }
        }
        return list;
    }

    @RequestMapping("/getByID")
    public List<AppMessage> getMessageById(@RequestParam("id") String id) {
        List<AppMessage> list = appMessageService.getMessageById(id);
        int num = list.size();
        if (null != list && num > 5) {
            for (int i = 0; i < num - 5; i++) {
                list.remove(0);
            }
        }
        return list;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public int addMessage(@RequestBody AppMessage appMessage) {
        return appMessageService.addMessage(appMessage);
    }

    @RequestMapping(value = "/delMessageById", method = RequestMethod.POST)
    public int delMessageById(@RequestParam("id") String id) {
        return appMessageService.delMessage(id);
    }
}
