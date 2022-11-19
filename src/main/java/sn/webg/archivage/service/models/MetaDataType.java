package sn.webg.archivage.service.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public enum MetaDataType {

    TEXT("Texte", String.class),
    TEXTAREA("Zone de texte", String.class),
    LIST("Liste", String.class),
    BOOLEAN("Oui ou Non", Boolean.class),
    MULTIPLE_CHOICE("Choix multiple", ArrayList.class),
    DATE("Date", LocalDate.class),
    DATETIME("DAte et heure", LocalDateTime.class),
    NUMERIC("Nombre", Number.class),
    ATTACHMENT("Piece jointe", String.class);

    @Getter
    String description;

    @Getter
    Class<?> type;

    MetaDataType(String description, Class<?> type) {
        this.description = description;
        this.type = type;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static MetaDataType fromValue(Object value) {
        if (value instanceof Map) {
            Map<String, Object> mapMetaDataType = (Map<String, Object>) value;
            if (mapMetaDataType.containsKey("code")) {
                return MetaDataType.valueOf(mapMetaDataType.get("code").toString());
            }
        }
        if (value instanceof String) {
            return MetaDataType.valueOf(value.toString());
        }
        throw new IllegalArgumentException(MessageFormat.format("{0} not found with the value: {1} in [{2}]", MetaDataType.class, value, values()));
    }

    @JsonValue
    Map<String, Object> getMetaDataType() {
        return Map.of(
                "code", name(),
                "description", description
        );
    }
}
