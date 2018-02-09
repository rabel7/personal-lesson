package com.example.demo;

import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@DubboComponentScan(basePackages = "cn.com.aperfect.controller")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


//	@Configuration
//	@EnableDubbo(scanBasePackages = "cn.com.aperfect", multipleConfig = true)
//	@PropertySource("classpath:/dubbo/dubbo-consumer.properties")
//	@ComponentScan(value = {"cn.com.aperfect"})
//	static public class ConsumerConfiguration {
//
//	}

//	@Configuration
//	@EnableDubbo(scanBasePackages = "dubbo", multipleConfig = true)
//	@PropertySource("classpath:/dubbo/dubbo-consumer.properties")
//	static public class ProviderConfiguration {
//		@Bean
//		public ConsumerConfig consumerConfig() {
//			ConsumerConfig consumerConfig = new ConsumerConfig();
//			consumerConfig.setTimeout(1000);
//			return consumerConfig;
//		}
//	}
}
