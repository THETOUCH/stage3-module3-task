package com.mjc.school;

import com.mjc.school.controller.implementation.AuthorController;
import com.mjc.school.controller.implementation.NewsController;
import com.mjc.school.controller.implementation.TagController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableAspectJAutoProxy
public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Main.class, args);
        NewsController newsController = applicationContext.getBean(NewsController.class);
        AuthorController authorController = applicationContext.getBean(AuthorController.class);
        TagController tagController = applicationContext.getBean(TagController.class);

        System.out.println(newsController);
        System.out.println(1);
    }
}
