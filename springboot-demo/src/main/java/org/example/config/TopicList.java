package org.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xie.wei
 * @date created at 2021-11-16 21:10
 */
@Validated
@Data
@ConfigurationProperties(prefix = "config-map")
@Configuration
public class TopicList {

    private List<@Valid Topic> topics;
    private List<String> scopes;

    @Data
    public static class Topic {
        private String topic;
        private List<@ValueCheck(type = "actions", message = "action value is not valid") String> actions;
        private List<String> permission;
        private List<@ValueCheck(type = "client", message = "client value is not valid") String> client;
    }

    public static class TopicValidator implements Validator {

        @Override
        public boolean supports(Class<?> aClass) {
            return Topic.class.isAssignableFrom(aClass);
        }

        @Override
        public void validate(Object o, Errors errors) {
            Topic topic = (Topic) o;
            List<String> collect = Stream.of(TypeEnum.values())
                    .map(TypeEnum::getType).collect(Collectors.toList());
            List<String> client = topic.getClient();
            System.out.println("========================");
            if (collect.containsAll(client)) {

                // throw new RuntimeException("errr");
                // errors.rejectValue("client", "config.topic.client", "[app.id] client type error");
            }

        }
    }
}


