//package com.example.save_chat;
//
//import com.example.codePicasso.global.config.JwtProperties;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//import org.springframework.scheduling.annotation.EnableScheduling;
//
//@EnableScheduling
//@EnableJpaAuditing
//  @SpringBootApplication(exclude = {RedisRepositoriesAutoConfiguration.class})
//  @EnableConfigurationProperties(JwtProperties.class)
//public class SavePicassoApplication {
//
//  public static void main(String[] args) {
//    System.setProperty("server.port", "8081");
//    System.out.println("Active profile: " + System.getProperty("spring.profiles.active")+"ㅁㅁㅁㅁㅁㅁㅁ"); // 확인용 출력
//    SpringApplication.run(SavePicassoApplication.class, args);
//  }
//
//}
