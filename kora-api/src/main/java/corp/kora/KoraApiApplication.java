package corp.kora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class KoraApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KoraApiApplication.class, args);
		log.info("== Start KoraApiApplication == ");
	}

}
