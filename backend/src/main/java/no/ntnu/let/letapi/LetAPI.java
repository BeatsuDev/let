package no.ntnu.let.letapi;

import no.ntnu.let.letapi.security.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Main class for the LetAPI application.
 */
@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class LetAPI {
    /**
     * Main method for the LetAPI application.
     * @param args The arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(LetAPI.class, args);
    }
}
