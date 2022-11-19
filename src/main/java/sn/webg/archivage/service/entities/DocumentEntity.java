package sn.webg.archivage.service.entities;


import com.querydsl.core.annotations.QueryEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@QueryEntity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = DocumentEntity.COLLECTION_NAME)
@CompoundIndexes( value = {
        @CompoundIndex(name = "document_reference_index", def = "{'reference': 1}", unique = true)
})
public class DocumentEntity extends AbstractAuditingEntity {

    public static final String COLLECTION_NAME = "AR_DOCUMENT";

    @Id
    String id;

    String path;

    String description;

    String reference;

    String name;

    String filePath;

    Map<String, Object> metaDatas;

    @DBRef
    DocumentTypeEntity documentType;

    @DBRef
    FolderEntity folder;

    @Builder
    public DocumentEntity(String createdBy, LocalDateTime createdDate, String lastModifiedBy, LocalDateTime lastModifiedDate, String id, String path, String description, String reference, String name, Map<String, Object> metaDatas, DocumentTypeEntity documentType, FolderEntity folder) {
        super(createdBy, createdDate, lastModifiedBy, lastModifiedDate);
        this.id = id;
        this.path = path;
        this.description = description;
        this.reference = reference;
        this.name = name;
        this.metaDatas = metaDatas;
        this.documentType = documentType;
        this.folder = folder;
    }
}
