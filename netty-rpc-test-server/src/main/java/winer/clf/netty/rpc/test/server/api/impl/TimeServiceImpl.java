package winer.clf.netty.rpc.test.server.api.impl;

import org.springframework.stereotype.Service;
import winer.clf.netty.rpc.core.config.RpcService;
import winer.clf.netty.rpc.test.server.api.TimeService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author chenlongfei
 */
@RpcService
@Service
public class TimeServiceImpl implements TimeService {

    private static final String TIME_PATTERN = "当前北京时间为：yyyy年MM月dd HH:mm:ss";

    @Override
    public String getTime () {
        LocalDateTime now = LocalDateTime.now();
        String timeResp = now.format(DateTimeFormatter.ofPattern(TIME_PATTERN));
        return timeResp;
    }

}
