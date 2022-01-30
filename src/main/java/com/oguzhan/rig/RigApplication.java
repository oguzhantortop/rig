package com.oguzhan.rig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories("com.oguzhan.rig")
@ComponentScan("com.oguzhan.rig")
public class RigApplication {

	public static void main(String[] args) {
		SpringApplication.run(RigApplication.class, args);
	}

}
