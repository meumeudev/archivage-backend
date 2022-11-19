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
@Document(collection = FolderEntity.COLLECTION_NAME)
@CompoundIndexes( value = {
        @CompoundIndex(name = "folder_name_index", def = "{'name': 1}", unique = true),
        @CompoundIndex(name = "folder_reference_index", def = "{'reference': 1}", unique = true),
        @CompoundIndex(name = "folder_name_index", def = "{'name': 1}", unique = true),
})
public class FolderEntity extends AbstractAuditingEntity {

    public static final String COLLECTION_NAME = "AR_FOLDER";

    @Id
    String id;

    String name;

    String description;

    String reference;

    String sequentialNumber;

    @DBRef
    FolderTypeEntity folderType;

    @DBRef
    CardboardEntity cardboard;

    Map<String, Object> metaDatas;

    LocalDateTime date;

    Integer year;

    String comment;

    @DBRef
    OrganizationEntity organization;

    @Builder
    public FolderEntity(String createdBy, LocalDateTime createdDate, String lastModifiedBy, LocalDateTime lastModifiedDate, String id, String name, String description, String reference, String sequentialNumber, FolderTypeEntity folderType, CardboardEntity cardboard, Map<String, Object> metaDatas, OrganizationEntity organization) {
        super(createdBy, createdDate, lastModifiedBy, lastModifiedDate);
        this.id = id;
        this.name = name;
        this.description = description;
        this.reference = reference;
        this.sequentialNumber = sequentialNumber;
        this.folderType = folderType;
        this.cardboard = cardboard;
        this.metaDatas = metaDatas;
        this.organization = organization;
    }
}
