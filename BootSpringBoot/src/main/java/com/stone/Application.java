package com.stone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

// 用于做一些框架的配置
// Spring启动应用程序的入口点
/*
* Spring Boot Auto Configuration 会根据在项目中添加的JAR依赖项自动配置Spring应用程序。
* 需要将@EnableAutoConfiguration 或 @SpringBootApplication 添加到主类文件中，然后将自动配置Spring Boot应用程序
* 如果将@SpringBootApplication批注添加到类中，则无需添加@EnableAutoConfiguration，@ComponentScan和@SpringBootConfiguration批注。
* @SpringBootApplication注释包括所有其他注释。
* Spring Boot应用程序在初始化时扫描所有bean和包声明，需要为类文件添加@ComponentScan批注，以扫描项目中添加的组件。*/
@SpringBootApplication
@MapperScan(basePackages = "com.stone.mapper")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
