package sn.webg.archivage.service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import sn.webg.archivage.service.entities.MetaDataEntity;
import sn.webg.archivage.service.entities.MetaDataForTypeEntity;
import sn.webg.archivage.service.models.MetaDataForTypeDTO;

import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface MetaDataForTypeMapper extends EntityMapper<MetaDataForTypeDTO, MetaDataForTypeEntity> {

    @Mapping(target = "metaData", source = "metaDataForTypeDTO", qualifiedByName = "metaData")
    MetaDataForTypeEntity asEntity(MetaDataForTypeDTO metaDataForTypeDTO);

    @Mapping(target = "metaDataId", source = "metaData.id")
    MetaDataForTypeDTO asDto(MetaDataForTypeEntity metaDataForTypeEntity);

    @Named("metaData")
    default MetaDataEntity getMetaData(MetaDataForTypeDTO metaDataForTypeDTO) {
        if (Objects.nonNull(metaDataForTypeDTO.getMetaDataId())) {
            MetaDataEntity metaData = MetaDataEntity
                    .builder()
                    .id(metaDataForTypeDTO.getMetaDataId()).build();

            if (Objects.nonNull(metaDataForTypeDTO.getMetaData())) {
                metaData.setMetaDataType(metaDataForTypeDTO.getMetaData().getMetaDataType());
                metaData.setLabel(metaDataForTypeDTO.getMetaData().getLabel());
            }

            return metaData;
        }
        return null;
    }

}
