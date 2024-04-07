package com.likelion.shopchoyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ShopchoyuApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopchoyuApplication.class, args);
	}

}
