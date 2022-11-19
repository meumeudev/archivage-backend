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

import java.time.LocalDateTime;

import static sn.webg.archivage.service.entities.ShelfEntity.COLLECTION_NAME;

@Data
@AllArgsConstructor
@NoArgsConstructor
@QueryEntity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = COLLECTION_NAME)
@CompoundIndex(name = "shelf_code_index", def = "{'code': 1}", unique = true)
public class ShelfEntity extends AbstractAuditingEntity {

	public static final String COLLECTION_NAME = "AR_SHELF";

	@Id
	String id;

	String code;

	String label;

	boolean active = true;

	@Builder
	public ShelfEntity(String createdBy, LocalDateTime createdDate, String lastModifiedBy, LocalDateTime lastModifiedDate, String id, String code, String label, boolean active) {
		super(createdBy, createdDate, lastModifiedBy, lastModifiedDate);
		this.id = id;
		this.code = code;
		this.label = label;
		this.active = active;
	}
}
