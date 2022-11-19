package sn.webg.archivage.service.mappers;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import sn.webg.archivage.service.entities.*;
import sn.webg.archivage.service.entities.FolderEntity;
import sn.webg.archivage.service.models.FolderDTO;
import sn.webg.archivage.service.models.FolderDTO;

import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface FolderMapper extends EntityMapper<FolderDTO, FolderEntity>{

    @Override
    @Mapping(target = "folderType", source = "folderDTO", qualifiedByName = "folderType")
    @Mapping(target = "cardboard", source = "folderDTO", qualifiedByName = "cardboard")
    @Mapping(target = "organization", source = "folderDTO", qualifiedByName = "organization")
    FolderEntity asEntity(FolderDTO folderDTO);

    @Override
    @Mapping(target = "folderTypeId", source = "folderType.id")
    @Mapping(target = "cardboardId", source = "cardboard.id")
    @Mapping(target = "organizationId", source = "organization.id")
    FolderDTO asDto(FolderEntity folderEntity);

    @Named("folderType")
    default FolderTypeEntity getFolderType(FolderDTO folderDTO) {
        FolderTypeEntity folderType = FolderTypeEntity
                    .builder()
                    .id(folderDTO.getFolderTypeId()).build();

            if (Objects.nonNull(folderDTO.getFolderType())) {
                folderType.setCode(folderDTO.getFolderType().getCode());
                folderType.setLabel(folderDTO.getFolderType().getLabel());
            }

            return folderType;
    }

    @Named("organization")
    default OrganizationEntity getOrganization(FolderDTO folderDTO) {

        if (Objects.nonNull(folderDTO.getOrganizationId())) {
            return OrganizationEntity
                    .builder()
                    .id(folderDTO.getOrganizationId()).build();
        }

        return null;

    }

    @Named("cardboard")
    default CardboardEntity getCardboard(FolderDTO folderDTO) {

        CardboardEntity cardboard = CardboardEntity
                .builder()
                .id(folderDTO.getCardboardId()).build();

        if (Objects.nonNull(folderDTO.getCardboard())) {
            cardboard.setCode(folderDTO.getFolderType().getCode());
            cardboard.setLabel(folderDTO.getFolderType().getLabel());
        }

        if(StringUtils.isNotEmpty(folderDTO.getCardboardId())){
            return cardboard;
        }
        return null;


    }
}
