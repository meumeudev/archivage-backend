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
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@QueryEntity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = FolderTypeEntity.COLLECTION_NAME)
@CompoundIndex(name = "folder_type_code_index", def = "{'code': 1}", unique = true)
public class FolderTypeEntity extends AbstractAuditingEntity {

    public static final String COLLECTION_NAME = "AR_FOLDER_TYPE";

    @Id
    String id;

    String code;

    String label;

    Set<MetaDataForTypeEntity> metaDataForTypeList;

    @Builder
    public FolderTypeEntity(String createdBy, LocalDateTime createdDate, String lastModifiedBy, LocalDateTime lastModifiedDate, String id, String code, String label, Set<MetaDataForTypeEntity> metaDataForTypeList) {
        super(createdBy, createdDate, lastModifiedBy, lastModifiedDate);
        this.id = id;
        this.code = code;
        this.label = label;
        this.metaDataForTypeList = metaDataForTypeList;
    }
}
