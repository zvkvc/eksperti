package com.zvkvc.eksperti;

import com.zvkvc.eksperti.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfig.class)
@EntityScan(basePackages = {"com.zvkvc.eksperti.model"})
public class EkspertiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(EkspertiApplication.class, args);
	}

}
