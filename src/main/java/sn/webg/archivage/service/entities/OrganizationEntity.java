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

@Data
@AllArgsConstructor
@NoArgsConstructor
@QueryEntity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = OrganizationEntity.COLLECTION_NAME)
@CompoundIndexes( value = {
        @CompoundIndex(name = "organization_name_index", def = "{'name': 1}", unique = true),
        @CompoundIndex(name = "organization_code_index", def = "{'code': 1}", unique = true)
})

public class OrganizationEntity extends AbstractAuditingEntity {

    public static final String COLLECTION_NAME = "AR_ORGANIZATION";

    @Id
    String id;

    String name;

    String phone;

    String code;

    String responsible;

    @DBRef
    OrganizationTypeEntity organizationType;

    @Builder
    public OrganizationEntity(String createdBy, LocalDateTime createdDate, String lastModifiedBy, LocalDateTime lastModifiedDate, String id, String name, String phone, String code, String responsible, OrganizationTypeEntity organizationType) {
        super(createdBy, createdDate, lastModifiedBy, lastModifiedDate);
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.code = code;
        this.responsible = responsible;
        this.organizationType = organizationType;
    }
}
