package com.example.codePicasso;

import com.example.codePicasso.global.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class CodePicassoApplication {

  public static void main(String[] args) {
    SpringApplication.run(CodePicassoApplication.class, args);
  }

}
