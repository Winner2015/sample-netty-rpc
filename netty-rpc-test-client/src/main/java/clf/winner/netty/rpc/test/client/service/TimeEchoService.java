package clf.winner.netty.rpc.test.client.service;

import clf.winner.netty.rpc.test.server.api.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chenlongfei
 */
@Service
public class TimeEchoService {

    @Autowired
    private TimeService timeService;

    public void printBeiJingTime () {
        String time = timeService.getTime();
        System.out.println("当前北京时间为：" + time);
    }
}
