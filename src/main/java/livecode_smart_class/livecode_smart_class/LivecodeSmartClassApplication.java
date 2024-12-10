package livecode_smart_class.livecode_smart_class;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "livecode_smart_class.livecode_smart_class")

public class LivecodeSmartClassApplication {

    public static void main(String[] args) {

		SpringApplication.run(LivecodeSmartClassApplication.class, args);
    }

}
