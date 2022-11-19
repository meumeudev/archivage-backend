package sn.webg.archivage.service.entities;

import com.querydsl.core.annotations.QueryEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@QueryEntity
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = CardboardEntity.COLLECTION_NAME)
@CompoundIndex(name = "cardboard_code_index", def = "{'code': 1}", unique = true)
public class CardboardEntity extends AbstractAuditingEntity {

	public static final String COLLECTION_NAME = "AR_CARDBOARD";

	@Id
	String  id;

	String code;

	String label;

	@DBRef
	ShelfEntity shelf;
}
