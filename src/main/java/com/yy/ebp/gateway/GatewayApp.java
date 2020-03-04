package com.yy.ebp.gateway;

import com.yy.ebp.gateway.sso.dto.SessionConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@EnableSwagger2
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableZuulProxy
@EnableConfigurationProperties({SessionConfig.class})
public class GatewayApp {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(GatewayApp.class);
	
	private static ConfigurableApplicationContext ctx;
	
	public static ApplicationContext getCtx() {
		return ctx;
	}
	
	public static void main(String[] args) {
		ctx = SpringApplication.run(GatewayApp.class, args);
        LOGGER.debug("startup...");
//        showBeans();
    }
	
	public static void showBeans() {
		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName : beanNames) {
			System.out.println(beanName);
			System.out.println(ctx.getBean(beanName).getClass());
		}
	}
}
