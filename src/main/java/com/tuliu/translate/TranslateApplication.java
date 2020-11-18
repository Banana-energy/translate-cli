package com.tuliu.translate;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@MapperScan(basePackages = {"com.tuliu.translate.mapper"})
@EnableScheduling
public class TranslateApplication {

	@Value("${spring.application.name}")
	static String appName;
	private static final Logger logger = LoggerFactory.getLogger(TranslateApplication.class);
	private static String[] args;
	private static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		TranslateApplication.args = args;
		TranslateApplication.context = 	SpringApplication.run(TranslateApplication.class, args);
	}

	public static void restart() {
		logger.info("spring.application.name:"+appName);
		ExecutorService threadPool = new ThreadPoolExecutor(1,1,0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1),new ThreadPoolExecutor.DiscardOldestPolicy());
		threadPool.execute(()->{
			context.close();
			context = SpringApplication.run(TranslateApplication.class, args);
		});
		threadPool.shutdown();
	}

}
