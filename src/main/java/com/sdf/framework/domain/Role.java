package com.sdf.framework.domain;

import lombok.Data;

/**
 * @author barry
 * @date 2021/8/30 10:41 上午
 * @description
 */
@Data
public class Role {

    private int id;

    private String name;

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Role() {
    }
}
