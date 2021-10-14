package co.com.sofkau.bibliotecap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("src/main/java/co/com/sofkau")
public class BibliotecapApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibliotecapApplication.class, args);
	}

}
