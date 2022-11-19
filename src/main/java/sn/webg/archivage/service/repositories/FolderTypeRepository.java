package sn.webg.archivage.service.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import sn.webg.archivage.service.entities.FolderTypeEntity;
import sn.webg.archivage.service.entities.QFolderTypeEntity;
import sn.webg.archivage.service.models.MetaDataType;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface FolderTypeRepository extends MongoRepository<FolderTypeEntity, String>, QuerydslPredicateExecutor<FolderTypeEntity> {

    default boolean existsByMetaDataForTypeListMetaDataId(@NotNull String metaDataId) {

        var builder = new BooleanBuilder();

        builder.and(QFolderTypeEntity.folderTypeEntity.metaDataForTypeList.any().metaData.id.eq(metaDataId));

        return exists(builder);
    }

    default Page<FolderTypeEntity> readByFilters(String code, String label, List<String> metaDataList, Pageable pageable) {

        var builder = new BooleanBuilder();

        if (StringUtils.isNotEmpty(code)) {
            builder.and(QFolderTypeEntity.folderTypeEntity.code.equalsIgnoreCase(code));
        }

        if (StringUtils.isNotEmpty(label)) {
            builder.and(QFolderTypeEntity.folderTypeEntity.label.containsIgnoreCase(label.toLowerCase()));
        }

        if (metaDataList != null && !metaDataList.isEmpty()) {
            builder.and(QFolderTypeEntity.folderTypeEntity.metaDataForTypeList.any().metaData.id.in(metaDataList));
        }

        return findAll(builder, pageable);

    }
}
