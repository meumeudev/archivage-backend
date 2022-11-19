package sn.webg.archivage.service.configurations;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import sn.webg.archivage.service.utils.DateConverter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ObjectConverterConfiguration implements DateConverter {

    @Primary
    @Bean
    public ObjectMapper objectMapper() {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        SimpleModule simpleModule = new SimpleModule();

        /*
         * The serializer should system zone string date to equivalent UTC LocalDateTime (with Z)
         */
        simpleModule.addSerializer(LocalDateTime.class, new JsonSerializer<>() {

            @Override
            public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeString(DateTimeFormatter.ISO_INSTANT.format(transformDateToUtcZone(localDateTime)));
            }
        });


        /*
         * The deserializer should transform UTC string date (with Z) to equivalent system zone LocalDateTime
         */
        simpleModule.addDeserializer(LocalDateTime.class, new JsonDeserializer<>() {
            @Override
            public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

                LocalDateTimeDeserializer localDateTimeDeserializer = LocalDateTimeDeserializer.INSTANCE;
                LocalDateTime localDateTimeUtc = localDateTimeDeserializer.deserialize(jsonParser, deserializationContext);

                // If text ends with Z, its a ZULU time, convert it to server time
                // If not, take it as it is
                if (jsonParser.getText().trim().endsWith("Z")) {
                    return transformDateToSystemZone(localDateTimeUtc).toLocalDateTime();
                }
                return localDateTimeUtc;
            }
        });

        objectMapper.registerModule(simpleModule);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;

    }

}
