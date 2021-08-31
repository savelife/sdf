package com.sdf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author barry
 * @date 2021/8/27 5:33 下午
 * @description
 */
@SpringBootApplication
@EnableSwagger2
public class SDFApplication {
    public static void main(String[] args) {
        SpringApplication.run(SDFApplication.class, args);
    }
}
