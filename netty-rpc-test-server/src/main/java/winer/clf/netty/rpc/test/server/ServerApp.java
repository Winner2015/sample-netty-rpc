package winer.clf.netty.rpc.test.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author chenlongfei
 */
@SpringBootApplication
public class ServerApp {

    public static void main(String [] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ServerApp.class);
    }
}
