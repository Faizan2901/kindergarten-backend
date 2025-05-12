package com.nmpc.kindergarten;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class NahneMunhePlayCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(NahneMunhePlayCenterApplication.class, args);
	}

}
