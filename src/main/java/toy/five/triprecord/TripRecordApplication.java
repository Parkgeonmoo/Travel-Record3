package toy.five.triprecord;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import toy.five.triprecord.global.common.StatusCode;

@SpringBootApplication
@EnableJpaAuditing
public class TripRecordApplication {

	public static void main(String[] args) {
		SpringApplication.run(TripRecordApplication.class, args);
	}

}
