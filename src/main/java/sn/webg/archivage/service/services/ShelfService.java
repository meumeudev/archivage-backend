package sn.webg.archivage.service.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.webg.archivage.service.models.ShelfDTO;


public interface ShelfService {

    ShelfDTO createShelf(ShelfDTO shelfDTO);

    ShelfDTO updateShelf(ShelfDTO shelfDTO);

    ShelfDTO readShelf(String id);

    void deleteShelf(String id);

    ShelfDTO readShelfByCode(String code);

    Page<ShelfDTO> readAllShelfs(Pageable pageable, String code, String label, Boolean active);

}
