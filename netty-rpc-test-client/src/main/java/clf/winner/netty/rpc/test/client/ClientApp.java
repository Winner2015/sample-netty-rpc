package clf.winner.netty.rpc.test.client;

import clf.winner.netty.rpc.test.client.service.TimeEchoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author chenlongfei
 */
@SpringBootApplication
public class ClientApp {

    public static void main(String [] args) {

        ConfigurableApplicationContext context = SpringApplication.run(ClientApp.class);

        TimeEchoService timeEchoService = (TimeEchoService)context.getBean("timeEchoService");

        timeEchoService.printBeiJingTime();

    }

}
