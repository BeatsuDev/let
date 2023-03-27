package no.ntnu.let.letapi.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Configuration properties for RSA keys. Used to inject the keys into the application.
 * The keys location are read from the application.properties file.
 * @param publicKey The public key
 * @param privateKey The private key
 */
@ConfigurationProperties(prefix = "rsa")
public record RsaKeyProperties(RSAPublicKey publicKey, RSAPrivateKey privateKey) {

}