package org.example.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xie.wei
 * @date created at 2021-11-16 22:31
 */
@AllArgsConstructor
@Getter
public enum TypeEnum {

    USER("user"),
    DEVICE("device"),
    Service("service");

    private final String type;
}
