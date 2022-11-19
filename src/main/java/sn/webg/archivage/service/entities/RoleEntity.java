package sn.webg.archivage.service.entities;

import com.querydsl.core.annotations.QueryEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import sn.webg.archivage.service.models.SecurityPermissions;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static sn.webg.archivage.service.entities.RoleEntity.COLLECTION_NAME;

@Data
@AllArgsConstructor
@NoArgsConstructor
@QueryEntity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = COLLECTION_NAME)
public class RoleEntity extends AbstractAuditingEntity implements Serializable {

    public static final String COLLECTION_NAME = "AR_ROLE";

    @Id
    String name;

    String description;

    Set<SecurityPermissions> permissions = new HashSet<>();

    @DBRef
    Set<MetaDataEntity> metaDataList;

    @DBRef
    Set<FolderEntity> folders;

    @DBRef
    Set<FolderTypeEntity> folderTypes;

    @DBRef
    Set<DocumentTypeEntity> documentTypes;

    @DBRef
    Set<DocumentEntity> documents;

    @Builder
    public RoleEntity(String createdBy, LocalDateTime createdDate, String lastModifiedBy, LocalDateTime lastModifiedDate, String name, String description, Set<SecurityPermissions> permissions, Set<MetaDataEntity> metaDataList, Set<FolderEntity> folders, Set<FolderTypeEntity> folderTypes, Set<DocumentTypeEntity> documentTypes, Set<DocumentEntity> documents) {
        super(createdBy, createdDate, lastModifiedBy, lastModifiedDate);
        this.name = name;
        this.description = description;
        this.permissions = permissions;
        this.metaDataList = metaDataList;
        this.folders = folders;
        this.folderTypes = folderTypes;
        this.documentTypes = documentTypes;
        this.documents = documents;
    }
}
