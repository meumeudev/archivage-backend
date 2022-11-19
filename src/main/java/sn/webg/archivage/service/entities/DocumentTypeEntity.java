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

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@QueryEntity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = DocumentTypeEntity.COLLECTION_NAME)
@CompoundIndex(name = "document_type_code_index", def = "{'code': 1}", unique = true)
@Builder
public class DocumentTypeEntity extends AbstractAuditingEntity {

    public static final String COLLECTION_NAME = "AR_DOCUMENT_TYPE";

    @Id
    String id;

    String code;

    String label;

    Set<MetaDataForTypeEntity> metaDataForTypeList;
}
