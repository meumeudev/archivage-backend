package sn.webg.archivage.service.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import sn.webg.archivage.service.entities.DocumentTypeEntity;
import sn.webg.archivage.service.entities.QDocumentTypeEntity;
import sn.webg.archivage.service.models.MetaDataType;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface DocumentTypeRepository extends MongoRepository<DocumentTypeEntity, String>, QuerydslPredicateExecutor<DocumentTypeEntity> {

    default boolean existsByMetaDataForTypeListMetaDataId(@NotNull String metaDataId) {

        var builder = new BooleanBuilder();

        builder.and(QDocumentTypeEntity.documentTypeEntity.metaDataForTypeList.any().metaData.id.eq(metaDataId));

        return exists(builder);
    }

    default Page<DocumentTypeEntity> readByFilters(String code, String label, List<MetaDataType> metaDataTypeList, List<String> metaDataLabelList, Pageable pageable) {

        var builder = new BooleanBuilder();

        if (StringUtils.isNotEmpty(code)) {
            builder.and(QDocumentTypeEntity.documentTypeEntity.code.equalsIgnoreCase(code));
        }

        if (StringUtils.isNotEmpty(label)) {
            builder.and(QDocumentTypeEntity.documentTypeEntity.label.containsIgnoreCase(label));
        }

        if (metaDataTypeList != null && !metaDataTypeList.isEmpty()) {
            builder.and(QDocumentTypeEntity.documentTypeEntity.metaDataForTypeList.any().metaData.metaDataType.in(metaDataTypeList));
        }

        if (metaDataLabelList != null && !metaDataLabelList.isEmpty()) {
            builder.and(QDocumentTypeEntity.documentTypeEntity.metaDataForTypeList.any().metaData.label.in(metaDataLabelList.stream().map(String::toLowerCase).collect(Collectors.toSet())));
        }

        return findAll(builder, pageable);

    }
}
