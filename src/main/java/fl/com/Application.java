package fl.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import fl.com.entity.StorageProperties;

/**
* @author FL
* @version create time��2018��4��16�� ����3:22:23
* 
*/
@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
