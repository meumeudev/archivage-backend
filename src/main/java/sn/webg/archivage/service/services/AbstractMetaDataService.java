package sn.webg.archivage.service.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import sn.webg.archivage.service.entities.MetaDataEntity;
import sn.webg.archivage.service.entities.RoleEntity;
import sn.webg.archivage.service.entities.UserEntity;
import sn.webg.archivage.service.exceptions.ForbiddenActionException;
import sn.webg.archivage.service.exceptions.MetaDataException;
import sn.webg.archivage.service.models.MetaDataForTypeDTO;
import sn.webg.archivage.service.models.MetaDataType;
import sn.webg.archivage.service.repositories.MetaDataRepository;
import sn.webg.archivage.service.repositories.UserRepository;
import sn.webg.archivage.service.security.SecurityUtils;
import sn.webg.archivage.service.utils.ArchivageUtils;
import sn.webg.archivage.service.utils.DateConverter;

import javax.validation.constraints.NotNull;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PROTECTED)
@Slf4j
public abstract class AbstractMetaDataService implements DateConverter {

    static final String START = ".start";
    static final String END = ".end";


    public void validateMetaData(Map<String, Object> metaDataList, Set<MetaDataForTypeDTO> metaDataForTypes) {

        /* Checking empty or null metaData */
        if (Objects.isNull(metaDataList) || metaDataList.isEmpty()) {
            if (Objects.nonNull(metaDataForTypes) && !metaDataForTypes.isEmpty()) {
                throw new MetaDataException("MetaData not valid");
            }
        }

        /* check if all the required elements have been filled in */
        checkAllRequiredElementsHaveBeenFilledIn(metaDataForTypes, metaDataList);

        if (Objects.nonNull(metaDataList)) {
            metaDataList.forEach((key, value) -> {

                /* Getting metaData From label */
                var metaDataForType = getMetaDataForTypeByMetaDataLabel(key, metaDataForTypes);
                var metaDataType = metaDataForType.getMetaData().getMetaDataType().getType();

                /* Checking type metaData match */
                if (metaDataType.equals(LocalDate.class)) {
                    value = stringToLocalDate(value.toString());
                } else if (metaDataType.equals(LocalDateTime.class)) {
                    value = stringToLocalDateTime(value.toString());
                } else if (metaDataType.equals(Number.class)) {
                    value = Double.valueOf(value.toString());
                } else {
                    log.info("Validate metaData - object compatible");
                    value = String.valueOf(value);
                }

                metaDataList.put(key, value);

                /* check value match in possibles values*/
                checkValueMatchIfMetaDataTypeIsList(metaDataForType, value);

            });
        }


    }

    MetaDataForTypeDTO getMetaDataForTypeByMetaDataLabel(String metaDataLabel, Set<MetaDataForTypeDTO> metaDataForTypes) {

        return metaDataForTypes.stream()
                .filter(metaDataForTypeDTO -> metaDataForTypeDTO.getMetaData().getLabel().equals(metaDataLabel))
                .findFirst()
                .orElseThrow(() -> new MetaDataException(MessageFormat.format("Aucun metaDonnees trouvé ayant le nom [{0}]", metaDataLabel)));
    }

    void checkValueMatchIfMetaDataTypeIsList(MetaDataForTypeDTO metaDataForType, Object value) {

        if (metaDataForType.getMetaData().getMetaDataType().getType().equals(List.class) && value instanceof String) {
            if (!metaDataForType.getPossibleValues().contains(value)) {
                throw new MetaDataException(MessageFormat.format("La valeur [{0}] ne correspond a aucune valeur possible du metatData [{1}]", value, metaDataForType.getMetaData().getLabel()));
            }
        }
    }

    void checkAllRequiredElementsHaveBeenFilledIn(Set<MetaDataForTypeDTO> metaDataForTypes, Map<String, Object> metaDataList) {
        if (Objects.nonNull(metaDataForTypes)) {
            metaDataForTypes.stream().filter(MetaDataForTypeDTO::isRequired)
                    .forEach(metaDataForType -> {
                        if (!metaDataList.containsKey(metaDataForType.getMetaData().getLabel())) {
                            throw new MetaDataException(MessageFormat.format("MetaData [{0}] requis", metaDataForType.getMetaData().getLabel())) ;
                        }
                    });
        } else if (Objects.nonNull(metaDataList) && !metaDataList.isEmpty()) {
            throw new MetaDataException("MetaData not correct") ;
        }

    }

    public void clearMataData(Map<String, Object> metaDataList) {
        ArchivageUtils.clearMataData(metaDataList);
    }

    public Map<String, Object> setValueAndConvertValidTYpe(boolean isAdmin, UserRepository userRepository, MetaDataRepository metaDataRepository, Map<String, Object> metaDataList) {

        if (Objects.nonNull(metaDataList)) {

            Set<MetaDataEntity> metaDataEntities;

            if (!isAdmin) {
                metaDataEntities = SecurityUtils.getCurrentUserLogin()
                        .flatMap(userRepository::findOneByUsername)
                        .map(UserEntity::getRole)
                        .map(RoleEntity::getMetaDataList)
                        .orElse(new HashSet<>());
            } else {
                metaDataEntities = metaDataRepository.findByLabelIn(metaDataList.keySet().stream().map(this::deletePrefixAndSuffixFromKey).collect(Collectors.toSet()));
            }
            metaDataList.forEach((key, value) -> {

                var metaDataType = findTypeMetaData(metaDataEntities, key);

                /* Checking type metaData match */
                if (metaDataType.equals(LocalDate.class)) {
                    metaDataList.put(key, stringToLocalDate(value.toString()));
                } else if (metaDataType.equals(LocalDateTime.class)) {
                    metaDataList.put(key, stringToLocalDateTime(value.toString()));
                } else if (metaDataType.equals(Number.class)) {
                    metaDataList.put(key, Double.valueOf(value.toString()));
                } else {
                    log.info("Validate metaData - object compatible");
                }

            });
        }

        return metaDataList;

    }

    Class<?> findTypeMetaData(Set<MetaDataEntity> metaDataList, String key) {
        return metaDataList.stream()
                .filter(metaDataEntity -> metaDataEntity.getLabel().equals(deletePrefixAndSuffixFromKey(key)))
                .map(MetaDataEntity::getMetaDataType).map(MetaDataType::getType).findFirst()
                .orElse(null);
    }

    private String deletePrefixAndSuffixFromKey(@NotNull String key) {
        return key.replaceAll(START, "").replaceAll(END, "");
    }


}
