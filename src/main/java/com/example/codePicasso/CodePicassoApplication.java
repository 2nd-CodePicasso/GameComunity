package com.example.codePicasso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CodePicassoApplication {

  public static void main(String[] args) {
    SpringApplication.run(CodePicassoApplication.class, args);
  }

}
