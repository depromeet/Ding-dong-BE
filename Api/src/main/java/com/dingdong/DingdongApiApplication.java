package com.dingdong;


import java.time.LocalDateTime;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;

@EnableFeignClients
@EntityScan("com.dingdong.domain")
@RequiredArgsConstructor
@SpringBootApplication(
        exclude = {
            org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration
                    .class,
            org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration.class,
            org.springframework.cloud.aws.autoconfigure.context
                    .ContextRegionProviderAutoConfiguration.class
        })
@Slf4j
public class DingdongApiApplication implements ApplicationListener<ApplicationReadyEvent> {

    private final Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(DingdongApiApplication.class, args);
        System.out.println("LocalDateTime.now() = " + LocalDateTime.now());
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("applicationReady status" + Arrays.toString(environment.getActiveProfiles()));
    }
}
