package serverside.course.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
public class WebapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebapiApplication.class, args);
	}


	@Bean
	public Executor taskExecutor(){
//		ThreadPoolExecutor javaExecutor =
//		new ThreadPoolExecutor(3,5,3000,
//				TimeUnit.MILLISECONDS,
//				new LinkedBlockingQueue<>(100));
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(3);
		taskExecutor.setMaxPoolSize(5);
		taskExecutor.setKeepAliveSeconds(3);
		taskExecutor.setQueueCapacity(100);
		taskExecutor.initialize();
		// define a Prefix for ThreadPoolTaskExecutor threads
		taskExecutor.setThreadNamePrefix("Spring ThreadPoolTaskExecutor");
		return taskExecutor;
	}

}
