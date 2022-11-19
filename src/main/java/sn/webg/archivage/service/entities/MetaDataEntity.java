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
import org.springframework.data.mongodb.core.mapping.Document;
import sn.webg.archivage.service.models.MetaDataType;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@QueryEntity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = MetaDataEntity.COLLECTION_NAME)
@CompoundIndex(name = "metadata_label_index", def = "{'label': 1}", unique = true)
public class MetaDataEntity extends AbstractAuditingEntity {

    public static final String COLLECTION_NAME = "AR_METADATA";

    @Id
    String id;

    String label;

    MetaDataType metaDataType;

    @Builder
    public MetaDataEntity(String createdBy, LocalDateTime createdDate, String lastModifiedBy, LocalDateTime lastModifiedDate, String id, String label, MetaDataType metaDataType) {
        super(createdBy, createdDate, lastModifiedBy, lastModifiedDate);
        this.id = id;
        this.label = label;
        this.metaDataType = metaDataType;
    }
}
