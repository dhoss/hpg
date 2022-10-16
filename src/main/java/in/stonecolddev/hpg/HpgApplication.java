package in.stonecolddev.hpg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@ConfigurationPropertiesScan
public class HpgApplication {

	public static void main(String[] args) {
		SpringApplication.run(HpgApplication.class, args);
	}
}