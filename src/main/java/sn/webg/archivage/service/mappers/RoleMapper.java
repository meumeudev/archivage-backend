package sn.webg.archivage.service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import sn.webg.archivage.service.entities.DocumentEntity;
import sn.webg.archivage.service.entities.DocumentTypeEntity;
import sn.webg.archivage.service.entities.FolderEntity;
import sn.webg.archivage.service.entities.FolderTypeEntity;
import sn.webg.archivage.service.entities.MetaDataEntity;
import sn.webg.archivage.service.entities.RoleEntity;
import sn.webg.archivage.service.models.RoleDTO;
import sn.webg.archivage.service.models.ValuePermission;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RoleMapper extends EntityMapper<RoleDTO, RoleEntity>{

    @Override
    @Mapping(target = "metaDataList", source = "metaDatas", qualifiedByName = "metaDataList")
    @Mapping(target = "folders", source = "folderList", qualifiedByName = "folders")
    @Mapping(target = "folderTypes", source = "folderTypeList", qualifiedByName = "folderTypes")
    @Mapping(target = "documents", source = "documentList", qualifiedByName = "documents")
    @Mapping(target = "documentTypes", source = "documentTypeList", qualifiedByName = "documentTypes")
    RoleEntity asEntity(RoleDTO roleDTO);

    @Override
    @Mapping(target = "metaDatas", source = "metaDataList", qualifiedByName = "metaDataListDTO")
    @Mapping(target = "folderList", source = "folders", qualifiedByName = "folderDTO")
    @Mapping(target = "documentList", source = "documents", qualifiedByName = "documentDTO")
    @Mapping(target = "documentTypeList", source = "documentTypes", qualifiedByName = "documentTypeDTO")
    @Mapping(target = "folderTypeList", source = "folderTypes", qualifiedByName = "folderTypeDTO")
    RoleDTO asDto(RoleEntity roleEntity);


    @Named("metaDataList")
    default Set<MetaDataEntity> getMetaDataList(Set<ValuePermission> metaDataList) {

        if (Objects.isNull(metaDataList) || metaDataList.isEmpty()) {
            return null;
        }

        return metaDataList.stream().filter(valuePermission -> Objects.nonNull(valuePermission.getId())).map(valuePermission -> MetaDataEntity.builder().id(valuePermission.getId()).label(valuePermission.getName()).build()).collect(Collectors.toSet());
    }

    @Named("metaDataListDTO")
    default Set<ValuePermission> getMetaDataListDTO(Set<MetaDataEntity> metaDataList) {

        if (Objects.isNull(metaDataList)) {
            return new HashSet<>();
        }

        return metaDataList.stream().map(metaDataEntity -> ValuePermission.builder().id(metaDataEntity.getId()).name(metaDataEntity.getLabel()).type(Objects.nonNull(metaDataEntity.getMetaDataType())? metaDataEntity.getMetaDataType().name() : null).build()).collect(Collectors.toSet());
    }

    @Named("folders")
    default Set<FolderEntity> getFolders(Set<ValuePermission> folders) {

        if (Objects.isNull(folders) || folders.isEmpty()) {
            return null;
        }

        return folders.stream().filter(valuePermission -> Objects.nonNull(valuePermission.getId())).map(valuePermission -> FolderEntity.builder().id(valuePermission.getId()).name(valuePermission.getName()).build()).collect(Collectors.toSet());
    }

    @Named("folderDTO")
    default Set<ValuePermission> getFolderDTO(Set<FolderEntity> folders) {

        if (Objects.isNull(folders)) {
            return new HashSet<>();
        }

        return folders.stream().map(folder -> ValuePermission.builder().id(folder.getId()).name(folder.getReference()).build()).collect(Collectors.toSet());
    }

    @Named("folderTypes")
    default Set<FolderTypeEntity> getFolderTypes(Set<ValuePermission> folderTypeList) {

        if (Objects.isNull(folderTypeList) || folderTypeList.isEmpty()) {
            return null;
        }

        return folderTypeList.stream().filter(valuePermission -> Objects.nonNull(valuePermission.getId())).map(valuePermission -> FolderTypeEntity.builder().id(valuePermission.getId()).code(valuePermission.getName()).build()).collect(Collectors.toSet());
    }

    @Named("folderTypeDTO")
    default Set<ValuePermission> getFolderTypeDTO(Set<FolderTypeEntity> folderTypes) {

        if (Objects.isNull(folderTypes)) {
            return new HashSet<>();
        }

        return folderTypes.stream().map(folderType -> ValuePermission.builder().id(folderType.getId()).name(folderType.getLabel()).build()).collect(Collectors.toSet());
    }

    @Named("documents")
    default Set<DocumentEntity> getDocuments(Set<ValuePermission> documentList) {

        if (Objects.isNull(documentList) || documentList.isEmpty()) {
            return null;
        }

        return documentList.stream().filter(valuePermission -> Objects.nonNull(valuePermission.getId())).map(valuePermission -> DocumentEntity.builder().id(valuePermission.getId()).reference(valuePermission.getName()).build()).collect(Collectors.toSet());
    }

    @Named("documentDTO")
    default Set<ValuePermission> getDocumentDTO(Set<DocumentEntity> documents) {

        if (Objects.isNull(documents)) {
            return new HashSet<>();
        }

        return documents.stream().map(document -> ValuePermission.builder().id(document.getId()).name(document.getReference()).build()).collect(Collectors.toSet());
    }

    @Named("documentTypes")
    default Set<DocumentTypeEntity> getDocumentTypes(Set<ValuePermission> documentTypeList) {

        if (Objects.isNull(documentTypeList) || documentTypeList.isEmpty()) {
            return null;
        }

        return documentTypeList.stream().filter(valuePermission -> Objects.nonNull(valuePermission.getId())).map(valuePermission -> DocumentTypeEntity.builder().id(valuePermission.getId()).code(valuePermission.getName()).build()).collect(Collectors.toSet());
    }

    @Named("documentTypeDTO")
    default Set<ValuePermission> getDocumentTypeDTO(Set<DocumentTypeEntity> documentTypes) {

        if (Objects.isNull(documentTypes)) {
            return new HashSet<>();
        }

        return documentTypes.stream().map(documentType -> ValuePermission.builder().id(documentType.getId()).name(documentType.getLabel()).build()).collect(Collectors.toSet());
    }

}
