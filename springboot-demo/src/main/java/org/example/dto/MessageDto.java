package org.example.dto;

import lombok.Data;

/**
 * @author xie.wei
 * @since 2022-01-10
 */
@Data
public class MessageDto {
    private String routingKey;
    private String msg;
    private String headerValue;
}
