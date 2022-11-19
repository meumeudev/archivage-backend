package sn.webg.archivage.service.repositories;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.MapPath;
import com.querydsl.core.types.dsl.SimplePath;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import sn.webg.archivage.service.entities.DocumentEntity;
import sn.webg.archivage.service.entities.DocumentEntity;
import sn.webg.archivage.service.entities.QDocumentEntity;
import sn.webg.archivage.service.entities.QDocumentEntity;
import sn.webg.archivage.service.entities.QFolderEntity;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Repository
public interface DocumentRepository  extends MongoRepository<DocumentEntity, String>, QuerydslPredicateExecutor<DocumentEntity> {

    boolean existsByFolderId(String documentId);

     String START = ".start";
     String END = ".end";

    default Optional<DocumentEntity> readByIdFilters(@NotNull String documentId, Set<String> accessMetaData, Set<String> idDocumentTypes, boolean isAdmin, Set<String> idDocuments, Set<String> idFolders) {

        var builder = new BooleanBuilder(QDocumentEntity.documentEntity.id.eq(documentId));

        /* Add filter documentType */
        if (Objects.isNull(idDocumentTypes) || idDocumentTypes.isEmpty()) {

            if (!isAdmin) {
                builder.and(QDocumentEntity.documentEntity.documentType.id.isNull());
            }

        } else {
            builder.and(QDocumentEntity.documentEntity.documentType.id.in(idDocumentTypes));
        }

        /* Add filter folder */
        if (Objects.isNull(idFolders) || idFolders.isEmpty()) {

            if (!isAdmin) {
                builder.and(QDocumentEntity.documentEntity.folder.id.isNull());
            }

        } else {
            builder.and(QDocumentEntity.documentEntity.folder.id.in(idFolders));
        }

        /* Add filter document */
        if (Objects.nonNull(idDocuments) && !idDocuments.isEmpty()) {

            builder.and(QDocumentEntity.documentEntity.id.in(idDocuments));

        } else {
            if (!isAdmin) {
                builder.and(QDocumentEntity.documentEntity.id.isNull());
            }
        }

        return findOne(builder);
    }

    default Page<DocumentEntity> readByFilters(Map<String, Object> metaDataList, Set<String> accessMetaData, String name, String reference, Set<String> idDocumentTypes, boolean isAdmin, Set<String> idDocuments, Set<String> idFolders, Pageable pageable) {

        var builder = new BooleanBuilder();

        if (StringUtils.isNotEmpty(name)) {
            builder.and(QDocumentEntity.documentEntity.name.containsIgnoreCase(name));
        }

        if (StringUtils.isNotEmpty(reference)) {
            builder.and(QDocumentEntity.documentEntity.reference.eq(reference));
        }

        /* Add filter documentType */
        if (Objects.isNull(idDocumentTypes) || idDocumentTypes.isEmpty()) {

            if (!isAdmin) {
                builder.and(QDocumentEntity.documentEntity.documentType.id.isNull());
            }

        } else {
            builder.and(QDocumentEntity.documentEntity.documentType.id.in(idDocumentTypes));
        }

        /* Add filter folder */
        if (Objects.isNull(idFolders) || idFolders.isEmpty()) {

            if (!isAdmin) {
                builder.and(QDocumentEntity.documentEntity.folder.id.isNull());
            }

        } else {
            builder.and(QDocumentEntity.documentEntity.folder.id.in(idFolders));
        }

        /* Add filter document */
        if (Objects.nonNull(idDocuments) && !idDocuments.isEmpty()) {

            builder.and(QDocumentEntity.documentEntity.id.in(idDocuments));

        } else {
            if (!isAdmin) {
                builder.and(QDocumentEntity.documentEntity.id.isNull());
            }
        }

        if (metaDataList != null) {
            metaDataList.forEach((key, value) -> {

                if (Objects.nonNull(value)) {
                    MapPath<String, Object, SimplePath<Object>> mapPath = QDocumentEntity.documentEntity.metaDatas;

                    if (value instanceof String) {
                        builder.and(Expressions.stringPath(mapPath.get(key).getMetadata()).containsIgnoreCase(value.toString()));
                    } else if (value instanceof LocalDateTime) {

                        if (key.contains(START)) {
                            builder.and(Expressions.dateTimePath(LocalDateTime.class, mapPath.get(key.replaceAll("\\" + START, "")).getMetadata()).goe((LocalDateTime) value));
                        }
                        if (key.contains(END)) {
                            builder.and(Expressions.dateTimePath(LocalDateTime.class, mapPath.get(key.replaceAll("\\" + END, "")).getMetadata()).loe((LocalDateTime) value));
                        } else  {
                            builder.and(mapPath.contains(key, value));
                        }

                    } else if (value instanceof LocalDate) {

                        if (key.contains(START)) {
                            builder.and(Expressions.datePath(LocalDate.class, mapPath.get(key.replaceAll("\\" + START, "")).getMetadata()).goe((LocalDate) value));
                        }
                        if (key.contains(END)) {
                            builder.and(Expressions.datePath(LocalDate.class, mapPath.get(key.replaceAll("\\" + END, "")).getMetadata()).loe((LocalDate) value));
                        } else {
                            builder.and(mapPath.contains(key, value));
                        }
                    } else if (value instanceof Double) {

                        if (key.contains(START)) {
                            builder.and(Expressions.numberPath(Double.class, mapPath.get(key.replaceAll("\\" + START, "")).getMetadata()).goe((Double) value));
                        }
                        if (key.contains(END)) {
                            builder.and(Expressions.numberPath(Double.class, mapPath.get(key.replaceAll("\\" + END, "")).getMetadata()).loe((Double) value));
                        } else {
                            builder.and(mapPath.contains(key, value));
                        }
                    } else if ( value instanceof Collection) {
                        builder.and(Expressions.stringPath(mapPath.get(key).getMetadata()).in((List<String>) value));
                    } else {
                        builder.and(mapPath.contains(key, value));
                    }
                }

            });
        }

        return findAll(builder, pageable);

    }
}
