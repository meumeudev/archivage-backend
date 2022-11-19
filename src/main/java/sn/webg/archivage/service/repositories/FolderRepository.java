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
import sn.webg.archivage.service.entities.FolderEntity;
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
public interface FolderRepository extends MongoRepository<FolderEntity, String>, QuerydslPredicateExecutor<FolderEntity> {

     String START = ".start";
     String END = ".end";

     Optional<FolderEntity> readFolderEntityByReference(String reference);

     default Optional<FolderEntity> readByIdFilters(@NotNull String folderId, Set<String> accessMetaData, Set<String> idFolderTypes, boolean isAdmin, Set<String> idFolders ) {

         var builder = new BooleanBuilder(QFolderEntity.folderEntity.id.eq(folderId));

         /* Add filter folderType */
         if (Objects.isNull(idFolderTypes) || idFolderTypes.isEmpty()) {

             if (!isAdmin) {
                 builder.and(QFolderEntity.folderEntity.folderType.id.isNull());
             }

         } else {
             builder.and(QFolderEntity.folderEntity.folderType.id.in(idFolderTypes));
         }

         /* Add filter folder */
         if (Objects.nonNull(idFolders) && !idFolders.isEmpty()) {

             builder.and(QFolderEntity.folderEntity.id.in(idFolders));

         } else {
             if (!isAdmin) {
                 builder.and(QFolderEntity.folderEntity.id.isNull());
             }
         }

         return findOne(builder);
     }

    default Page<FolderEntity> readByFilters(Map<String, Object> metaDataList, Set<String> accessMetaData, String name, String reference, Set<String> idFolderTypes, boolean isAdmin, Set<String> idFolders, Integer year, Pageable pageable) {

        var builder = new BooleanBuilder();

        if (StringUtils.isNotEmpty(name)) {
            builder.and(QFolderEntity.folderEntity.name.containsIgnoreCase(name));
        }

        if (Objects.nonNull(year)) {
            builder.and(QFolderEntity.folderEntity.year.eq(year));
        }

        if (StringUtils.isNotEmpty(reference)) {
            builder.and(QFolderEntity.folderEntity.reference.eq(reference));
        }

        /* Add filter folderType */
        if (Objects.isNull(idFolderTypes) || idFolderTypes.isEmpty()) {

            if (!isAdmin) {
                builder.and(QFolderEntity.folderEntity.folderType.id.isNull());
            }

        } else {
            builder.and(QFolderEntity.folderEntity.folderType.id.in(idFolderTypes));
        }

        /* Add filter folder */
        if (Objects.nonNull(idFolders) && !idFolders.isEmpty()) {

            builder.and(QFolderEntity.folderEntity.id.in(idFolders));

        } else {
            if (!isAdmin) {
                builder.and(QFolderEntity.folderEntity.id.isNull());
            }
        }

        if (metaDataList != null) {
            metaDataList.forEach((key, value) -> {

                if (Objects.nonNull(value)) {
                    MapPath<String, Object, SimplePath<Object>> mapPath = QFolderEntity.folderEntity.metaDatas;

                    if (value instanceof String) {
                        builder.and(Expressions.stringPath(mapPath.get(key).getMetadata()).containsIgnoreCase(value.toString()));
                    } else if (value instanceof LocalDateTime) {

                        if (key.contains(START)) {
                            builder.and(Expressions.dateTimePath(LocalDateTime.class, mapPath.get(key.replaceAll("\\" + START, "")).getMetadata()).goe((LocalDateTime) value));
                        } else if (key.contains(END)) {
                            builder.and(Expressions.dateTimePath(LocalDateTime.class, mapPath.get(key.replaceAll("\\" + END, "")).getMetadata()).loe((LocalDateTime) value));
                        } else  {
                            builder.and(mapPath.contains(key, value));
                        }

                    } else if (value instanceof LocalDate) {

                        if (key.contains(START)) {
                            builder.and(Expressions.datePath(LocalDate.class, mapPath.get(key.replaceAll("\\" + START, "")).getMetadata()).goe((LocalDate) value));
                        } else if (key.contains(END)) {
                            builder.and(Expressions.datePath(LocalDate.class, mapPath.get(key.replaceAll("\\" + END, "")).getMetadata()).loe((LocalDate) value));
                        } else {
                            builder.and(mapPath.contains(key, value));
                        }
                    } else if (value instanceof Double) {

                        if (key.contains(START)) {
                            builder.and(Expressions.numberPath(Double.class, mapPath.get(key.replaceAll("\\" + START, "")).getMetadata()).goe((Double) value));
                        } else if (key.contains(END)) {
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
