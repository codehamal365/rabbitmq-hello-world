package org.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author xie.wei
 * @date created at 2021-11-16 21:30
 */
@Data
@ConfigurationProperties(prefix = "user")
@Component("user")
public class User {
    private String name;
    private Integer age;
}
