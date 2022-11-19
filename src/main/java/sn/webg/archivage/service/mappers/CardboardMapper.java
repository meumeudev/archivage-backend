package sn.webg.archivage.service.mappers;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import sn.webg.archivage.service.entities.CardboardEntity;
import sn.webg.archivage.service.entities.ShelfEntity;
import sn.webg.archivage.service.models.CardboardDTO;

import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CardboardMapper extends EntityMapper<CardboardDTO, CardboardEntity> {

    @Override
    @Mapping(target = "shelfId", source = "shelf.id")
    CardboardDTO asDto(CardboardEntity cardboardEntity);

    @Override
    @Mapping(target = "shelf", source = "cardboardDTO", qualifiedByName = "shelfId")
    CardboardEntity asEntity(CardboardDTO cardboardDTO);

    default ShelfEntity shelfId(CardboardDTO cardboardDTO) {
        if (StringUtils.isNotEmpty(cardboardDTO.getShelfId())) {
            ShelfEntity shelfEntity = ShelfEntity.builder().id(cardboardDTO.getShelfId()).build();
            if (Objects.nonNull(cardboardDTO.getShelf())) {
                shelfEntity.setActive(cardboardDTO.getShelf().isActive());
                shelfEntity.setCode(cardboardDTO.getShelf().getCode());
                shelfEntity.setLabel(cardboardDTO.getShelf().getLabel());
            }
            return shelfEntity;
        }
        return null;
    }
}
