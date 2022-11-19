package sn.webg.archivage.service.mappers;

import java.util.List;

public interface EntityMapper<D, E> {

    E asEntity(D dto);

    D asDto(E entity);
}
