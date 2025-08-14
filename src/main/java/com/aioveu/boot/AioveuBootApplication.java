package com.aioveu.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;


/**
 * @Description: TODO 应用启动类   时光飞逝，莫等闲，白了少年头，空悲切，一万年太久，只争朝夕
 * @Param:  null
 * @Return: null
 * @Author: 雒世松
 * @Email:
 * @Date:  2025-06-11 15:52:28
 * @LastEditors:
 * @LastEditTime: 2025-06-11 15:52:28
*/

@SpringBootApplication
@ConfigurationPropertiesScan // 开启配置属性绑定
public class AioveuBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(AioveuBootApplication.class, args);
    }
}