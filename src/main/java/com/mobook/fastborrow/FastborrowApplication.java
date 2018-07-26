package com.mobook.fastborrow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableCaching //缓存
@EnableSwagger2 //Swagger自动生成文档
public class FastborrowApplication {
	public static void main(String[] args) {
		SpringApplication.run(FastborrowApplication.class, args);
	}
}
