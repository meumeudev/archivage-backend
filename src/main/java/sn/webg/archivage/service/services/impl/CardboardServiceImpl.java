package sn.webg.archivage.service.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.webg.archivage.service.entities.CardboardEntity;
import sn.webg.archivage.service.exceptions.ResourceNotFoundException;
import sn.webg.archivage.service.mappers.CardboardMapper;
import sn.webg.archivage.service.models.CardboardDTO;
import sn.webg.archivage.service.repositories.CardboardRepository;
import sn.webg.archivage.service.services.CardboardService;
import sn.webg.archivage.service.services.ShelfService;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class CardboardServiceImpl implements CardboardService {

    final CardboardRepository cardboardRepository;

    final CardboardMapper cardboardMapper;

    final ShelfService shelfService;

    static final String CARD_IDENTIFIER_NOT_FOUND_MESSAGE = "[Cardboard] Not found  {0}";

    @Override
    public CardboardDTO createCardboard(CardboardDTO cardboardDTO) {

        cardboardDTO.setId(null);

        CardboardEntity cardboardEntity = cardboardRepository.save(cardboardMapper.asEntity(cardboardDTO));

        log.info("create cardboard ok id {}", cardboardEntity.getId());
        log.trace("create cardboard ok  {}", cardboardEntity);

        return cardboardMapper.asDto(cardboardEntity);

    }

    @Override
    public CardboardDTO updateCardboard(CardboardDTO cardboardDTO) {

        if (!cardboardRepository.existsById(cardboardDTO.getId())) {
            throw new ResourceNotFoundException(MessageFormat.format(CARD_IDENTIFIER_NOT_FOUND_MESSAGE, cardboardDTO.getId()));
        }

        CardboardEntity cardboardEntity = cardboardRepository.save(cardboardMapper.asEntity(cardboardDTO));

        log.info("update card ok Id {}", cardboardEntity.getId());
        log.trace("update card ok Id {}", cardboardEntity);

        return cardboardMapper.asDto(cardboardEntity);

    }

    @Override
    public CardboardDTO readCardboard(String id) {

        var cardboard = cardboardRepository.findById(id)
                .map(cardboardMapper::asDto)
                .orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format(CARD_IDENTIFIER_NOT_FOUND_MESSAGE, id)));

        log.info("read cardboard end ok - Id: {}", id);
        log.trace("read cardboard end ok - cardboard: {}", cardboard);

        return cardboard;

    }

    @Override
    public void deleteCardboard(String id) {

        if (!cardboardRepository.existsById(id)) {
            throw new ResourceNotFoundException(MessageFormat.format(CARD_IDENTIFIER_NOT_FOUND_MESSAGE, id));
        }

        cardboardRepository.deleteById(id);
        log.info("delete cardboard ok id {}", id);

    }

    @Override
    public Page<CardboardDTO> readAllCardboards(Pageable pageable, String code, String label, List<String> shelfId) {
        var cardboardDTOS = cardboardRepository.readAllByFilters(pageable, code, label, shelfId)
                .map(cardboardMapper::asDto);

        log.trace("list cardboard get ok {}", cardboardDTOS);

        return cardboardDTOS;
    }
}
