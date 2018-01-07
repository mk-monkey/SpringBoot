package monkey.retry.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;


/**
 * @Class Name: RemoteService
 * @Description: TODO
 * @Company bgy:  MK monkey
 * @create: 2018-01-07 18:52
 **/
@Component("remoteService")
public class RemoteService {

    private static final Logger logger = LoggerFactory.getLogger(RemoteService.class);

    @Retryable(value = {BusionessExceprion.class}, maxAttempts = 5, backoff = @Backoff(delay = 5000, multiplier = 2))
    public void call() throws Exception {
        logger.info("do something...");
        throw new BusionessExceprion();
    }

    @Recover
    public void recover(BusionessExceprion e) {
        //具体的业务逻辑
        logger.info("--------------------------");
        logger.info(e.getMessage());
    }
}
