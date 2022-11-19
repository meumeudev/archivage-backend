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

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@QueryEntity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MetaDataForTypeEntity {

    boolean required;

    List<String> possibleValues;

    boolean visibleInDisplay;

    boolean availableInSearch;

    @DBRef
    MetaDataEntity metaData;
}
