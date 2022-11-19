package sn.webg.archivage.service.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.webg.archivage.service.models.CardboardDTO;

import java.util.List;

public interface CardboardService {

    CardboardDTO createCardboard(CardboardDTO cardboardDTO);

    CardboardDTO updateCardboard(CardboardDTO cardboardDTO);

    CardboardDTO readCardboard(String  id);

    void deleteCardboard(String  id);

    Page<CardboardDTO> readAllCardboards(Pageable pageable, String code, String label, List<String> labelShelf);

}
