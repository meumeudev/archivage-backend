package sn.webg.archivage.service.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import sn.webg.archivage.service.entities.MetaDataEntity;
import sn.webg.archivage.service.entities.QMetaDataEntity;
import sn.webg.archivage.service.models.MetaDataType;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Repository
public interface MetaDataRepository extends MongoRepository<MetaDataEntity, String>, QuerydslPredicateExecutor<MetaDataEntity> {

    Optional<MetaDataEntity> findByLabel(String label);

    default Page<MetaDataEntity> readByFilters(String label, MetaDataType metaDataType, Pageable pageable) {

        var builder = new BooleanBuilder();

        if (StringUtils.isNotEmpty(label)) {
            builder.and(QMetaDataEntity.metaDataEntity.label.containsIgnoreCase(label.toLowerCase()));
        }
        if (Objects.nonNull(metaDataType)) {
            builder.and(QMetaDataEntity.metaDataEntity.metaDataType.eq(metaDataType));
        }

        return findAll(builder, pageable);
    }

    Set<MetaDataEntity> findByLabelIn(Set<String> metaData);
}
