package hobin.toyBoard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ToyBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToyBoardApplication.class, args);
	}

}
