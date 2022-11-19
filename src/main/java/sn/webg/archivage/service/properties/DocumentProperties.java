package sn.webg.archivage.service.properties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;

@Validated
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
@ConfigurationProperties(prefix = "storage-document")
public class DocumentProperties {

    /**
     * accept file extension to document
     */
    List<String> acceptFileExtensions = Arrays.asList("pdf", "xls", "xlsx", "doc", "docx");

    /**
     * Root path where data files can be started
     */
    @NotEmpty
    String fileStorageRootPath;
}
