package org.jdev2015;

import org.apache.camel.spring.boot.CamelSpringBootApplicationController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplication(Application.class).run(args);
		CamelSpringBootApplicationController applicationController = applicationContext.getBean(CamelSpringBootApplicationController.class);
		applicationController.blockMainThread();
	}
}
