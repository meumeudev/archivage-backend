package sn.webg.archivage.service.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public interface DateConverter {

    ZoneId ZONE_UTC = ZoneId.of("UTC");

    default ZonedDateTime transformDateToSystemZone(LocalDateTime dateUtc) {
        return dateUtc.atZone(ZONE_UTC).withZoneSameInstant(ZoneId.systemDefault());
    }

    default ZonedDateTime transformDateToUtcZone(LocalDateTime dateSystemZone) {
        return dateSystemZone.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZONE_UTC);
    }

    default LocalDate stringToLocalDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    default LocalDateTime stringToLocalDateTime(String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    }
}
