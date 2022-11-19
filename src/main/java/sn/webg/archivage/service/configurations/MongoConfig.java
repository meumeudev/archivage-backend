package sn.webg.archivage.service.configurations;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;

@Configuration
@EnableMongock
@EnableMongoAuditing
public class MongoConfig {

    /**
     * Add Jsr310Converters to convert new java 8 objects like Currency, Duration, LocalTime...
     * <b>When an object is saved in mongodb, all localDateTime values are converted from system zone to utc</b>
     * <b>When an object is read from mongodb, strings with date format are converted to localDateTime without any system zone conversion, same as stored value</b>
     *
     * @return MongoCustomConversions
     */
    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(new ArrayList<>(Jsr310Converters.getConvertersToRegister()));
    }
}
