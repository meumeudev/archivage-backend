package sn.webg.archivage.service.entities;

import com.querydsl.core.annotations.QueryEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@QueryEntity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = OrganizationTypeEntity.COLLECTION_NAME)
@CompoundIndex(name = "organization_type_code_index", def = "{'code': 1}", unique = true)
public class OrganizationTypeEntity extends AbstractAuditingEntity {

    public static final String COLLECTION_NAME = "AR_ORGANIZATION_TYPE";

    @Id
    String id;

    String code;

    String label;
}
