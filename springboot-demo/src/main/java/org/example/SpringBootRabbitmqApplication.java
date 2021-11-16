package org.example;

import org.example.config.TopicList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author xie.wei
 * @since 2021/11/14
 */
@SpringBootApplication
public class SpringBootRabbitmqApplication {


    private static TopicList list;

    @Autowired
    public void setList(TopicList list){
        SpringBootRabbitmqApplication.list = list;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SpringBootRabbitmqApplication.class, args);

        Object user = run.getBean("user");
        Object topicList = run.getBean("topicList");
        System.out.println(list);
        System.out.println(user);
        System.out.println(topicList);
    }
}
