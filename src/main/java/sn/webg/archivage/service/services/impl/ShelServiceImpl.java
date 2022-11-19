package sn.webg.archivage.service.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.webg.archivage.service.entities.ShelfEntity;
import sn.webg.archivage.service.exceptions.ResourceNotFoundException;
import sn.webg.archivage.service.mappers.ShelfMapper;
import sn.webg.archivage.service.models.ShelfDTO;
import sn.webg.archivage.service.repositories.ShelfRepository;
import sn.webg.archivage.service.services.ShelfService;

import java.text.MessageFormat;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class ShelServiceImpl implements ShelfService {

    final ShelfRepository shelfRepository;

    final ShelfMapper shelfMapper;

    static final String SHELF_IDENTIFIER_NOT_FOUND_MESSAGE = "[Shelf] Not found  {0}";

    @Override
    public ShelfDTO createShelf(ShelfDTO shelfDto) {
        shelfDto.setId(null);

        ShelfEntity shelfEntity = shelfRepository.save(shelfMapper.asEntity(shelfDto));

        log.info("create shelf ok id {}", shelfEntity.getId());
        log.trace("create shelf ok  {}", shelfEntity);

        return shelfMapper.asDto(shelfEntity);

    }

    @Override
    public ShelfDTO updateShelf(ShelfDTO shelfDTO) {

        if (!shelfRepository.existsById(shelfDTO.getId())) {
            throw new ResourceNotFoundException(MessageFormat.format(SHELF_IDENTIFIER_NOT_FOUND_MESSAGE, shelfDTO.getId()));
        }
        
        ShelfEntity shelfUpdate = shelfRepository.save(shelfMapper.asEntity(shelfDTO));

        log.info("update shelf ok Id {}", shelfUpdate.getId());
        log.trace("update shelf ok Id {}", shelfUpdate);

        return shelfMapper.asDto(shelfUpdate);

    }

    @Override
    public ShelfDTO readShelf(String id) {

        var shelf = shelfRepository.findById(id)
                .map(shelfMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(SHELF_IDENTIFIER_NOT_FOUND_MESSAGE, id)));

        log.info("read shelf end ok - Id: {}", id);
        log.trace("read shelf end ok - shelf: {}", shelf);

        return shelf;
    }

    @Override
    public void deleteShelf(String id) {

        if (!shelfRepository.existsById(id)) {
            throw new ResourceNotFoundException(MessageFormat.format(SHELF_IDENTIFIER_NOT_FOUND_MESSAGE, id));
        }

        shelfRepository.deleteById(id);
        log.info("delete shelf ok id {}", id);

    }

    @Override
    public ShelfDTO readShelfByCode(String code) {

        var shelf = shelfRepository.findByCode(code)
                .map(shelfMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(SHELF_IDENTIFIER_NOT_FOUND_MESSAGE, code)));

        log.info("read shelf end ok - code: {}", code);
        log.trace("read shelf end ok - shelf: {}", shelf);

        return shelf;
    }

    @Override
    public Page<ShelfDTO> readAllShelfs(Pageable pageable, String code, String label, Boolean active) {

        var shelfs = shelfRepository.readAllByFilters(pageable, code, label, active)
                .map(shelfMapper::asDto);

        log.trace("list shelf get ok {}", shelfs);

        return shelfs;

    }
}