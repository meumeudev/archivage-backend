package sn.webg.archivage.service.repositories;

import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import sn.webg.archivage.service.entities.CardboardEntity;
import sn.webg.archivage.service.entities.QCardboardEntity;
import sn.webg.archivage.service.entities.ShelfEntity;

import java.util.List;
import java.util.Objects;

@Repository
public interface CardboardRepository extends MongoRepository<CardboardEntity, String>, QuerydslPredicateExecutor<CardboardEntity> {

    default Page<CardboardEntity> readAllByFilters(Pageable pageable, String code, String label, List<String> labelSelf) {
        var booleanBuilder = new BooleanBuilder();
        if (StringUtils.isNotEmpty(code)) {
            booleanBuilder.and(QCardboardEntity.cardboardEntity.code.containsIgnoreCase(code));
        }
        if (StringUtils.isNotEmpty(label)) {
            booleanBuilder.and(QCardboardEntity.cardboardEntity.label.containsIgnoreCase(label));
        }
        if (Objects.nonNull(labelSelf)) {
            booleanBuilder.and(QCardboardEntity.cardboardEntity.shelf.id.in(labelSelf));
        }

        return findAll(booleanBuilder, pageable);


    }


}
