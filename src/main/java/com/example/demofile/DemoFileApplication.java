package com.example.demofile;

import com.example.demofile.configuration.FileConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FileConfiguration.class)
public class DemoFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoFileApplication.class, args);
    }

}
