package sn.webg.archivage.service.properties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Data
@Component
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "security-jwt")
public class JwtProperties {

    String secret;

    String base64Secret;

    long tokenValidityInSeconds = 1800;

    long tokenValidityInSecondsForRememberMe = 2592000;
}
