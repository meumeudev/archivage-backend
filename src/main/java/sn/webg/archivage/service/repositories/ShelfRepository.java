package sn.webg.archivage.service.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import sn.webg.archivage.service.entities.QShelfEntity;
import sn.webg.archivage.service.entities.RoleEntity;
import sn.webg.archivage.service.entities.ShelfEntity;

import java.util.Optional;

@Repository
public interface ShelfRepository extends MongoRepository<ShelfEntity, String>, QuerydslPredicateExecutor<ShelfEntity> {

    Optional<ShelfEntity> findByCode(String code);

    default Page<ShelfEntity> readAllByFilters(Pageable pageable, String code, String label, Boolean active) {
        var booleanBuilder = new BooleanBuilder();
        if (StringUtils.isNotEmpty(code)) {
            booleanBuilder.and(QShelfEntity.shelfEntity.code.containsIgnoreCase(code));
        }
        if (StringUtils.isNotEmpty(label)) {
            booleanBuilder.and(QShelfEntity.shelfEntity.label.containsIgnoreCase(label));
        }
        if (active != null) {
            booleanBuilder.and(QShelfEntity.shelfEntity.active.eq(active));
        }

        return findAll(booleanBuilder, pageable);


    }


}
