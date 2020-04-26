package nd.sched.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringRestServer {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringRestServer.class);
        app.setBannerMode(Mode.OFF);
        app.run(args);
    }
}